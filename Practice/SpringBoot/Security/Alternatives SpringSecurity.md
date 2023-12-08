
Spring Security가 좀 복잡하다.

개인용 프로젝트 등에 쓸만한 Spring Security 대안


**Sa-Token**
https://github.com/dromara/Sa-**Token
"굉장히" 코드가 간결하고, 사용이 쉽고 문서가 잘 되어 있다.
LDAP, SOAP 등의 포괄적인 보안 요소를 다루고 있는 Spring Security와 달리, 세션과 토큰 등을 사용하는 HTTP 인증, 권한 처리에 집중되어 있다. OAuth 등의 지원도 훌륭한 것으로 보임.

예를들어, Spring Security에서 내가 생각하는 일반적이고 가장 간단한 인증 구현은 stateless 모드로 JWT를 Authorization Header로 주고 받는 방식인데,
Sa-Token에서는 Set-Cookie와 uuid 토큰을 사용한 


**Apache Shiro**
상대적으로 설정이나 API 사용이 쉽다고 함.
OAuth의 경우 지원이 부족할 수 있다.
현재로써는 SpringSecurity 대신 사용할 이유를 찾지 못함.