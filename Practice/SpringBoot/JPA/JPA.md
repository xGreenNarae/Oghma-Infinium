
#### Bean내부에서 같은클래스의 method를 호출할때는 Transactional이 동작하지 않는다
Spring AOP는 Proxy 객체를 사용하는데, Self-invocation상황에서는 this를 통해 호출하기 때문.  

---

#### CheckedException은 "기본적으로" RollBack 되지 않는다
`@Transactional` annotation의 기본 설정 값이 그렇다.
``@Transactional(rollbackFor = ..)`` 을 사용하거나, 
전역에서 처리하려면 AOP로 RollbackRuleAttribute를 설정할 수 있다.  

---

#### Index, DB Constraints 등은 Entity에 정의하지 않는다.
Index 는 상황에 맞게 쿼리를 최적화 하기 위한 것, DB 에 책임이 있다.  
Entity 레벨에서 관리해야할 제약조건과 DB 레벨에서 관리해야할 제약조건은 쓰임이 다를 수 있다.

---

#### Transactional readonly 
readonly 는 실제 쿼리호출을 방해하지 않는다. dirty-checking 시에 write를 막는다.
또한 javax가 아닌 springframework의 Transactional을 import해야 사용할 수 있다.  

---  

#### Import Id  
Id annotation을 import 할때,  
javax.. Id 와 org.springframework.. 이 있는데,  
후자는 NoSQL에 대한 지원용이라고 한다.    

헷갈리지 말것은, Entity 와 Transactional 등의 어노테이션에도 이와 같이 두개의 Import가 있으나 이 쪽은 Entity의 경우 javax(다른것은 deprecated), Transactional의 경우 springframework의 것을 사용하면 된다.  


---  

#### MultipleBagFetchException

어떤 Entity가 `@OneToMany`로 참조하고 있는 필드를 둘 이상 가지고 있고 FetchType.Lazy 라고 가정하자.
하나씩 하나씩 LazyLoading 쿼리를 추가로 날려서 가져오게 될텐데, 한 방에 가져온다고 Join Fetch를 써주는 것은 불가능하다.
Join Fetch 자체가 Many 관계인 자식 Entity 둘 이상에 적용할 수 없게 되어 있다.
그 때 발생하는 Exception.

해결책은, `jpa.properties.spring.default_batch_fetch_size` 등의 설정을 사용해서 in 쿼리를 이용한 꼼수 최적화(?) 가 가능하다고 하는데, 적용되지 않아서(아직 이유를 모르겠다)..

EntityManager 또는 JdbcTemplate 사용한 Raw SQL 로 직접 매핑하는 것도 괜찮은 듯 하다.

---

#### `[QueryTranslatorImpl:389]` HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!

JPQL의 Fetch Join을 사용하면서 Pagination을 사용하려고 시도할 경우,
Pagination이 적용되지 않고, 모든 결과를 메모리에 올려놓고 작업하려고 한다.

warning이 뜨는게 아니라 Exception(`JpaSystemException`)을 발생 시켜서 실수를 방지하려면, `spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch = true` 설정을 주면 된다.

대량의 데이터를 다루는 batch application이 아니라면 꼭 해결해야 하는 것은 아닌 `warning` 정도로 취급해도 좋고, 해결하기 위해서는 fetch join 없이 2번의 쿼리를 사용하라는 제안이 있는데 가장 간단하고 JPA 레벨을 벗어나지 않아서 유지보수 측면에서도 좋아 보인다.
1. pagination 으로 id만 가져온다.(조건식이 필요하면 JPQL의 `JOIN`을 활용하라: `JOIN FETCH`가 아님)
2. in 절을 사용해 식별자를 담아서 두번째 쿼리를 사용한다.


참고로, entity manager의 `setFirstResult`는 조회 시작 위치(offset), `setMaxResults`는 limit에 해당한다.
