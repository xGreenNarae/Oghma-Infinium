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
