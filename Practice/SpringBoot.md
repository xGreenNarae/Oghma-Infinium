### 인터페이스 타입 빈 주입을 시도하면 자동으로 구현체를 찾는다. 2개 이상이면 기본적으로는 에러다.

---
---
---

#### DateTime 이 배열로 나오는 문제
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") 붙여준다.  

---

#### Controller Response Content-type
ObjectMapper 등을 이용하든지로.. String type 을 return 하면 기본적으로 Content-type 이 text/html 이다.  

---

#### @Valid 는 순서가 보장되지 않는다.
Validation 라이브러리의 @Validated 기준, 선언된 제약조건들이 ConcurrentHashMap 이라는 자료구조에 저장 - 접근 되기 때문에 매 요청마다 검사 순서가 무작위로 발생하는 것으로 보인다. 확신이 없기 때문에, Question 에 남겨둔다.

---

#### RequestBody 에 들어가는 Dto 의 Field 가 1개일때는 zero-parameter 생성자가 없을 경우 오류가 발생한다.
- Jackson 에 연관된 사항으로, 좋은 이야기가 이미 있다.(제대로 읽어보지는 않음) 필드가 하나인 경우 굳이 객체를 사용할 이유가 없기 때문이 아닐까라고 생각해봤다. 이것 하나를 해결하기 위해서 여러가지 다른 문제를 일으킬 여지들이 많다는 뉘앙스 인 듯..
  - https://github.com/mock-rc4/coupangEatsB-test-server-ashley/issues/15
- 내가 선택한 해결책은 그냥 zero-parameter 를 추가해서 사용하는것. 또는 Dto 의 경우 Setter 를 사용해도 괜찮을 것 같다.  

---

#### RestControllerAdvice 가 여러 개일 경우, 순서가 보장되지 않는다.
@Order 로 관리해줄 수 있다.

---

#### JUnit은 반드시 Autowired 를 주렁주렁 달아야 하는가
JUnit 의 Jupiter 가 Bean 을 직접 관리하지 않기 때문에, 생성자 주입이 안된다고 한다. @Autowired 의 반복을 해결하기 위해 TestConstructor 라는 annotation 도 존재한다고 하는데, 단순히 중복되는 코드를 상속 등을 이용해 해결해 볼 수 있음.

---

#### build.gradle, Dependency 는 소스코드에서 가져다 쓸 jar 파일들을 의미하고, plugin 은 빌드 과정에서 사용될 jar 파일들.

---

#### Open Session In View
- spring.jpa.open-in-view 를 application.yml 에서 false 로 설정할수있음. 기본값은 true
- false 시에 DB 세션의 수명을 transaction 과 동기화. Controller 단에서 Read 를 막는것을 의미한다.
- 트래픽이 적은 admin page 같은 경우에 쓸만할 듯.
    
---

#### @Valid 는 List type 에서는 동작하지 않는다.
Class level @Validated 로 해결할 수 있다.

---

#### Controller URL Mapping 시 "" 와 "/" 는 다르다.
둘 다 존재한다면 "/" 가 우선 매핑된다. 구체적인 동작의 원리는 이해 못했음.

---

#### LomBok은 당연하지만, 컴파일타임에 코드검색을 할 수 없다.

---

#### JUnit, ParameterizedTest + CsvSource에 NullValue 넣는 방법
```
@ParameterizedTest
@CsvSource(value = {"0,Name1", "2,Name2", "3,null"}, nullValues = "null")
void test(final int index, final String name) {
  System.out.println("index = " + index);
  System.out.println("name = " + name);

  assertThat(index).isLessThan(4);
  assertThat(name).isNotNull(); // Fail
}
```

---

#### JUnit, assertAll  
assert문의 나열에서, 중간에 실패하더라도 모두 실행하여 결과들을 보여준다.  

---  

#### Mockito, MockBean vs SpyBean  
MockBean은 조작한 부분 빼고는 작동하지 않음.
SpyBean은 조작하지 않은 부분들은 정상작동.  

---  

