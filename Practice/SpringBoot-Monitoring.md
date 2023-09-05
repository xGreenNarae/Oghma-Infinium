### 애플리케이션 메트릭 노출    
SpringBoot Actuator는 애플리케이션의 메트릭을 HTTP 엔드포인트 등으로 노출시켜준다. 또한 어느정도 환경설정을 제어할 수 있게 해준다고 한다.  

#### SpringBoot Actuator  
참고. Actuator는 Micrometer라는 JVM 기반 애플리케이션을 위한 metrics 측정 라이브러리의 자동 구성을 제공하고 이것에 의존한다.  

다음을 추가한다.  
`implementation 'org.springframework.boot:spring-boot-starter-actuator'`  

기본적으로 활성화 되어있는 엔드포인트들은..  
`GET /health`  
`GET /info`  

yml은 다음과 같이 설정할 수 있다.  
```
management:
  endpoints:
    web:
      base-path: /management # 수동지정
      exposure:
        include: health,info,beans,conditions,heapdump
        exclude: threaddump
```
base-path는 기본적으로는 /actuator 인 것으로 보임.  

Actuator는 기본적으로 노출되는 엔드포인트들에 대한 보안처리를 하지않는다! 스프링시큐리티를 사용하건 **보안처리를 반드시** 하도록 하자.  

`/health`의 경우, "status"라는 값을 제공하는데..  
```
UP : 외부 시스템이 작동 중이고 접근 가능하다.
DOWN : 외부 시스템이 작동하지 않거나 접근할 수 없다.
UNKNOWN : 외부 시스템의 상태가 분명하지 않다.
OUT_OF_SERVICE : 외부 시스템에 접근할 수 있지만, 현재는 사용할 수 없다.
```  

다음 설정을 통해 status뿐 아니라 다양한 값을 제공하도록 할 수 있다.  
```
management:
  endpoint:
    health:
      show-details: always
```

JDBC데이터소스 등 다른 외부 데이터베이스와 시스템의 건강지표들도 제공한다고 한다.  

참고로 info에서 노출되는 값도 yml에 직접설정해줄수 있다.  
```
info:
  contact:
    email: support@springboot.com
    phone: 010-1234-5678
```  

`/env` 의 경우, GET POST DELETE를 지원하는데, application.yml의 설정정보 또는 기본설정값들.. classpath등을 확인할수있고 이것들을 서버의 런타임에 수정할수도 있다!  

`/mappings` 는 api endpoint들을 모두 보여준다.  

`/heapdump` 는 애플리케이션의 힙 덤프를 다운로드받는다. Eclipse MAT 등의 도구가 해당 파일을 분석할 수 있게 지원한다.  

이외에 커스텀 엔드포인트를 만들수있음..  


---  

### 노출된 메트릭 지표 수집, 수집된 데이터 시각화  
간단한 수준으로는 SpringBoot Admin Server 를 사용할 수 있다.  
이것은 2023-09-05 현재 `서드파티 프로젝트`이다.  
더 고급 수준의 시스템이 필요할때 Prometheus, Grafana 등을 사용할 수 있는것으로 보인다.  

#### SpringBoot Admin Server  
이것은 Actuator처럼 API서버 내부의 특정 엔드포인트에서 접근할수있는 시스템이 아니라, API서버를 "Client"로 등록하고 메트릭을 가져와서 시각화 하는 **별도의 서버** 이다!(Grafana가 돌아가는것을 생각해보면 될듯)  

