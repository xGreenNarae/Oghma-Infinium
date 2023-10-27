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

데이터가 이미 들어있는 상태로 시작한다면, `baseline-on-migrate` 속성 설정을 켜고 실행해주도록 한다.  (DB에 `flyway_schema_history` 테이블이 만들어진다.)

기본적인 동작원리에 대해 조금 헷갈릴 수 있는 부분  
flyway sql script들을 보고 순서대로 데이터베이스에 적용하는 것으로 보인다.(Migration)  


**JPA를 사용하는 경우 DDL-AUTO에 대해 헷갈릴 수 있는 부분..**  
DDL-AUTO 속성에 대해 헷갈릴 수 있는 부분은, validate로 하였을 경우, "Entity의 필드이름이 DB와 다른것" 같은것을 확인해주는것이고, Entity에 존재하지않는 테이블이 DB에 존재하는것 등은 검사하지 않는다.  
(update등도 모두 마찬가지. Entity파일에 작성된 필드가 DB에 있는지를 확인할 뿐이다. 임의로 이름을 변경하는경우 당연히 새로운 필드가 생성될것이고 DB에서 이름이 변경될것으로 기대하는것이 오류..)    
즉, 새로운 DB서버를 열고 flyway가 적용된 서버를 연결시켜서 실행시키면 
1. migration scripts를 읽고 스키마를 생성한다.  
2.`flyway_schema_history` 도 역시 만들어진다.  
3. DDL-AUTO는 이것과 무관하다. DB 스키마들과 Entity간에 오류를 검사하는것.  
이미 스키마가 존재하는 DB라면,  
"Entity와 DB스키마 간의 차이"를 검증하는 DDL-AUTO validate와 유사하게,  
"migration scripts와 DB스키마 간의 차이"를 검증하는 동작을 한다.  


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

#### Practice

**데이터가 이미 존재하는 DB에 Flyway 를 도입하는 경우**,

1. `baseline-on-migrate` 속성을 켜고 연결한 뒤 한번 실행해준다. DB에 Flyway History 테이블이 만들어진다.
2. Entity를 자유롭게 수정하고, (JPA Buddy 를 사용한다면) Flyway Diff Versioned Migration 도구의 지원을 받아 스크립트를 자동 생성한다.(DB 연결정보를 넣어주면 된다. Entity들을 읽고, DB Table들을 읽어서 변경 내용을 추적하는 것.)

( SQL파일의 이름과 순서에 다시 한 번 주의할 것. )


**단순 스키마 변경이 아닌, 기존 데이터를 조작하고 옮겨야 하는 경우**
마찬가지로 DDL 뿐 아니라, DML 까지 Flyway 스크립트에 순차적으로 잘 작성하면 된다.
(DB를 따로 먼저 변경해버리는게 아니다!!)