#### Spring Bean  
일반적으로 싱글톤 타입으로 객체를 관리하게 하는데 의미가 있다고 생각한다. @Component, @Bean, @Configuration .. 등 다양한 Annotation들이 Spring Bean을 만들어 낸다.  
같은 타입의 빈일 경우, 주입받을 때 식별될 필요가 있다. Qualifier, Primary 등을 사용할 수 있고 여기서 같은 타입이란 예를들어 method 의 반환 타입을 의미한다.  
프로토타입 빈도 있다. 싱글톤이 아니라 일반 객체를 new 로 만드는 것과 동일하게 매번 새로운 객체를 생성한다는 건데, DI의 장점을 사용한다는 것에 의의가 있다.  

---  

#### DataSource 를 여러 개 사용하고 싶다면  
yml 은 대략 이런식
```  
spring:
  datasource:
    write:
      jdbc-url: jdbc:mysql://127.0.0.1:3307/testdb?allowPublicKeyRetrieval=true&useSSL=false
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: 'write-service'
      password: password
    read:
      jdbc-url: jdbc:mysql://127.0.0.1:3308/testdb?allowPublicKeyRetrieval=true&useSSL=false
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: 'read-service'
      password: password
```  

DataSource 를 분기하여 사용할 수 있는데, 예를들어 Transational 의 readonly 를 기준으로 분기하고자 한다면.. 다음과 같은 구현이 가능하다.  

```
@Configuration
public class DataSourceConfiguration {
  public static final String WRITE_SERVER = "WRITE";
  public static final String READ_SERVER = "READ";

  @Bean
  @Primary
  public DataSource dataSource() {
    DataSource determinedDataSource = routingDataSource(writeDataSource(), readDataSource());
    return new LazyConnectionDataSourceProxy(determinedDataSource);
  }

  @Bean
  @Qualifier("ROUTER")
  public DataSource routingDataSource(
    @Qualifier(WRITE_SERVER) DataSource writeDataSource,
    @Qualifier(READ_SERVER) DataSource readDataSource
  ) {
    DataSourceRouter routingDataSource = new DataSourceRouter();

    HashMap<Object, Object> dataSourceMap = new HashMap<>();
    dataSourceMap.put(READ_SERVER, readDataSource);
    dataSourceMap.put(WRITE_SERVER, writeDataSource);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(writeDataSource);

    return routingDataSource;
  }

  @Bean
  @Qualifier(WRITE_SERVER)
  @ConfigurationProperties(prefix = "spring.datasource.write")
  public DataSource writeDataSource() {
    return DataSourceBuilder.create()
      .build();
  }

  @Bean
  @Qualifier(READ_SERVER)
  @ConfigurationProperties(prefix = "spring.datasource.read")
  public DataSource readDataSource() {
    return DataSourceBuilder.create()
      .build();
  }
}
```

```
public class DataSourceRouter extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? 
      DataSourceConfiguration.READ_SERVER 
      : 
      DataSourceConfiguration.WRITE_SERVER;
  }
}
```  

[모범예제 참고: hudi](https://hudi.blog/database-replication-with-springboot-and-mysql/).  

---  

#### Jacoco  
Java Code Coverage  
테스트 이후 실행되는 코드 커버리지 측정 도구  

대충 Gradle 예제.
```
plugins {  
	...  

	id 'jacoco'  
}

jacoco {
	toolVersion = "0.8.10" // 최신버전이 뭔가 -> https://www.jacoco.org/jacoco/
}

jacocoTestReport {
	reports {
		xml.required = true // Gradle 8.0.2 .. 아무튼 최신버전에서 바뀐 부분이라 주의.
		csv.required = false
		html.required = true
		html.destination file("${buildDir}/reports/jacocoReport")
	}
	finalizedBy jacocoTestCoverageVerification
}

jacocoTestCoverageVerification { // 실패하면 빌드실패.  
	violationRules {
		rule { // 브랜치, 라인 커버리지가 80% 이상이어야 한다.
			enabled = true
			limit {
				counter = 'LINE'
				value = 'COVEREDRATIO'
				minimum = 0.8
			}

			limit {
				counter = 'BRANCH'
				value = 'COVEREDRATIO'
				minimum = 0.8
			}
		}
	}
}
```

---  







