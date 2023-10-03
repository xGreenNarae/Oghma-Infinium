### 인터페이스 타입 빈 주입을 시도하면 자동으로 구현체를 찾는다. 2개 이상이면 기본적으로는 에러다.  
IoC/DI, AOP, PSA 가 Spring의 3가지 특징.  

@Bean 으로 bean을 생성하게 되면, method name이 bean name으로 생성된다.  
@Qualifier가 없어도 bean name과 field name이 매칭 가능하면 bean을 주입해준다.  
(!) @Primary가 있으면, bean name을 무시하고 Type 기반으로 Primary인 Bean을 주입한다.  
@Qualifier 어노테이션이 @Primary 어노테이션보다 우선하여 적용된다.  

---
---
---

#### DateTime 이 배열로 나오는 문제
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") 붙여준다  
Date의 경우 당연히 yyyy-MM-dd 까지..  

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

#### Spring Bean  
일반적으로 싱글톤 타입으로 객체를 관리하게 하는데 의미가 있다고 생각한다. @Component, @Bean, @Configuration .. 등 다양한 Annotation들이 Spring Bean을 만들어 낸다.  
같은 타입의 빈일 경우, 주입받을 때 식별될 필요가 있다. Qualifier, Primary 등을 사용할 수 있고 여기서 같은 타입이란 예를들어 method 의 반환 타입을 의미한다.  
프로토타입 빈도 있다. 싱글톤이 아니라 일반 객체를 new 로 만드는 것과 동일하게 매번 새로운 객체를 생성한다는 건데, DI의 장점을 사용한다는 것에 의의가 있다.  

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

#### IntelliJ 우측 패널에서 Gradle을 열어 "직접 추가한" Dependency 들의 버전을 쉽게 확인할 수 있음.  

---  

#### 기본적인 Exception들에는 무엇이 있는가  
```
spring:
  web:
    resources:
      add-mappings: false
  mvc:
    throw-exception-if-no-handler-found: true
    dispatch-options-request: false
```
`NoHandlerFoundException` : 404처리를 위해 사용할수있다.  
web: `resources: add-mappings: false`  
엔드포인트에 없는 주소인 경우 정적 리소스에 대한 매핑을 하지 않음  
mvc : `throw-exception-if-no-handler-found: true`   
Spring 기본적으로 생성하는 404 Not Found 응답을 생성하지 않도록 설정    
mvc : `dispatch-options-request: false`   
OPTIONS 요청이 애플리케이션에 전달되지 않고 기본 OPTIONS 처리기가 처리하여 간단한 200 OK 응답을 보냄  

`MethodArgumentNotValidException`  
`ConstraintViolationException` Class Level Validated 를 이용하는 경우, 이것으로 취급되는 것으로 보인다.  
`MethodArgumentTypeMismatchException` : 주로 Request Parameter 을 binding 하지 못할떄 발생  

`HttpRequestMethodNotSupportedException` : 지원하지 않은 HTTP method 호출 할 경우 발생

`HttpMessageNotReadableException` : HTTP 요청 메시지가 올바르게 읽을 수 없을 때 발생하는 예외
`MissingServletRequestParameterException` : 요청에서 필수 매개변수가 누락된 경우 발생하는 예외
`BindException` : 매개변수에 대한 바인딩이 실패할 때 발생하는 예외

---  

#### Server Sent Events  
SSE 개념..  
서버에서 클라이언트로 단방향 통신 즉, 서버푸쉬가 필요할때 적합..  
지속연결을 사용한다. HTTP/1.1 의 경우도 6개 까지의 동시접속을 허용한다고하고.. HTTP/2 의 경우 기본값이 100이라고 한다.  

요청, 응답간에 MIME Type을 `text/event-stream`으로 맞춰줘야함. Accept, Content-type  
바이너리 데이터는 전송할수 없다고하고, UTF-8 로 전송하라고 한다.  
```
event: type1
data: An event of type1.
data2: Data2.

event: type2
data: An event of type2.
```
이벤트는 필드를 가질수있고 줄바꿈으로 구분되고.. 이벤트 간 구분은 줄바꿈 두개로 한다고..  
(여기까지 표준의 내용들을 확실하게 찾아보지 않았음)  

SpringBoot에서..  
SseEmitter라는 API가 제공된다.  

Practical한 이야기들은..  
첫 응답시 데이터를 무엇이든 포함해서 보내도록 하자.(이유?)  
jpa open-in-view가 true라면, HTTP Connection이 열려있어야 하므로.. 동시에 DB Connection도 열려있게됨에 주의.  
Nginx등의 프록시를 사용할경우, Connection header와 http version에 주의. 추가로 Nginx의 proxy buffering 기능도 주의.(X-accel 기능에 대해서..)   
SseEmitter를 메모리에 저장할경우.. 분산환경에서 주의..  

---  

#### Exception Handler, Annotation에 선언한 class와 method parameter로 받는 class 타입이 다른경우, advice 자체에 도달하지 못하는 것 처럼 보이거나 상위타입 Handler에 먼저 도달하는 등 기괴하고 다양하게 펼쳐지는 에러를 경험할 것임.  

---  

#### Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310  

Jackson time 직렬화/역직렬화 문제.  

SpringBoot Controller에서 Object를 return할 때는 발생하지 않으나..
테스트 코드 등, objectmapper를 수동으로 생성해서 사용할 때 주로 발생..

`'com.fasterxml.jackson.datatype:jackson-datatype-jsr310` 종속성을 추가하여 해결하거나,  
`new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString("DTO Object..");`  
와 같이 해결할 수 있음.  
테스트 코드라면 아래쪽을 만들어두고 상속 등으로 공유해서 사용하거나 하자..

---  

#### Application Server TimeZone 설정  

SpringBootApplication에 다음 메소드를 추가한다..  

```
@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
```

---  

#### k8s에서 실행할 때 기본으로 server.forward-headers-strategy 설정이 native로 된다.

---

#### Query Parameter 입력 값을 Object 로 바인딩해서 받고 싶을 때,

@RequestParam 으로는 동작하지 않고.. @ModelAttribute 등을 사용하면 된다.
이유는 현재 모르겠음.

---




















