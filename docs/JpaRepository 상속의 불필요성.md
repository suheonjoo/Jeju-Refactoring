

JpaRepository가 편한데 Repository를 사용하는 이유

### 1. 명령 모델의 단위 테스트

- 단위 테스트에서 리포지토리의 대역은 가짜 대역을 사용한다
모의 객체를 사용하지 않는다
인터페이스에 메서드가 적어야 가짜 구현 만들기가 쉽다

- JpaRepository는 가짜 대약을 구현하기에 메서드가 너무 많다

```java
public class MemberUserRepository implements UserRespository{
	//UserRespository가 JpaRepository 를 상속 했다면 약 20여개 메서드를 구현해야 한다
}
```

### 2. 가능하면 필요한 메서드

- 명령 모델의 리포지토리는 필요한 메서드가 매우 적다
findById 또는 findOne.save 정도가 필수
이외 도메인이 제공할 기능에 따라 2-3개 정도의 메서드만 추가가 필요하다

- JpaRepsoitory 를 상속하면, 필요하지 않은 메서들를 오용할 가능성이 있다
특히 intellij에서 .누르면 추천 메서드가 나오는데 이때 오용할 가능성이 있다


### 3. 조회 모델은?

- 조회 모델도 JpaRepsoitory를 상속하지 않는다
조회 모델에 save()가 존재하면?
Repository를 상속하고 필요한 메서드를 그때그때 추가한다

- 조회 모델 구현
다양한 스페 조합이 필요하면 jpa
단순 조회면 jdbcTemplate
쿼리가 복잡한 조회면 MyBatis를 사용하자







JpaRepository를 상속하게 되면 이미 구현되어 있는 수많은 메시지가 외부에 개방됩니다.  
주로 사용하게 될 서비스 레이어에서 해당 리포지토리에 개방된 메시지 자동완성 도움을 받았을 때,  
이 리포지토리의 책임들이 무엇이고 역할이 무엇인지 파악할 수 없게 됩니다.  
물론 리포지토리니까 당연히 객체들을 컬렉션처럼 보관해주는 역할이겠거니 하고 유추할 순 있지만,  
해당 비즈니스 로직에 있어서 이 리포지토리가 어떤 메시지들의 묶음으로 어떤 역할을 갖는지는 알 수 없습니다.

Repository를 상속하게 되면 기본으로 구현되는 메서드가 전혀 없습니다.  
사용할 메서드들을 인터페이스 내부에 선언해두어야 하고, 선언된 메서드에 대해서만 외부에 개방됩니다.  
서비스 레이어에서 사용할 때, 어떤 메시지를 보낼 수 있는지 한눈에 파악할 수 있게 되고,  
비즈니스 로직에서 유의미한 책임들이 무엇이 있는지 파악할 수 있게 됩니다.

이처럼 필요한 메시지만 외부에 개방하는 일은  
함께 일하는 동료 개발자들에게 "이 리포지토리는 이런 역할이야" 라고 메시지를 보낼 수 있다는 점에서  
상당히 매력적이라고 생각합니다.




### 정리

JpaRepsoitory 를 상속하면 편하지만 단위 테스트에서 가짜 구현을 만들기 어렵다
CQRS에는 맞지 않다
그래서 Repository를 상속하고 필요한 메서드만 추가한다

//실제로 Repository만 상속 받아도 된다

Repository 인터페이스의 javadoc을 보면 다음과 같이 작성되어 있습니다.

> Central repository marker interface. Captures the domain type to manage as well as the domain type's id type. General purpose is to hold type information as well as **being able to discover interfaces that extend this one during classpath scanning for easy Spring bean creation.**

핵심은 마지막의 `클래스패스 스캐닝을 할 때 스프링 빈을 쉽게 만들어줄 수 있도록 한다` 부분입니다. 즉, Repository 인터페이스만 상속하고 있으면 해당 인터페이스에 대한 구현체를 Spring Data JPA가 만들어준다는 이야기입니다.

```java
public interface MemberRepository extends Repository<Member, Long> { 
	void save(Member member); 
	Optional<Member> findById(Long id); 
}
```


https://jaehoney.tistory.com/250