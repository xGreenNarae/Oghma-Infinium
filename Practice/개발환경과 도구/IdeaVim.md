https://johngrib.github.io/wiki/vim/ideavim/

설정은 `~/ideavimrc` 에 추가한다.
#### 1. 탐색

**스크롤**
**`Ctrl + B, F`** page up/down
또는 `Shift + up/down`

`Vim-easymotion` 을 사용한다.(커서 점프)
Acejump 와 ideavim-easymotion 플러그인이 설치되어 있어야 한다고 함.

`set easymotion` 을 추가한다.
```
let mapleader=" "
nmap <Leader>a <Plug>(easymotion-jumptoanywhere)
```

easymotion 없는 수동 이동 시
**`w, b`**
단어 단위로 캐럿 앞 뒤 이동
기존 Option + left/right 키를 대체할 수 있다.

**`Ctrl + ^, $`**
기존 Home/End 키를 대체할 수 있다

`set relativenumber`  를 활성화하고,
9k, 3j 등으로 행간 이동을 쉽게 할수 있음.


#### 2. 입력

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

#### 3. Action
`set tai` 로 사용하는 action id 를 우측 하단에 popup 시킬 수 있다.

예시
```
nnoremap <Tab>w :action ReformatCode<CR>
nnoremap <Tab>e :action ShowIntentionActions<CR>
nnoremap <Tab>s :action CollapseAllRegions<CR>
nnoremap <Tab>d :action ExpandAllRegions<CR>
```


---

#### 4. Vim-surround
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


