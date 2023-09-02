#### N:N 관계를 피하기 위한 노하우
예를들어, 온라인 서점 시스템에서 공동저자가 발생하는 경우, 공동저자를 하나의 새로운 저자로 취급할 수 있다.  

---  

#### Delete 하지 않는다.
Soft Delete 해두면 나중에라도 추적할 수 있음.  
JPA로는 @Where 같은것을 사용해서 쉽게 관리할 수 있음.  
또한 DB레벨에서 권한, Policy를 이용하여 서비스계정에 removed 접근을관리할수도 있다.  

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
`resources/db/migration` 하위에 V1__init.sql 등의 sql 파일로 변경이력을 관리한다.  
변경은 계속 쌓여나가야한다.  
언더바가 두 개 인것에 주의.  
데이터가 이미 들어있는 상태로 시작한다면, `baseline-on-migrate` 속성 설정을 켜주도록 한다.  

기본적인 동작원리에 대해 조금 헷갈릴 수 있는 부분  
flyway sql script들을 보고 순서대로 데이터베이스에 적용하는 것으로 보인다.(Migration)  
DDL-AUTO 속성에 대해 헷갈릴 수 있는 부분은, validate로 하였을 경우, "Entity의 필드이름이 DB와 다른것" 같은것을 확인해주는것이고, Entity에 존재하지않는 테이블이 DB에 존재하는것 등은 검사하지 않는다.  
즉, 새로운 DB서버를 열고 flyway가 적용된 서버를 연결시켜서 실행시키면 1. migration scripts를 읽고 스키마를 생성한다. 2.`flyway_schema_history` 도 역시 만들어진다. 3. DDL-AUTO는 이것과 무관하다. DB 스키마들과 Entity간에 오류를 검사하는것. 이미 스키마가 존재하는 DB라면, "Entity와 DB스키마 간의 차이"를 검증하는 DDL-AUTO validate와 유사하게, "migration scripts와 DB스키마 간의 차이"를 검증하는 동작을 한다.

이외에 repair등 마이그레이션에 관련한 다른 기능들이 지원된다.  


로컬 임베디드 DB인 H2 같은경우, `flyway_schema_history` 테이블 자체가 존재하지 않는 상태에서 매번 새롭게 만들어지기 때문에 당연히 의미없음.  


**History File Naming Rule**에 대해 생각해보았음.  
**Version Naming Rule**  
1.0.0 으로 시작.  
Major.Minor.Patch  

Major : 주요한 데이터 구조의 변경이 발생한 경우  
		테이블 추가 및 삭제  

Minor : 기존 버전과 크게 다르지 않은 변경 사항  
		테이블 변경 : 필드 추가, 삭제, 이름 또는 타입 변경 등  

Patch : 기존 버전과 호환되는 버그 수정  
		의미 상의 변경이 아닌, 실수를 바로 잡기위한 수정사항 등.  
**File Naming Rule**  
SQL KEYWORD를 따르도록 한다.  

Major : Table 추가 및 삭제 시  
		create_tableName.sql  
		drop_tableName.sql  

Minor : Table 변경 시  
		alter_tableName_rename_newTableName.sql : 테이블 이름 변경  
		alter_tableName_add_column.sql  
		alter_tableName_drop_column.sql  
		alter_tableName_modify_column.sql  
		alter_tableName_change_column.sql : 필드 이름까지 변경  

Patch : 사소한 변경  
		Minor 의 Rule을 따르도록 한다.  

---  

#### Index는 DB 조회성능 개선을 위한 설정  
서버의 스레드 풀을 조절하는 것과 마찬가지로 지속적으로 변하는 DB의 상황을 모니터링하며 개선해야하는 설정 값이다. 따라서 Index설정이 DB가 아닌 서버 코드에 들어가 있는 것은 이상하다고 볼 수 있음.  

---  



