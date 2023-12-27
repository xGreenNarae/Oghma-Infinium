https://johngrib.github.io/wiki/vim/ideavim/

설정은 `~/ideavimrc` 에 추가한다.
#### 탐색

`Shift + up/down` 으로도 스크롤이 지원된다 ?

`Vim-easymotion` 을 사용한다.(커서 점프)
Acejump 와 ideavim-easymotion 플러그인이 설치되어 있어야 한다고 함.

`set easymotion` 을 추가한다.
```
let mapleader=" "
nmap <Leader>a <Plug>(easymotion-jumptoanywhere)
```

#### Action
`set tai` 로 사용하는 action id 를 우측 하단에 popup 시킬 수 있다.

예시
```
nnoremap <Tab>w :action ReformatCode<CR>
nnoremap <Tab>e :action ShowIntentionActions<CR>
nnoremap <Tab>s :action CollapseAllRegions<CR>
nnoremap <Tab>d :action ExpandAllRegions<CR>
```


---

#### .ideavimrc 설정

```
set surround  
set easymotion  
set relativenumber  
  
let mapleader=" "  
  
## easymotion  
nmap <Leader>a <Plug>(easymotion-jumptoanywhere)  
  
## intellij actions keymap  
nnoremap <Tab>w :action ReformatCode<CR>  
nnoremap <Tab>e :action ShowIntentionActions<CR>  
nnoremap <Tab>s :action CollapseAllRegions<CR>  
nnoremap <Tab>d :action ExpandAllRegions<CR>  
  
  
## 선언으로 이동  
nnoremap <Space>gd :action GotoDeclaration<CR>  
  
```

파일트리, 터미널 토글 기능은 vim 상태에서 action을 호출하여 "켜는 것"은 가능하지만, 다시 끄는 것은 intellij의 영역이기 때문에, space등의 키가 아닌 ctrl, command 등의 키에 할당해야 함.