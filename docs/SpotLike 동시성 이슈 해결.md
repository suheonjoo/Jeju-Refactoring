
# SpotLike 동시성 이슈 해결

## SpotLike 동시성 이슈 상황

관광지(spot)마다 사람들이 like 버튼을 클릭할수 있습니다.

여기서 동시성 이슈가 생깁니다
userA, userB 가 있다고 가정하겠습니다.

1. userA가 "한라산"이라는 관광지에 like를 누릅니다.

 userA가 "한라산"이라는 관광지에 like 숫자를 조회-> 업데이트-> 커밋하면, like 숫자가 올라가게 됩니다

2. 여기서 userA로 인해서 like 숫자가 올라기 전에 userB도 조회하면, userB도 like숫자를 올리지만 like 숫자가 업데이트 되기 전의 조회된것이므로 잘못되 업데이트가 됩니다

그러면 결론적으로 기족 like에 +2 가 되어야 하는데 like +1 만 하게 됩니다


-------


## 구현 방법

위와 같은 동시성 이슈를 해결하기 위해 5가지의 방법을 고려했습니다.

Mysql을 사용할 경우  
(1) optimistic lock  
(2) pessimistic lock  
(3) named lock  

Redis를 사용할 경우  
(1) lettuce 라이브러리를 사용하는 경우  
(2) redisson 라이브러리를 사용하는 경우  

자세한 장단점은 [여기](https://github.com/suheonjoo/Study-Document/blob/master/%EC%A0%9C%EC%A3%BC%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EA%B4%80%EB%A0%A8%20doc/jeju-Refactoring%20doc/docs/%EB%8F%99%EC%8B%9C%EC%84%B1%20%EC%9D%B4%EC%8A%88%20%ED%95%B4%EA%B2%B0%20%EB%B0%A9%EC%95%88.md)를 참고 하시면 됩니다

### NoSQL vs RDB

먼저 mysql 혹은 redis를 사용할지 결정해야 합니다.
저희 서비스는 이미 redis 를 로그인할 경우 토큰 정보로 활용하고 있었기에 비용적 여유를 걱정할 필요가 없었고,
현재 동시성 이슈는 대용량의 트래픽을 감당하는 상황이라고 가정하였기에 mysql 보다 redis를 선택했습니다

redis를 이용하여 락을 구현하는 방법은 mysql 의 named lock과 같은 방식으로 구현하였습니다. 단지 차이점은 inmemory를 사용하여 락을 구현하였기에 mysql 보다 성능이 좋습니다.

### lettuce vs redisson

lettuce는 spinlock 방식이며, redisson은 pub-sub 방식입니다
lettuce는 spinlock 방식이므로, 락 획득을 실패할시 다시 재시도 하지 않는 상황에서 하는 것이 적절하다고 생각하습니다.
물론 저희가 재시도하는 로직에 Thread.sleep(50)와 같이 락 획득 재시도의 텀을 줄수도 있지만 항상 일정한 수준의 트래픽이 오는 상황이 아니라라고 판단하였습니다.

그래서 redisson의 pub-sub 방식인 "채널"을 이용하야 적용하는 것이 적절하다고 생각하였습니다. 

-----


## 트랜잭션 전파로 추가적인 동시성 이슈 해결

자식 트랜잭션(updateSpotLike)는 부모의 트랜잭션(flipSpotLike)과 별도의 트랜잭션으로 실행되어야 하기때문에 
==@Transactional(propagation = Propagation.REQUIRES_NEW)== 로 변경해 줍니다

required_new 이유:  "부모의 트랜잭션과 동일한 범위로 묶인다면, 트랜잭션 커밋 전에 다른 누가 decrease()하는 경우, 위 코드에서 `"/**/"` 부분에 개입가능할수 있습니다
즉, Database 에 commit 되기전에 락이 풀리는 현상이 발생하여, 중간에 누가 침입할수 있습니다
**핵심은 lock 을 해제하기전에 Database 에 commit 이 되도록 하는 것입니다.**

updateSpotLike는 내부 트랜잭션이라서, commit이 되지 않고, 외부 트랜잭션이 커밋이 완료 되어야 내부 트랜잭션이 커밋됩니다

```java
@Transactional  
public LikeFlipResponse flipSpotLike(Long spotId, Long memberId, Integer key) {  
   boolean isSpotLikeExist = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(spotId, memberId);  
   RLock lock = redissonClient.getLock(key.toString());  
   try {  
      boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);  
      validatedLock(available);  
      updateSpotLike(spotId, memberId, isSpotLikeExist);  
   } catch (InterruptedException e) {  
      throw new RuntimeException(e);  
   } finally {  
	   /**/
      lock.unlock();  
   }  
   Optional<SpotLikeTag> spot = spotLikeTagRepository.findBySpotId(spotId);  
   return LikeFlipResponse.of(spot.get().getLikeCount(), isSpotLikeExist);  
}  
  
private void validatedLock(boolean available) {  
   if (!available) {  
      throw new NotLockException();  
   }  
}  
  
@Transactional(propagation = Propagation.REQUIRES_NEW)  
public void updateSpotLike(Long spotId, Long memberId, boolean isSpotLikeExist) {  
   if (isSpotLikeExist) {  
      memberSpotTagRepository.deleteSpotLikeByMemberIdAndSpotId(spotId, memberId);  
      spotLikeTagRepository.decreaseLikeCount(spotId);  
      return;  
   }  
   spotLikeTagRepository.increaseLikeCount(spotId);  
   memberSpotTagRepository.createSpotLikeByMemberIdAndSpotId(spotId, memberId);  
}
```


---

## 좋아요 기능: 관광지 테이블 내부 컬럼 -> Tag 테이블로 따로 관리

좋아요 표시 기능을 Spot 테이블내부 컬럼에 놓지 않고, SpotLikeTag라는 테이블을 만들어 관리하였습니다.
테이블 이름에 Tag를 달았으며 Spot의 id만 참조하도록 설계하였습니다.

만약 좋아요 표시 기능을 Spot 테이블 내부 컬럼에 놓게 되는 장점을 2가지라고 생각했습니다.
1. 구조적 장점
application의 Facade클래스가 redis 구현 기술의 의존성을 갖게 되며, application부분이 방대해지게 됩니다. 이는 "도메인 주도 개발"로 만들어진 프로젝트에서 좋지 않은 설계라고 생각했습니다.  
왜냐하면, 좋아요 기능은 도메인 내부 변경이라 domain의 서비스 기능이지 application에 로직이 아니기 때문입니다.
그래서 좋아요 기능을 Tag 테이블로 만들며, domain의 서비스 패키지에 로직을 놓을 수 있게 되었으며,
priority 애그리거트에 놓게 되어, 각 애그리거트의 관계또한 명확해졌습니다.

2. 확장성
현재 레디스의 redisson를 사용하여 좋아요 기능의 동시성 이슈를 해결하였습니다.
만약 추후에 mysql 의 비관적락, 낙관적락, named 락으로 기술을 변경하거나 레디스의 lettuce로 기술을 변경할시, application 영역이 아닌 domain 까지만로직을 변경하면 되기에 확장성이 있다고 판단하였습니다.
