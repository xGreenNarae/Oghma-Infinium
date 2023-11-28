
**Any, Unit, Nothing**
Any는 Java의 Object에 해당한다.

Unit은 void에 해당한다. (싱글톤이다는것이 차이라고 함)

Nothing은 return문 자체가 없음을 의미한다.
- 무한루프
- throw Exception

---

**기본적으로 named parameter 제공(Builder 대체)**
```
val request = ExampleRequest(  
    id = 1,  
    name = "hello"  
)
```


---

package name이 kotlin이면(경로중에 있다면) gradle build 시 에러를 발생 시키는 것 같다.
예: `package kotlin.test`

---






