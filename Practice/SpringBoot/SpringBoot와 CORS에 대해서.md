

CORS 문제 라고 표현되는 것은 보통, 개발 단계에서 클라우드 등에 배포해둔 원격서버를 로컬에서 클라이언트 개발자가 사용하는 과정에서 HTTP 통신의 장애를 만들어 내는 현상인데.

일단, CORS를 "문제" 라고 표현하기 때문에 오해할만한 부분은
CORS가 문제를 해결해주는 설정이다. 
"CORS 문제를 해결해주세요" == "CORS 설정을 한다" 가 되겠다.

이건 브라우저 정책이다.(굉장히.. 복잡한 보안 문제가 담겨있다.)
로컬의 node 런타임 등, 브라우저를 거치지 않으면 맞이할 일이 없다.


프로그래밍 언어는 js 이고, http request는 fetch api를 통해 발생하기 때문에
- `브라우저의 주소 창에 URL을 넣고 Enter` == `form tag + get`  (이 쪽은 Origin이 Cross가 아니라 문제 없다.)
- `fetch` (현재 Origin에서 다른 Origin을 호출할 수 있는, 이 쪽이 문제다)

그리고 `GET` 과 `GET이 아닌 METHOD` 는 다르다.
POST등의 요청은 OPTIONS(pre-flight)가 먼저 날아가서 총 2번의 요청이 전송된다.

SpringBoot에서 CORS 설정과 관련된 코드가 두 가지가 있는데
```
@Configuration  
public class CorsConfiguration implements WebMvcConfigurer {  
  
    @Override  
    public void addCorsMappings(final CorsRegistry registry) {  
        registry.addMapping("/**")  
            .allowedHeaders("*")  
            .allowedMethods("*")  
            .allowedOrigins("*")  
            .exposedHeaders("Authorization")  
            .allowCredentials(false);  
    }
}
```

```
public CorsConfigurationSource configurationSource() {  
    final CorsConfiguration configuration = new CorsConfiguration();  
    configuration.addAllowedHeader("*");  
    configuration.addAllowedMethod("*"); 
	configuration.addAllowedOriginPattern("*"); 
	configuration.setAllowCredentials(true); 
    configuration.addExposedHeader("Authorization");
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
    source.registerCorsConfiguration("/**", configuration);  
    return source;  
}  
  
@Bean  
public SecurityFilterChain securityFilterChain(..) {  
    http.cors().configurationSource(configurationSource());  
}
```

아래쪽은 Security를 추가했을 때 설정하는 부분..

---

**CORS가 문제가 해결되었다는 것은 대충 무엇을 의미하는가?**

이것도 2가지다.

Response Header에
`Access-Control-Allow-Origin` 와
`Vary: Origin` 가 붙는다.

CDN과 관련한 보안 문제가 추가로 발생해서 나오는 개념 같은데, 더 자세히 알아보지 못했음.

---

즉, 
- SpringBoot에서 각 설정들을 적용하였을 때,
- Fetch API를 사용하여 Cross Origin으로 GET과 POST 요청을 보낼 때, 
- 각각 SecurityFilter를 타는 요청, 타지 않는 요청을 보낼 때,
- 응답 헤더에 무엇이 달려오는지(`Access-Control-Allow-Origin`, `Vary: Origin`)
- 브라우저에서 어떻게 반응하는지(Fetch 명령어에서 Exception이 터지는지)
를 하나씩 알아보자.
(Security를 달지 않았을 경우는 또 어떻게 되는지는 논외로 치고..)


#### 아무것도 적용하지 않았을 때
헤더에는 당연히 둘 다 없다.
(POST의 OPTIONS에는 Vary: Origin이 달려있다?)

**GET**
**GET + Security**
**POST**
**POST + Security**

4가지 모두, Fetch는 Exception을 던지고
CORS 에러다.

#### CorsRegistry만 설정했을 때

**GET**
**GET + Security**
**POST**
위 3가지는 `Access-Control-Allow-Origin` 과 `Vary: Origin` 이 붙고
Fetch는 터지지 않는다.

**POST + Security**
CORS Error가 발생한다.(둘 다 안 붙어있다, pre-flight OPTIONS에는 붙는데 POST에는 붙어있지 않아서 Fetch문이 터지는 것으로 보임.)


#### SecurityFilterChain http.cors

`ConfigurationSource` 설정을 해주지 않는다면(`http.cors();` 로 끝나는 경우) `Vary: Origin` 은 붙어있으나 `Access-Control-Allow-Origin`은 안 붙는다.(4 경우 모두 Fetch가 터지고 CORS에러가 발생한다)

`ConfigurationSource`을 해줘야 `Access-Control-Allow-Origin` 까지 붙는다.
(4가지 다 CORS 에러 없이 깔끔히 해결된다)

또는 기존의 `CorsRegistry` 설정을 놔두고, `http.cors();` 만 써줘도 모두 해결된다.


표로 정리하면 다음과 같다.

| 설정 \ 요청타입 | GET | GET + Security | POST | POST + Security |
|---------------|-----|----------------|------|-----------------|
| None          | ❌ | ❌ | ❌ | ❌ |
| WebMvcConfigurer CorsRegistry  | ✅ | ✅ | ✅ | ❌ |
| SecurityFilterChain http.cors().configurationSource.. | ✅ | ✅ | ✅  | ✅ |


---

**결론**

`SecurityFilterChain`의 `http.cors..` 설정이 없다면, **인증이 필요한 POST(GET 이외의 메소드)** 에서 CORS Error가 발생할 것이다.
`WebMvcConfigurer CorsRegistry`로 허용하는 method, origin등을 설정해두었다면, `SecurityFilterChain`에서는 `http.cors();` 만 가지고 끝내도 해결된다.

그런데 분리하고 싶지 않다면, 그냥 `SecurityFilterChain`에서 `CorsConfigurationSource`까지 설정을 하도록 하자.


문제를 해결하려는 목적과 보안 정책의 의도를 모두 충족하기 위한 Best Practice는 클라이언트 개발을 위해 브라우저 정책을 잠시 끄거나 우회하는 것으로 보인다.

