
[Vim documentation](https://vimdoc.sourceforge.net/htmldoc/index.html)
.. 기본 key map을 확인 등
`:help` 를 입력하면 됨.


#### 탐색 

**스크롤**
**`Ctrl + B, F`** page up/down

**`w, b`**
단어 단위로 캐럿 앞 뒤 이동
기존 Option + left/right 키를 대체할 수 있다.

**`Ctrl + ^, $`**
기존 Home/End 키를 대체할 수 있다

`set relativenumber`  를 활성화하고,
9k, 3j 등으로 행간 이동을 쉽게 할수 있음.

---
#### 입력

**입력 시작**
```
O
I i a A
o
```

**수정**
**`c (change)`**
`ci` change inside

`ciw` 현재 캐럿이 위치한 단어 수정
`c$` 현재 줄 전체 수정
`ci(, ci[, ci{, ci', ci" .. ` 각 괄호, 따옴표 등의 내부 수정

**삭제**
동일하게 **d (delete)** 에도 적용가능.

라인 시작과 끝으로 가는것은 `^, $` 키 인데, 이 중에 시작으로 가는 키는 `0` 도 지원한다.
커서부터 라인 시작, 끝으로 삭제/수정 등은
`c0, d0`, `C, D` 

`set relativenumber`
j, k 등으로 원하는 라인 맨 앞으로 커서를 이동하기 위한 상대숫자 라인넘버 설정

`set nornu` 로 일시적으로 끌 수 있다. (debug trace 확인 등)



#### vimrc key map 예시
```
nnoremap H ^
vnoremap H ^
```
n(normal), v(visual) 모드에서 각각 no recursive 로 H키를 ^키에 매핑한다. (trailing space가 없도록 주의)



---


**설정 구문의 오류. 주의할 점**
예를들어,
`"inoremap kj <Esc>"`

여기서, `"inoremap kj <Esc> "` 와 같이 맨 뒤에 공백이 들어가있으면
키 입력 후 커서가 한 줄 아래로 내려가있는 문제가 발생한다.

참고로, esc키는 caps lock 에 switch하여 쓴다.

---

#### 시스템 클립보드로 복사

`+y`

---

#### 일치하는 문자열을 여러개 수정해야 할 때
`:%s/old/new`


---

#### 윈도우에서 다른 파일 열기

`:e <file-path>`

---

#### `<C-o>, <C-i>`
back , forth 커서 이동(easymotion, search, go to definition 등으로 jump했을 시)

---

#### Replace mode

`R`
`gR` : virtual replace mode (tab 문자도 입력 가능)

---

#### Visual mode

`v`: 글자 단위 선택
`V`: 줄 선택
`<C-v>` block 선택 모드

`gv` 이전 선택 복구

**들여쓰기**
`<<`, `>>` 등을 사용한다.
반복이 필요한 경우 `.` 을 사용하고,


---
#### Macro

`qq` 로 매크로 기록 시작
모든 명령어 입력을 기록한다.
`q`로 기록 종료
`@q`로 매크로 실행 (`10@q`면 10번 반복)


---
#### Plugins

**surround**
`', ", (, {, [` 등으로 감싼다.
`ideaVim`에서는 `set surround` 구문을 추가하여 활성화 한다.

`ysiw"` 
" 로 word inside를 감싼다.

`yss"`
한줄 전체 감싸기

`S"`
v모드에서 선택영역만 감싼다.

`cs({` 
( 로 감싸진 영역을 { 로 변경한다

`ds"` 
감싸진 태그 제거

여는 태그`{` 대신 닫는 태그`}` 를 사용하면 태그 내부 공백 없이 작동한다. 

**easymotion**
lightspeed, leap.nvim ..
커서 점프


**argtextobj**
function argument 를 조작한다.
`daa` delete an argument
`cia` change inner argument
`via` select inner argument

설정 값으로 중첩 함수의 argument를 조작할 때 동작을 조정할 수 있다.















