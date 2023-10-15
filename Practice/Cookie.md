브라우저와 쿠키의 동작


쿠키 설정(Server -> Client)
`Response Header`에 `Set-Cookie` 필드를 통해서 브라우저가 쿠키를 저장하게 할 수 있다.(중복필드가 여러개 들어갈 수 있음)
`cookie1=value1; Max-Age=86400; Expires=Mon, 16 Oct 2023 10:47:36 GMT; Path=/`
`path`는 해당 도메인에 요청을 보낼 때 쿠키가 자동으로 전송되는 `prefix`를 의미한다.

쿠키 전송(Client -> Server)
`Request Header`의 `Cookie` 필드를 통해 전달된다.(필드는 하나고, `;`으로 구분되어 키=값 쌍들이 전부 들어간다.)


쿠키에 공백 등이 포함되지 않도록 주의한다. (URL Encoding 하거나)

`Http Only Cookie`
Http Only Flag가 달려있는 경우, `document.cookie;` 로 접근이 불가능하다.

`Secure Cookie`
Chrome에서 현재 테스트 해봤을 때, TLS가 적용되지 않은 도메인에서 Secure Flag를 추가한 cookie가 발급이 가능하고, client에서도 자동으로 전송한다. (이유는 모르겠지만 안전하지 않음)