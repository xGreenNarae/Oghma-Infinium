#### N:N 관계를 피하기 위한 노하우
예를 들어, 온라인 서점 시스템에서 공동 저자가 발생하는 경우, 공동 저자를 하나의 새로운 저자로 취급할 수 있다.  

---  

#### Delete 하지 않는다.
Soft Delete 해두면 나중에라도 추적할 수 있음.  
JPA로는 @Where 같은 것을 사용해서 쉽게 관리할 수 있음.  
또한 DB레벨에서 권한, Policy를 이용하여 서비스 계정에 removed 접근을 관리할 수도 있다.  

---  

#### DataJpaTest 는 @Repository로 선언된 Bean 을 Autowired 할 수 없다.
이것은 Jpa Repository 가 아니라 Component 이기 때문.  
@TestConfiguration 을 이용하여 수동으로 사용하거나, SpringBootTest 를 쓰자.  

---  

#### 가격이나 수량 등은 int 보다는 큰 범위의 타입을 사용하도록 한다. 또한 overflow 도 고려해야 한다.  

---  

#### Index는 DB 조회 성능 개선을 위한 설정  
서버의 스레드 풀을 조절하는 것과 마찬가지로 지속적으로 변하는 DB의 상황을 모니터링 하며 개선해야 하는 설정 값이다. 따라서 Index설정이 DB가 아닌 서버 코드에 들어가 있는 것은 이상하다고 볼 수 있음.  

---  

#### SQL: LIMIT, OFFSET
LIMIT개를 뽑을건데, OFFSET 번부터 뽑는다.

`SELECT * FROM TABLE LIMIT 10, 20`
뒤 쪽 숫자가 LIMIT
뽑힌결과의 10번째 레코드부터 20개를 뽑는다.
