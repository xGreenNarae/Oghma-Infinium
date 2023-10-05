### Logback  
Slf4j 의 구현체로는 logback 과 log4j 가 있으니 차이점을 이해한다면 적절히 골라서 사용할 수 있겠다.  
log4j 의 경우, 2015년 개발이 중단되었다고함. 현재 log4j2 를 사용할 수 있다. logback과의 차이점은 멀티스레드 환경에서 비동기 로거의 처리량과 대기시간이 훨씬 더 짧다고 한다.  

패키지경로가 ch.qos .. 로 시작하는것을 볼수있는데, Logback 프레임워크의 Creator는 스위스의 QOS.CH Sarl 이라는 이름의 회사라고 한다.  
`logback-core`는 핵심 코어 컴포넌트  
`logback-classic`은 slf4j에서 사용이 가능하도록 하는 플러그인 컴포넌트.  
`logback-access`는 HTTP요청에 대한 디버깅 기능을 제공하는 부분이라고 한다.  

syntax는 기본적으로 xml이나, groovy를 지원한다. xml을 사용하여 설정할수도있고, Configuration을 사용하여 설정할 수도 있다.  
개인적으로 실행환경 구분 등까지 감안하면 xml을 사용하는 것이 깔끔해보인다. 또한 Automatic reloading of configuration files 를 지원한다. 즉, 주기적인 설정변경을 스캐닝하여 서버의 재기동없이 설정을 유연하게 변경할 수 있다고 하는데 제대로 사용하는법에 대해 숙지하지 못했음.       
logback.xml 이 아닌, logback-spring.xml 을 사용하면 application property를 불러올 수 있다고 한다.(권장)  
레벨은 error, warn, info, debug, trace 가 있다. error의 경우, **한 밤 중에 시스템 관리자가 침대에서 일어나서 작업에 착수해야 할 수준**을 의미한다고 한다(stackoverflow)  
org.hibernate.type 을 trace레벨로 찍으면, 바인딩 파라미터 값을 콘솔에서 확인 가능하다.  

관행적으로 camelCase를 따른다고 한다.

**패턴**  
[공식문서](https://logback.qos.ch/manual/layouts.html)  
`"%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%-5level] %logger{5} - %msg %n"`  
%d 는 날짜를 의미한다. %date 등과 같다.  
-5 부분은 좌측정렬, 문자열 최대길이 5를 의미한다.   
%logger{5} 부분의 5는 문자열 최대 길이를 의미한다.  
이외에 caller는 호출자정보 등이 있는데.. 문서를 잘 읽자.  
색깔을 표시하는 방법으로, %highlight 의 경우, ERROR, DEBUG 등 각 레벨에 대해 기본 지정색을 사용하는 것을 의미한다. %red 등으로 직접 지정할 수 있다  

`%yellow(#) %replace(%msg){'binding parameter \\[(\\d*)\\] as \\[\\w*\\] - ', '$1 - '} %n`  
노란색 # 문자.  
replace(a){b, c} 구문은 a에 등장하는 b패턴을 c패턴으로 치환하라는 것이다. 패턴부분은 정규식이다. 위의 예제 같은경우 hibernate binding type을 로깅하는 패턴이다. 정규식에 캡처구문이 사용된 것에 주의.  

**RollingFileAppender**  
파일에 기록하고 제한에 도달하면 삭제하면서 회전시키는 전략이다.  
RollingPolicy가 있고, TriggeringPolicy가 있음.  

공식문서의 설명, 소스코드의 주석 등이 난해한 부분들이 있어서 중요한 부분만 정리한다.  
여기서는 두 가지 Policy 만 고려한다.   

1. TimeBasedRollingPolicy  
일별, 월별 등 시간을 기준으로 하는 롤오버 정책.  
`logFile.%d{yyyy-MM-dd}.log` 와 같은 fileNamePattern을 가질 수 있는데, 이게 중요하다.  
위의 경우, dd 까지 명시되어 있기 때문에, "일별" 로 새로운 파일이 생성된다는 뜻이다(같은 이름의 파일이 두개 존재할수는 없으므로) MM까지만 명시할 경우 월별순환으로 설정했다고 볼 수 있다.  
maxHistory는 위에서 "일별"로 설정하였기 때문에, 몇일분량을 최대제한으로 할지를 결정하는 것이다. 30이라면, 30일치(30개의파일)만 유지한다. 기본값은 0이고, 삭제하지 않음을 의미한다.    
totalSizeCap은 여기서 유지할 아카이브용량(전체로그파일용량)을 의미한다. 초과할 경우, 가장 오래된 파일을 삭제한다. 0이라면 제한없음을 의미한다.      
maxHistory가 먼저 적용되고, 그다음에 totalSizeCap이 적용된다고 나와있다.  

2. SizeAndTimeBasedRollingPolicy  
`mylog-%d{yyyy-MM-dd}.%i.txt` 와 같은 fileNamePattern이 있다.(i와 d 토큰이 여기서는 필수이다)  
나머지는 동일하고, maxFileSize 필드가 있는데 단일 로그파일의 사이즈를 제한하는 것이다. 이것을 초과한 상태인데 아직 dd가 다음날이 아니라면, i(index)를 증가하여 새로운 파일을 생성하는 것이다.  

이외에도 FixedWindowRollingPolicy가 있으나 느리고 문제가 많기 때문에 공식문서에서 권장하지 않는다고함 -> 더이상 사용되지 않는 정책으로 간주한다는 표현이 사용됨 
maxHistory 필드가 있는데, 초기 값은 0이고, 아카이브의 삭제를 비활성화 한다는 뜻이다. 



[예제 코드](./examples/log-example/src/main/resources/logback-spring.xml)

---  
