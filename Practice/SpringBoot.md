### 인터페이스 타입 빈 주입을 시도하면 자동으로 구현체를 찾는다. 2개 이상이면 기본적으로는 에러다.  
IoC/DI, AOP, PSA 가 Spring의 3가지 특징.  
인터페이스 힘을 잘 활용하는것이 중요.  

---
---
---

#### DateTime 이 배열로 나오는 문제
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") 붙여준다  

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

#### HikariCP  
default maximum pool size는 10 이다.  
모든 connection이 사용중인 상태에서 어떤 트랜잭션이 또 다른 connection을 필요로하는 경우, deadlock 이 발생한다. timeout 기본은 30 이다.  

HikariCP wiki 에서 대략적으로 권장하는 Deadlock을 피하는 최소한의 풀 사이즈 계산법  
전체 스레드 개수 = Tn  
하나의 Task에서 동시에 필요한 Connection 수 = Cm  
`Pool Size = Tn X (Cm - 1) + 1`  

spring.datasource.hikarip.maximum-pool-size 로 설정이 존재함.  

---  

#### Tomcat 설정  
max-connections : 최대 처리가능한 연결 수. 기본값은 8192.  
accept-count : max-connections를 넘어서는 추가요청들을 큐에 얼마나 저장해두는지. 기본값은 100  

즉, 요청이 하나도 완료되지 않는다고 가정할때, max-connections + accept-count 를 넘어서는 요청부터는 거절되거나 timeout된다.  

threads.max : 동시요청을 처리할수있는 thread 개수. 기본값은 200  

**accept-count를 늘릴 바에는 max-connections를 늘리는 것으로 충분하지 않나?** : max-connections와 thread를 통해 실제 처리되는 요청은 시스템 리소스를 완전하게 소비하는 것들. accept-count로 저장되어있는 요청들은 리소스를 사용하지 않고 대기하고 있다는 것에 의미가 있다.  

---  

#### SpringBoot Multi Module Project  
공통 코드를 재사용하기 위함.  
예를들어, 동일한 Entity와 Repository들을 참조하여 하나는 Api, 또 다른 하나는 Batch 작업을 하는 프로젝트 등을 생각해볼 수 있다.  
구현은 gradle 설정에 대해 조금 신경쓰면 된다.  
[예제 코드](./examples/multi-module-example/build.gradle)  

---  

#### Template Engines  
장단점과 특징을 ChatGPT가 간략히 정리하였음. 각 1회의 검증을 위한 검색도 하지 않았으므로 대략 참고정도로 읽되 절대적으로 신뢰해서는 안된다.  

**Thymeleaf**  
장점: 자연스러운 HTML 템플릿, 자바 코드와의 통합 용이, 다국어 및 국제화 지원, Spring과의 강한 통합.  
단점: 상대적으로 높은 메모리 사용, 다른 엔진보다 처리 속도가 느릴 수 있음.  

**Handlebars**  
장점: 간단한 문법, 좀 더 확장 가능한 기능, 다국어 및 국제화 지원  
단점: 기능이 다소 제한적일 수 있음.  

**Mustache**  
장점: 간단한 문법, 데이터 표현에 중점, 다양한 언어에서 사용 가능  
단점: 조건문과 반복문의 기능이 제한적, 복잡한 동적 컨텐츠 생성에는 부적합.  

**Pebble**  
장점: 확장 가능한 템플릿, 빠른 처리 속도, Spring과의 통합.  
단점: 상대적으로 사용자 커뮤니티가 작을 수 있음.  

이하는 최신 업데이트가 잘 이루어지고 있는지 확인할 필요가 있음.  

**FreeMarker**  
장점: 높은 처리 속도, 템플릿 상속 및 확장 기능, XML과 유사한 문법.  
단점: 문법이 복잡할 수 있음 + 기능이많아서 러닝커브가 높게 느껴질수있음, 일부 경우에는 성능이 미세하게 떨어질 수 있음.  

**Velocity**  
장점: 높은 처리 속도, 간결한 문법, 캐시 기능 지원, 레거시 시스템 통합에 적합.  
단점: 유연성 부족, 자동 이스케이프가 부족할 수 있음.  


---  

#### Web Clients    
장단점과 특징을 ChatGPT가 간략히 정리하였음. 각 1회의 검증을 위한 검색도 하지 않았으므로 대략 참고정도로 읽되 절대적으로 신뢰해서는 안된다.  

