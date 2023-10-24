
#### git rebase -i HEAD~n
최종 커밋을 포함하여 n개의 최근 커밋을 대화형으로 수정하겠다.

**git squash**
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
그러면 위에서부터 1st, 2nd, 3rd .. 로 역순으로 표기될것.(**가장 아래있는것이 최근커밋**) 

순서를 바꿔도 되고.. 커밋 이름도 변경할수있다.  
없앨커밋은 pick -> s 로 바꾸고, 남길만 pick을 두자.(맨 위로 올릴 것)  

대화형 작업 도중 문제가 생겼다면, `git rebase --abort` 로 취소한다.

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


#### 커밋 사이즈를 검사하는 스크립트

[원본](https://github.com/baekdev/git-stat)

윈도우 용 .bat 파일
```
@echo off
setlocal enabledelayedexpansion

REM Counters for insertions and deletions
set plus=0
set minus=0
set count=0

REM Range of change size
set rangeTo[XS]=10
set rangeTo[S]=50
set rangeTo[M]=100
set rangeTo[L]=250

REM Color Variables
set "BLACKC=[30m"
set "REDC=[31m"
set "REDB=[41m"
set "GREENB=[42m"
set "YELLOWB=[43m"
set "BLUEC=[34m"
set "BLUEB=[44m"
set "MAGENTB=[45m"
set "NORMALB=[49m"
set "NORMALC=[0m"

REM Calculate the changes
for /f "tokens=1,2" %%a in ('git diff --cached --numstat') do (
    set /a plus+=%%a
    set /a minus+=%%b
    set /a count+=1
)

REM Output the results
echo ^>^>^> Git Local Changes Stat

if %plus% leq 0 if %minus% leq 0 (
    echo ^>^>^> Any no changes
    exit /b 0
)

set /a sum=plus+minus
set size=XS
set color=!GREENB!

if !sum! lss !rangeTo[XS]! (
    set size=XS
    set color=!GREENB!
) else if !sum! lss !rangeTo[S]! (
    set size=S
    set color=!BLUEB!
) else if !sum! lss !rangeTo[M]! (
    set size=M
    set color=!YELLOWB!
) else if !sum! lss !rangeTo[L]! (
    set size=L
    set color=!MAGENTB!
) else (
    set size=XL
    set color=!REDB!
)

echo ^>^>^> !count! files, !REDC!!plus! insertions(+), !BLUEC!!minus! deletions(-)!NORMALB!!NORMALC!

echo ^>^>^> !color!Your commit size is !size!!NORMALB!!NORMALC!
```


`git config --global --edit` 을 실행하여 스크립트 절대 경로를 다음 처럼 추가하여 사용하였음.

```
[alias]
  stat = !C:/\"Program Files\"/Git/script/git_stat.bat
```

---

#### git blame

`git blame 파일명` 으로 해당 파일의 수정 기록을 확인할 수 있다.
여러가지 옵션들이 있는데..
이런 목적으로 유용하게 써먹을만한게 커맨드라인 도구보다는 IntelliJ 의 경우, `파일 우클릭 - Git - 기록표시` 로 깔끔하게 확인할 수 있다. 

이름이 `blame`인 이유는 제작자의 스타일이 반영된 것으로 보임.


---

#### git hooks 

commit 등 git 명령어가 실행되는 시점 전후에 스크립트 실행.
`.git/hooks/` 하위에 sample파일들이 있다.
`pre-commit`이라는 이름으로 파일을 두면 commit 시점에 자동 실행됨. 
예제로 copilot이 자동생성해대는 trailing spaces를 제거한다.
```
#!/bin/sh

git diff --cached --name-only | while read file; do
	sed -i 's/[ \t]*$//' "$file"
	git add "$file"
done
```

python에 `pre-commit 이라는 도구로 미리 만들어진 스크립트들을 공유하는데, 설치하고 yaml설정파일로 가져다 쓰면 된다.
[링크](https://github.com/pre-commit/pre-commit-hooks)

