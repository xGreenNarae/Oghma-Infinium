
#### JPA와 SQL 고민

현재 내 생각으로는, 가능한 최대한 JPA에서 해결을 보는게 생산성, 유지보수(!!) 측면에서 유리한 것 같다. 
SQL(native query, jdbc template 등) 사용을 고민하고 있다면, 왜 고민하고 있는지 단순히 쿼리를 여러 번으로 나눠서 해결할 수는 없는지 등을 한번 더 생각해보자.
성능 문제가 발생하더라도 다른 쪽에서 해결 보는 것을 먼저 생각해보자.

---
#### JPA만 사용하다가 SQL을 사용할 때 주의할 점

- EntityManager를 사용할거라면 `@Autowired` 보다는 `@PersistenceContext`로 주입하자. JPA에서 영속성 컨텍스트를 주입하는 표준 annotation.  (Factory 방식을 사용한다고 함)

- SQL 코드는 결국 필드의 이름이나 순서만 바뀌더라도, 코드에 변경이 필요할 수 있다. 모든 결과 값에 대한 자동화 테스트 코드를 반드시 달아둘 것...

- Select 절에 필요한 값을 모두 명시할 것.
- 결과 값을 객체에 자동으로 매핑하는 JPA의 지원을 받을거라면, Select 요소의 순서가 중요하다. 또는 as Name 절로 뽑아서 직접 매핑할 것.

- Entity 이름을 Table 이름으로 바꿨는지 주의
- join에는 on 절이 따른다.(무슨 기준으로 join 할 것이냐)


---

#### JPA native query vs JdbcTemplate
native query는 EntityManager의 createNativeQuery를 포함한다.

공통점은 둘 다 SQL을 직접 작성한다는 점인데, 쿼리 작성의 유연함이라는 장점을 가지고 가고, 
JPA Query Method 또는 JPQL에 비해 생산성은 더 떨어질 수 있겠다. 
`@Transactional` 의 지원은 받을 수 있다고 함.

차이점은,
- JPA native query는 영속성 컨텍스트를 그대로 활용할 수 있다.(캐싱, 지연로딩, 더티체킹 등..). JdbcTemplate은 불가능.
- JPA native query는 batchUpdate가 불가능하다. JdbcTemplate은 가능.