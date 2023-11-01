
헬스체크 및 지표 노출을 위한 exporter


#### 안전한 사용을 위한 설정 
[참고](https://techblog.woowahan.com/9232/)
```
# Actuator 설정 샘플

# 1. 기본적으로 다 꺼놓고 필요한것만 켜서 쓰자
# 1-1. Endpoint all disable
management.endpoints.enabled-by-default = false

# 1-2. Enable specific endpoints
management.endpoint.info.enabled = true
management.endpoint.health.enabled = true

# 2. JMX를 안쓰면 닫고, 필요한 endpoint만 노출하자
management.endpoints.jmx.exposure.exclude = *
management.endpoints.web.exposure.include = info, health

# Enable(켜다/끄다)과 Exposure(닫다/열다)의 차이
# Enable은 bean을 활성화하는 것
# Exposure은 jmx, http로 노출시키는 것
# expose 하지 않고, 자유롭게 사용할 수 있도록 하기 위한 설계인 듯.


# 3. 기본 포트를 피하자
management.server.port = [포트번호]

# 4. 기본 path를 피하자(/actuator)
management.endpoints.web.base-path = [/변경된 경로]


# 이외에 필요한 경우, Security등의 설정으로 인증과정 등록..

```


---

#### Endpoints

[문서 참고](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/)

```
info
health
env
metrics
loggers
threaddump
```

정도 사용했다.