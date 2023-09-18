#### Redirect 와 Location
브라우저는 300번 대 status code를 기준으로 redirect를 결정하는 듯. 이 때, Header의 Location 필드를 보고 redirect를 수행한다.
예를들어, Created(201) + Location Header는 redirect 를 수행하지는 않는다.  


### HTTP API  

#### GET/POST Method only + Verb URL
일단 RESTful API 의 가이드라인에 어긋난다고 알고 있다. 그럼에도 불구하고 이런 방식이 여전히 많이 사용되고 있다는 느낌(?)을 받아서 여기저기 조사해봤는데, 복잡한 웹 서비스 개발의 역사가 얽혀있는 듯 하다.

- HTTP 1.0 에서는 GET/POST 만 지원되었다고 한다. 이것과 관련한 레거시 코드 문제라기엔 너무 옛날.
- 위의 내용과 이어지는 지는 잘 모르겠지만, 국가사업과 연관하여 GET/POST 만 사용하라는 보안권고사항 같은것이 있었다는 이야기가 있다. - [ https://webstone.tistory.com/166 ] - 이 부분과 관련한 레거시 문제라면 어느 정도 유효할 것 같다는 추측으로 결론.

가능하다면 PUT/DELETE 를 사용하는 것 자체 (프로토콜 자체) 에는 문제가 없다고 함.

`http://server/getUsers` 와 같은 URL을 사용하는 것을 한 단어로 표현하고 싶다면 **URL을 이용한 RPC** 가 적절하다. - '뷰티풀 아키텍처'  
