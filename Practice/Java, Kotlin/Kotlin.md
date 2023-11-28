
**.kt vs .kts**
후자는 script파일을 의미한다. bash/python 등 과 같이 한 줄씩 인터프리트되어 실행되는, 컴파일이 필요 없는 파일.
`kotlinc -script <filename>.kts` 로 실행

---

**Any, Unit, Nothing**
Any는 Java의 Object에 해당한다.

Unit은 void에 해당한다. (싱글톤이다는것이 차이라고 함)

Nothing은 return문 자체가 없음을 의미한다.
- "무한"루프
- throw Exception (주로 이것을 위해 사용하라는 의미 같음)

Void는 자바 호환을 위해 존재하는 것이고, 일반적으로 사용할 일이 없다고 함.

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

List는 기본적으로 불변(추가, 삭제 등 불가)
MutableList 라는 자료형이 변경 가능.
val, var의 참조 변경을 말하는 것은 아님.


---

**클래스**

```
class MyClass(var value1) { // 여기는 생성자고, 여기 value1도 속성이다.
	val value2 = null // 이것도 속성
}

class MyClass2(private val v1) // 접근제한자 쓸수있다는 뜻
```


참고로 데이터 클래스는 이렇다.
```
data class MyDataClass(
	val value // 모든 속성이 주 생성자에 정의 되는것
)
```

---

Function parameter는 immutable이다. (var, val 등 선언 불가)

Class Constructor에서는 var, val 선언 가능(이 쪽은 어떤 큰 이유가 있는 것 같지는 않음..)

---

**Elvis Operator**

`?:`
연산자가 엘비스 프레슬리 헤어를 닮았다는데..

null 일 경우, default 값을 준다는 의미이다.
```
fun getName(str: String?) {
    val name = str ?: "Unknown"
}

// 같은 의미
if (str != null) str else "Unknown"
```

