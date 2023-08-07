#### N:N 관계를 피하기 위한 노하우
예를들어, 온라인 서점 시스템에서 공동저자가 발생하는 경우, 공동저자를 하나의 새로운 저자로 취급할 수 있다.  

#### Delete 하지 않는다.
Soft Delete 해두면 나중에라도 추적할 수 있음.  
JPA로는 @Where 같은것을 사용해서 쉽게 관리할 수 있음.  

#### DataJpaTest 는 @Repository로 선언된 Bean 을 Autowired 할 수 없다.
이것은 Jpa Repository 가 아니라 Component 이기 때문.  
@TestConfiguration 을 이용하여 수동으로 사용하거나, SpringBootTest 를 쓰자.  

#### 가격이나 수량 등은 int 보다는 큰 범위의 타입을 사용하도록 한다. 또한 overflow 도 고려해야 한다.

