
#### nullable vs late
late는 더 안전한 문법이다.  null이 들어갈 수 있으나(초기에) **사용** 시점에는 null을 허용하지 않겠다는 의도를 가지고 있다(에러 발생).



**Dart에 없는 것**
- import 구문의 wildcard 사용
- 클래스 추출
- 파일, 디렉토리 구조를 변경할 때 자동 리팩토링(import 경로 변경)이 원활하게 수행되지 않는 경우가 있음.


**Default Parameter Value**
```
const _MyWidget({  
  required this.title,  
  required this.onPressed,  
  this.width = 200,  // default value
  this.height = 80,  
});
```
