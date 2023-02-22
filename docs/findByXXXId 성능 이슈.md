
## spring data jpa의 성능 이슈

XXXID로 해당 엔티티의 참조된 id 접근하는게 아니라
실제 XXX와 조인하여 id에 접근한다

-> 즉 불필요한 조인이 나간다


### 해결
@Query로 순수 jpql 쿼리를 짜준다

ex) findByTeamId(Long id)가 아니라

@Query("select m from member m where m.team.id = :id")
로 지겁 jpql 로 날리면 된다