
**요약**
Object가 Jackson에 의해 Serialize 될 때, 필드는 다음으로 정의된다.
- get으로 시작하는 method가 있어야 한다.
- public 접근제한자를 가져야 한다.
- return type이 존재해야 한다.(void가 아닌)


Gson은 Reflection으로 field를 읽고 만들어서 그렇지 않다고 함.
(좋은 것인가는 별개의 문제)



따라서, Springdoc이 문서를 만들어버리는 최악의 경우를 살펴보자.
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
[Springdoc 문서에서 그나마 조금이라도 관련 정보를 유일하게 찾은 것](https://springdoc.org/faq.html#_how_can_i_extract_fields_from_parameter_object)


즉, get 접두사를 가진 method를 생성할 때 주의하자.
꼭 해야 한다면 `@Hidden` 을 메소드에 달아서 숨겨주거나,
visibility를 public이 아닌 것으로 변경하자.
request, response 모두 동일하고, `implements Serializable`을 따로 달아준다고 해서 달라지지는 않는다.