### Logback  
Slf4j 의 구현체로는 logback 과 log4j 가 있으니 차이점을 이해한다면 적절히 골라서 사용할 수 있겠다.  
log4j 의 경우, 2015년 개발이 중단되었다고함. 현재 log4j2 를 사용할 수 있다. logback과의 차이점은 멀티스레드 환경에서 비동기 로거의 처리량과 대기시간이 훨씬 더 짧다고 한다.  

패키지경로가 ch.qos .. 로 시작하는것을 볼수있는데, Logback 프레임워크의 Creator는 스위스의 QOS.CH Sarl 이라는 이름의 회사라고 한다.  
`logback-core`는 핵심 코어 컴포넌트  
`logback-classic`은 slf4j에서 사용이 가능하도록 하는 플러그인 컴포넌트.  
`logback-access`는 HTTP요청에 대한 디버깅 기능을 제공하는 부분이라고 한다.  

syntax는 기본적으로 xml이나, groovy를 지원한다. xml을 사용하여 설정할수도있고, Configuration을 사용하여 설정할 수도 있다.  
개인적으로 실행환경 구분 등까지 감안하면 xml을 사용하는 것이 깔끔해보인다. 또한 Automatic reloading of configuration files 를 지원한다. 즉, 주기적인 설정변경을 스캐닝하여 서버의 재기동없이 설정을 유연하게 변경할 수 있다. 현재로서는 xml 파일로는 이것이 작동하는 것을 확인했고 @Configuration Bean에서도 작동하는지는 모르겠다.      
logback.xml 이 아닌, logback-spring.xml 을 사용하면 application property를 불러올 수 있다고 한다.(권장)  
레벨은 error, warn, info, debug, trace 가 있다. error의 경우, 한 밤 중에 시스템 관리자가 침대에서 일어나서 작업에 착수해야 할 수준을 의미한다고 한다(stackoverflow)  
org.hibernate.type 을 trace레벨로 찍으면, 바인딩 파라미터 값을 콘솔에서 확인 가능하다.  


**패턴**  
[공식문서](https://logback.qos.ch/manual/layouts.html)  
`"%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] %logger{5} - %msg %n"`  
%d 는 날짜를 의미한다. %date 등과 같다.  
-5 부분은 좌측정렬, 문자열 최대길이 5를 의미한다.   
%logger{5} 부분의 5는 문자열 최대 길이를 의미한다.  
이외에 caller는 호출자정보 등이 있는데.. 문서를 잘 읽자.  
색깔을 표시하는 방법으로, %highlight 의 경우, ERROR, DEBUG 등 각 레벨에 대해 기본 지정색을 사용하는 것을 의미한다. %red 등으로 직접 지정할 수 있다  


[예제 코드](./examples/log-example/src/main/resources/logback-spring.xml)

---  