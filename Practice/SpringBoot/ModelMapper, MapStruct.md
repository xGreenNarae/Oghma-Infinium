#### ModelMapper, MapStruct  
일종의 Domain객체와 Dto 간 변환을 편리하게 하는 라이브러리.  

**ModelMapper**  
리플렉션을 이용하여 일치하는 필드를 자동으로 매핑한다.  
매칭전략에 따라 조금 다른데.. [매칭전략 문서](http://modelmapper.org/user-manual/configuration/#matching-strategies)  
소스, 대상으로 구분하도록 하고  
Standard의 경우, 소스는 속성이름에 일치하는 토큰이 하나 이상 있어야한다. 대상은 모든 토큰이 일치해야한다. 순서는 상관없다. 쉽게 표현하면 "필드이름이 같은경우" 자동 매핑이 이루어진다.  
소스에는 Getter, 대상은 Setter가 필요함.  

**MapStruct**  
컴파일 시점에 어노테이션을 읽어 구현체를 만들어낸다. 리플렉션을 사용하지 않아서 상대적으로 속도가 빠르다고 함.  
주의할 점, Dependency 선언 순서에 Lombok보다 나중에 나와야 함.  


일단은, MapStruct의 경우 직접 Convert 코드를 구현하는것에 비해 코드 작성량에서 이점이 있다는 생각이 들지는 않는다.  
ModelMapper의 경우 간단한 케이스에 사용하면 편리할 수 있을듯.  

[ModelMapper 예제](../examples/entity-dto/src/test/java/com/example/entitydto/EntityToDtoTest.java)  

---  