**RestTemplate**  
특징: 간단한 설정으로 사용 가능하며, 동기적인 방식으로 HTTP 호출을 처리합니다.  
장점: Spring Framework와의 연동이 원활하며, 표준적인 방법으로 RESTful 서비스를 호출할 수 있습니다.  
단점: 비동기 처리에 제한적이며(Blocking I/O), 높은 확장성을 요구하는 경우에는 다른 기술을 고려해야 할 수 있습니다.  

**WebClient**  
특징: Reactive Programming을 활용하여 비동기적인 방식으로 HTTP 호출을 처리합니다. Spring WebFlux와 함께 사용할 때 가장 효과적입니다.  
장점: 높은 확장성과 성능을 제공하며, 비동기 작업을 지원하여 블로킹되지 않는 처리가 가능합니다.  
단점: 기존의 RestTemplate에 비해 학습 곡선이 높을 수 있으며, 모든 프로젝트에 적합하지는 않을 수 있습니다.  

**Feign**  
특징: 선언적 방식으로 HTTP 클라이언트를 작성하며, Spring Cloud에서 지원됩니다.  
장점: 인터페이스와 어노테이션을 사용하여 간단하게 HTTP 호출을 정의하고 구현할 수 있습니다.  
단점: Spring Cloud 환경이 아닌 경우에는 추가적인 설정이 필요할 수 있습니다. + Blocking I/O  

**Apache HttpClient**  
특징: Apache 라이브러리로서 안정적이고 다양한 설정 및 기능을 제공합니다.  
장점: 많은 기능과 커스터마이징이 가능하며, 별도의 의존성 추가 없이 사용 가능합니다.  
단점: RestTemplate이나 WebClient와 비교하여 불필요한 코드나 설정이 추가될 수 있습니다.  

**OkHttp**  
특징: 성능이 우수하며, 대용량 데이터 처리에 효과적입니다.  
장점: 빠른 속도와 간결한 문법을 가지고 있습니다. Connection pooling과 같은 기능을 내장하고 있습니다.  
단점: Spring과의 연동이 덜 원활할 수 있으며, Spring에서 제공하는 기능을 모두 활용하기 어려울 수 있습니다.  

**Retrofit**  
특징: Android와의 통합이 강조된 HTTP 클라이언트 라이브러리입니다.
장점: Android 앱 개발 시에 효과적으로 사용 가능하며, 인터페이스 기반의 명확한 호출 방식을 제공합니다.  
단점: Android 외의 플랫폼에서는 사용 시 추가적인 작업이 필요할 수 있습니다.  

**Unirest**  
특징: 다양한 언어와 형식을 지원하는 간편한 HTTP 클라이언트 라이브러리입니다.  
장점: 간결한 문법으로 HTTP 요청을 처리할 수 있으며, 여러 데이터 형식을 다루기 쉽습니다.  
단점: 다른 Spring 기반의 기술과 연동 시 일부 제한이 있을 수 있습니다.   

---  

#### Spring Retry  
외부 API 호출 등, 일시적인 실패로 여러 번 재시도해주는 처리가 필요할 경우 사용할 수 있는 기술.  
resilience4j 를 사용할 수도 있으나 이쪽은 CircuitBreaker와 같이 사용되는 류 라고.. (자세히는 모르겠음)  
설정 이후, 다음과 같이 간단하게 적용 가능.    
```
@Retryable(
	value = {RuntimeException.class}, 
	maxAttempts = 2, 
	backoff = @Backoff(delay = 3000)
)
```
maxAttempts가 모두 실패하였을 경우(FallBack)에 대한 처리로 `@Recover`를 사용할 수 있다. parameter도 똑같이 넘겨받을 수 있다.  

---  

### IntelliJ 환경에서 실행 오류 관련  

#### java.lang.IllegalStateException: Module entity with name: ... should be available  
settings.gradle의 rootProject.name 이 대소문자까지 올바르게 써있는지 확인.  

#### Gradle Dependency 들이 load되지 않을때..  
cache invalidate, restart도 해보고..  
오래된 프로젝트인 경우, IntelliJ IDEA 업데이트와 관련한 오류일 수도 있다. 프로젝트 내의 .idea 폴더를 삭제하고 재시작하면 해결될 수 있음.  

#### Code Color Scheme 적용되지 않을때.  
사실 IntelliJ의 문제는 아닐것이고, SpringBoot를 사용하고 있는 환경에서 source root directory 설정을 해줘야 해결되는 경우가 있음.  
이 경우, `src/main/java` 가 되겠다.  

---  

#### SlF4J에서 Placeholder를 사용하여 불필요한 문자열 연산을 생략할 수 있다.  
log.error("...{}", value) 의 경우, 이 로그레벨(에러)이 아닌경우 문자열 연산을 수행하지 않는다고 함.  

---  

























