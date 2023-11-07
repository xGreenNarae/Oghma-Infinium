
`org.springframework.boot:spring-boot-devtools` 종속성은 보통 developmentOnly로 사용하는데, live-reload 등의 기능을 지원한다.

그리고 `ExceptionHandlerExceptionResolver`에서 발생하는 로그(DEBUG레벨)를 WARN 레벨로 바꿔서 띄워준다.
이러면 개발환경에서 보던 로그가 Production 환경(jar파일 실행)에서 뜨지 않게 된다.

이걸 없애려면 devtools를 사용하지 않거나,
`spring.devtools.add-properties`를 false로 명시하면 devtools가 기본적으로 설정하는 property들을 사용하지 않는다.

또는 `spring.mvc.log-resolved-exception`을 명시적으로 false로 해주면된다.


이외에도 기본 설정 값들을 바꿔버리는 devtools의 설정들은.. 다음과 같다.
```
devToolsProperties.put("spring.thymeleaf.cache", "false");
		devToolsProperties.put("spring.freemarker.cache", "false");
		devToolsProperties.put("spring.groovy.template.cache", "false");
		devToolsProperties.put("spring.mustache.cache", "false");
		devToolsProperties.put("server.session.persistent", "true");
		devToolsProperties.put("spring.h2.console.enabled", "true");
		devToolsProperties.put("spring.resources.cache-period", "0");
		devToolsProperties.put("spring.resources.chain.cache", "false");
		devToolsProperties.put("spring.template.provider.cache", "false");
		devToolsProperties.put("spring.mvc.log-resolved-exception", "true");
		devToolsProperties.put("server.jsp-servlet.init-parameters.development", "true");
		devToolsProperties.put("spring.reactor.stacktrace-mode.enabled", "true");
```
