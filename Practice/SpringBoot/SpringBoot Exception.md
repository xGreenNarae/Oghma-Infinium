
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

#### Exception Handler, Annotation에 선언한 class와 method parameter로 받는 class 타입이 다른경우, advice 자체에 도달하지 못하는 것 처럼 보이거나 상위타입 Handler에 먼저 도달하는 등 기괴하고 다양하게 펼쳐지는 에러를 경험할 것임.  

---  

#### ExceptionHandler가 잡을 수 없는 영역은 어떤 것들이 있는가

일단 Spring Context 바깥의 것들은 다 못잡는다.

Tomcat 자체에서 Max Size를 넘어서는 Request를 버린다거나.. (`server.tomcat.max-swallow-size`)

Interceptor, Aspect는 ExceptionHandler가 처리할 수 있다.
Filter는 처리할 수 없다. (DispatcherServlet 바깥)

---



