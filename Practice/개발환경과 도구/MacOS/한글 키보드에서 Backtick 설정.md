

기본적으로는 한글 입력 상태에서 backtick 키를 입력하면 \\(원화) 가 입력된다.(keymap 등에 난감하다)

`/Users/greennarae/Library/KeyBindings/DefaultkeyBinding.dict`  파일(없으면생성)에  다음을 추가한다.

```
{
	"₩" = ("insertText:", "`");
}
```

사용 중이던 애플리케이션은 재시작하면 적용된다.