
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

`d$`
현재 커서부터 줄 끝까지 삭제

^와 $는 `<Space>h, <Space>l` 에 remap 해서 사용한다.

#### Vim-surround
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

---

`set relativenumber`
j, k 등으로 원하는 라인 맨 앞으로 커서를 이동하기 위한 상대숫자 라인넘버 설정

`set nornu` 로 일시적으로 끌 수 있다. (debug trace 확인 등)



#### key map
```

inoremap kj <Esc>l

## navigation 기능 보완 
nnoremap H ^
nnoremap dH d^
nnoremap cH c^
vnoremap H ^

nnoremap L $
nnoremap dL d$
nnoremap cL c$
vnoremap L $

```




---


**설정 구문의 오류. 주의할 점**
예를들어,
`"inoremap kj <Esc>"`

여기서, `"inoremap kj <Esc> "` 와 같이 맨 뒤에 공백이 들어가있으면
키 입력 후 커서가 한 줄 아래로 내려가있는 문제가 발생한다.