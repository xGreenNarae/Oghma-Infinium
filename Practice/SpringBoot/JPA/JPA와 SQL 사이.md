
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


---

#### JPA 부터 SQL 까지

TODO: 충분히 다양한 케이스들을 숙지하고 나면 행과 열의 표로 정리해보자.


핵심은
- 영속성 컨텍스트의 지원 여부
	- 캐싱, 더티체킹 등이 지원되는지.
- Mapper(Projection)
	- (유지보수) 쿼리결과를 자동으로 Entity등의 "구조가 정의된 데이터타입"으로 만들어주는지, 직접 결과값을 순환하면서 인덱스로 집어 넣어야 하는지
	- 직접 넣는다면, select필드의 순서가 변경되기만 해도 코드를 읽고 바꿔야 한다.

연속적인 추상화 단계의 기술들이고,
추상화 레벨이 올라갈수록 작성 비용과 유지보수 비용의 이점을 얻는다.
추상화 레벨이 낮아질수록 유연하고 자유로운 조작이 가능하다. 
베스트는 모든 기술을 빠삭하게 꿰차고 적재적소에 사용하는 것인데, 가성비가 높은 방법을 찾아보자.


추상화 단계의 순서로 적는다.

- Entity자체에 NamedQuery를 적고 결과를 Dto에 바로 매핑하거나 Rest Repositories등을 사용해서 Entity만 가지고 Controller - Dto - JPA Repository 의 API를 바로 뽑아내는 극단적인 추상화 레벨의 구현들도 존재한다.

- JPA Query Method
일반적이다. join이 필요한 시점부터 내려간다.


- JPA Query Method + JPQL
fetch join이 지원된다.(그냥 join도 있다. fetch하지 않는다는 것. where절에서 참조만 하려는 경우 등)
OneToMany 필드를 둘 이상 가지고 있는 경우 fetch join은 하나만 가능하다.(둘 이상부터는 아예 에러)
Pagination과 Fetch Join을 하나의 쿼리에서 동시에 사용하는것은 지원되지 않는다(동작은하지만 전부다 메모리로 가져와서 pagination이 application레벨에서 발생한다.)
Batch Size, default fetch size 등을 이용한 적절한 조정으로 이 선에서 어떻게든 해결을 보는 시도는 가능하다.


- JPA Query Method 
- Entity Manager + JPQL
비슷한데, Entity Manager를 직접 이용하는 경우 조금 더 상세한 컨트롤이 가능하다.(별도의 클래스로 분리해서..) 


- Query DSL

- Entity Manager + Native Query
- JPA Query Method + Native Query

- JdbcTemplate




