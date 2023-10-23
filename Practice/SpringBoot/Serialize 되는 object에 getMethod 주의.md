
#### Dto, record 등의 Get prefix를 갖는 method에 주의할 것

Springdoc이 문서를 만들어버리는 최악의 경우를 살펴보자.

Controller의 Method에 명시된 Parameter는 전부 읽어서 request example json 을 띄워주는데,
이 때 해당 parameter object(또는 record이거나) 의 "field"를 읽어서 만든다기보다 "getter method"를 읽어서 만들어 내는 것 같다.(정확하지 않음)
[그나마 조금이라도 관련 정보를 유일하게 찾은 것](https://springdoc.org/faq.html#_how_can_i_extract_fields_from_parameter_object)

따라서 
```java
@Getter  
@Builder  
public class SomeDto {  
  
    private Integer id;  
    private String message;  
  
  
    public int myMethod() {  
        return 50;  
    }  
    public int getSomething() {  
        return 100;  
    }  
}
```
이런 코드가 있을 경우, Request Example Value는 이렇게 등장한다.
```json
{
  "id": 0,
  "message": "string",
  "something": 0
}
```

즉, get 접두사를 가진 method를 생성할 때 주의하자.
꼭 해야한다면 `@Hidden` 을 메소드에 달아서 숨겨줄 수 있다.

이것은 Springdoc의 예시이고, Serialize 될때 get prefix를 가진 method를 "field" 로 인식한다는 것이다. request, response 모두 동일하다.