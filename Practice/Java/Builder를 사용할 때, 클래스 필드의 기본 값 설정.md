


Class 레벨에 선언된 Builder는 보통 inner static class로 외부 클래스의 필드를 그대로 복사해가져가서 하나씩 만든 다음, 최종 값들을 모두 AllArgsConstructor에 넘겨주어 복사해서 만들어내는 것이다.
따라서, Class field를 가변 이니셜라이저로 초기화 한다고 해서 기본 값이 설정되지 않는다.
new로 생성할때(NoArgsConstructor 사용) 는 의미 있다.
```
Class {
	private type field = value; // 이런게 가변 이니셜 라이저
}
```

기본 값을 설정하려면, `@Builder.Default`등을 사용할 수도 있으나,
default값 세팅이 필요한 필드를 제외한 생성자를 직접 만들고 생성자 레벨에서 Builder를 사용하자.

```
public class MyClass {  
    private String name;  
    private int age;  
  
    @Builder  
    public MyClass(String name) {  
        this.name = name;  
        this.age = 1234;   // default
    }
}
```


AllArgsConstructor + Builder가 Class Level에 중복으로 선언되어 있는 경우 등, 어떤 Builder가 호출되고 있는지 주의하자. 찾기 어려움.