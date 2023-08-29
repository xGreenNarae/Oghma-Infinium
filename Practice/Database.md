#### N:N 관계를 피하기 위한 노하우
예를들어, 온라인 서점 시스템에서 공동저자가 발생하는 경우, 공동저자를 하나의 새로운 저자로 취급할 수 있다.  

---  

#### Delete 하지 않는다.
Soft Delete 해두면 나중에라도 추적할 수 있음.  
JPA로는 @Where 같은것을 사용해서 쉽게 관리할 수 있음.  

---  

#### DataJpaTest 는 @Repository로 선언된 Bean 을 Autowired 할 수 없다.
이것은 Jpa Repository 가 아니라 Component 이기 때문.  
@TestConfiguration 을 이용하여 수동으로 사용하거나, SpringBootTest 를 쓰자.  

---  

#### 가격이나 수량 등은 int 보다는 큰 범위의 타입을 사용하도록 한다. 또한 overflow 도 고려해야 한다.  

---  

#### DB Scheme 형상관리  
Flyway 등, 스키마 변경이력을 추적할 수 있게한다.  
Liquibase 등은 무료로 Rollback을 지원한다고 함.  

Flyway 설정예시  
application.yml  
```
flyway:
enabled: true
url: jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false
user: 'user'
password: PASSWORD
locations: classpath:db/migration
```  
resources/db/migration 하위에 V1__init.sql 등의 sql 파일로 변경이력을 관리한다.  
변경은 계속 쌓여나가야한다.  
언더바가 두 개 인것에 주의.  
데이터가 이미 들어있는 상태로 시작한다면, baseline-on-migrate 속성 설정을 켜주도록 한다.  

---  

#### Index는 DB 조회성능 개선을 위한 설정  
서버의 스레드 풀을 조절하는 것과 마찬가지로 지속적으로 변하는 DB의 상황을 모니터링하며 개선해야하는 설정 값이다. 따라서 Index설정이 DB가 아닌 서버 코드에 들어가 있는 것은 이상하다고 볼 수 있음.  

---  



