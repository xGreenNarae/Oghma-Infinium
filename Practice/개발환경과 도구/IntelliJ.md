#### IntelliJ가 코드를 읽지 못하는 것 같을 때
코드의 키워드 별로 Color가 뜨지 않고 전부 흰색이라거나,
자동완성 등이 작동하지 않는 경우.

Java, SpringBoot를 사용하고 있는 환경이라면 
source root directory 설정을 해서 해결되는 경우가 있다.
이 경우, `src/main/java` 가 되겠다.  

Dart, Flutter 환경에서도 동일한 문제가 발생했다.
캐시 무효화 이후 재시작으로 해결되었음.

---  

#### Java에서 모든 가능한 변수에 final을 붙이고 싶다면  
Settings - 에디터 - 검사 - 코드스타일 이슈 기능을 사용하자.  
이외에도 좋은 검사 옵션들이 많이있음.  

---

### 나의 IntelliJ 설정


**Font**
Monaspace Neon 을 사용한다.

#### Layout
`설정 - 모양 및 동작 - 모양 - 도구 창 - 와이드스크린 도구 창 레이아웃`
VSCode와 동일하게 하단 터미널영역의 "가로길이"가 좌측 프로젝트 윈도우를 침범하지 않게 한다.

#### 사용하는 Plugins
**일반**
`Material Theme UI` + `Atom Material Icons` 파일 종류 별로 프로젝트 윈도우에서 아이콘을 다르게 해서 가독성을 높임
테마는 `Atom One Dark (Material)`
`CodeMetrics` 코드 복잡도를 보여준다
`GitHub Copilot` 코딩 도구
`Key Promoter X` 크게 유용한가는 모르겠지만, 수동 액션을 취했을 때 사용가능한 단축키를 알림을 띄워준다.
`SonarLint` 정적 분석, 권장 사항 보고
`VSCode Keymap` VSCode와 단축키 통일에 도움이 된다.
`Fast Scroll` 마우스 휠 스크롤을 빠르게해줌(Ctrl+휠)

**Java, Spring 관련**
`JPA Buddy` JPA를 사용한다면 훌륭한 도구
`Maven Helper`, `Package Search` 둘 중에 하나는 불필요한건지 모르겠는데.. 아무튼 설치된 라이브러리들의 최신 버전들을 확인할 수 있게 해줌.

#### Java, SpringBoot 관련 기타 설정
`에디터`
- `일반`
	- `자동 가져오기 - import문 추가 및 최적화`
	- `모양 - 메서드 구분 기호 표시`
	- `코드접기` default: 파일헤더, import문
- `색 구성표` Atom One Dart (Material)
- `코드 스타일 - Java - 정렬` 
	- 최하단에 `메서드 public protected private` 순서로 추가한다. `코드 서식 다시 지정` 실행 시 메서드를 접근제한자 순으로 정렬해준다.
- `검사` 기본 값 이외에 추가한 것들 (가벼운 경고)
	- 적당히 현재 내 수준에 필요한 것만 쓰는게 좋아보인다. 너무 당연해서 검사할 필요가 없어 보이는 것들도 많고, 아예 무슨 맥락인지 파악도 안되는 이야기들도 있어서..
	- `데이터흐름` (고려)
		- `데메테르의 법칙`
		- `부울 변수가 항상 반전됨`
	- `로그 관련` (약한 경고)
		- `로깅 호출에 대한 인수로서의 비상수 문자열 연결`
	- `메서드 메트릭` 전체 + 기본 숫자 (약한 경고)
	- `숫자 문제` 경고
		- `BigDecimal에서 equals() 호출`
		- `부동소수점 상등 비교`
	- `이름 생성 규칙` (고려)
		- `메서드`
			- `매개변수 수가 동일한 오버로드된 메서드`
			- `부울 메서드 이름은 질문 단어로 시작해야 함`
			- `부울이 아닌 메서드 이름은 질문 단어로 시작하면 안 됩니다.`
		- `클래스` 
			- `예외 클래스 이름이 Exception 으로 끝나지 않음`
		- `의심스러운 이름`
	- `코드 성숙도` (약한 경고)
		- `곧 사용할 수 없게 될 date-time API 사용`
		- `곧 사용할 수 없게 될 컬렉션 타입 사용`
	- `코드 스타일 이슈` (약한 경고)
		- `비교 시 잘못된 쪽에 있는 상수
		- `지역 변수 또는 매개변수는 final이 될 수 있음`
	- `클래스 구조`
		- `인터페이스에 @FunctionalInterface로 어노테이션 추가 가능`
	- `클래스 메트릭` 전체

`빌드, 실행, 배포`
- `빌드 도구 - Gradle - 다음을 사용하여 테스트 실행 IntelliJ IDEA` (IDE에서 테스트 결과 한글 깨지는 것 처리)


#### 도구
`저장 시 액션`
- `코드 서식 다시 지정, import문 최적화, 코드 재정렬`

---

#### 단축키

**windows**
`Ctrl + Q, W` : 코드 서식 다시 지정 (기본 `Ctrl + K, F`)

`Ctrl + Q, E` : 컨텍스트 액션 표시 (기본 `Ctrl + .`)

`Ctrl + Shift + Q` : 코드 모두 접기
`Ctrl + Shift + W` : 코드 모두 펼치기

```
Ctrl + ` : 터미널 토글. (하단 뷰 숨기기도 같은 단축키에 매핑해두면 편리하다)
```

**mac**
`Option + Q, W` 코드서식 다시지정
`Option + Q, E 컨텍스트액션표시
`Option + Q, S` 코드모두접기
`Option + Q, E` 코드모두펼치기

`command + backtick`  터미널
`command + 1` project explorer


`ctrl + option + arrow` 터미널, 프로젝트 윈도우 크기조정  

추가로 줄을 위/아래로 이동 키를 `option + up/down` 에서 `command + up/down` 으로 바꾼다.


---

#### UI (프로젝트 탐색기 등) 줄 간격, 들여쓰기 간격 수정

도움말 - 사용자 프로퍼티 수정 항목에서
`idea.properties` 파일을 수정한다.
`idea.is.internal=true` 를 추가하고 IDE를 재시작하면
`command + shift + a` - `LaF 디폴트 값 수정` 에서
`Tree.left/right ChildIndent`, `Tree.rowHeight` 등을 수정할 수 있다.
줄 간격의 경우, MaterialUI 등의 플러그인에서 수정할수도 있음.