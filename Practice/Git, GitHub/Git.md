#### git squash
- 여러 개의 이력을 하나로 합칠 때 사용.
- squash commit : 합치고자 하는 커밋의 부모 커밋으로 rebase, 남길 커밋을 제외하고 squash.
- squash & merge : merge 시에 squash 를 사용하는 것. 대략 깔끔해지는 대신, atomic rollback 이 불가능.  

사용법  
커밋이 다음과 같다고 하자.  
`qwer` 3rd commit  
`asdf` 2nd commit  
`zxcv` 1st commit  
`1234` previous commit  

1st, 2nd, 3rd 까지의 커밋을 정리하여 하나로 만들고 싶다면,  
`git rebase -i 1234`  
그러면 위에서부터 1st, 2nd, 3rd .. 로 역순으로 표기될것.  
없앨커밋은 pick -> s 로 바꾸고, 남길 커밋 하나만 pick을 두자.  
커밋 이름도 변경할수있다.  

---  

#### git push origin main의 의미  
`git push origin src:dest`의 경우, 로컬의 src브랜치에서 원격 저장소의 dest브랜치로 push 하는것을 의미한다.  
`git push origin main`의 경우, 로컬브랜치 이름이 생략되었기 때문에, 로컬브랜치와 원격저장소의 브랜치이름이 모두 main이라고 가정하고 수행되는 명령어이다.  
따라서 로컬에 main브랜치가 존재하지 않으면 에러가 발생한다.  

---  

#### .gitmodules 서브모듈  
특정 디렉토리 내용을 다른 GitHub의 repository를 참조하도록 만들 수 있다.  
예를들어 비밀번호 정보같은것을 별도의 private repository에 연결시키고 서브모듈로 관리한다면, 상위 repository는 public이더라도 이것을 내려받은 계정이 서브모듈 접근권한이 없다면 서브모듈 부분은 내려받을 수 없다. -> **이것은 결과적으로 좋은 practice는 아닌것으로 보인다.** 비밀번호는 GitHub에서 제공하는 Secret 서비스를 활용하도록 하자.  
maven repository 등 공식 저장소에서 제공하는 외부 의존성 모듈들은 maven, gradle 등으로 관리가 충분하다고 보이지만, 자체적으로 운용하는 repository에서 구현된 모듈들을 관리할때 좋다!  

---  

#### git branch 생성 + switch 를 하나의 커맨드로  

`git switch -c <new_branch_name>`
c 는 create라는 뜻.

---


#### git add -p , git commit -v

`git add -p` 작업한 내용물을 변경 단위(hunk)로 `y`, `n` 키를 통해 하나씩 스테이징하는 커맨드.

y와 n 이외에 다른 명령어들.
```
y - stage this hunk
n - do not stage this hunk
q - quit; do not stage this hunk or any of the remaining ones
a - stage this hunk and all later hunks in the file
d - do not stage this hunk or any of the later hunks in the file
g - select a hunk to go to
/ - search for a hunk matching the given regex
j - leave this hunk undecided, see next undecided hunk
J - leave this hunk undecided, see next hunk
k - leave this hunk undecided, see previous undecided hunk
K - leave this hunk undecided, see previous hunk
s - split the current hunk into smaller hunks
e - manually edit the current hunk
? - print help
```



`git commit -v` 커밋 이전에 변경사항을 한번 더 확인할 수 있게 해준다.(메시지 입력화면에서)


---


