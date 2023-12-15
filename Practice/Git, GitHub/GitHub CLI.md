[Manual](https://cli.github.com/manual)


`brew install gh`

`gh auth login` 으로 로그인한다.

remote repository가 연결되어 있을 경우, `--repo OWNER/REPO` 를 생략할 수 있다고 함.(이외에는 명시)

다양한 서브커맨드들에 대해서 `--help` 를 모두 지원한다고 한다.

`gh config set editor "code -w"`
이슈, PR등 생성 시 본문 입력에 대한 기본 편집기를 vscode로 설정
`-w` 옵션은 해당 창이 닫힐때 까지 wait한다는 뜻이라고 한다.


**이슈**
`gh issue list`
이슈 리스트 확인
default로 state가 open인 것들만 보여준다.

`gh issue view <issue-number>`
해당 이슈 내용 확인(markdown을 터미널에서 해석하여 보여준다)
comment도 같이 보여준다.

`gh issue comment <issue-number> -b "<comment-content>"`
comment 달기.




**Pull Request**
`gh pr create`
PR을 생성한다.
`-B, -base` 로 PR의 target 브랜치를 지정
지정하지않으면 repo의 default 브랜치로 향하는 것 같다.
주의할 것은 B를 대문자로 써야한다.

`-H, -head` 로 source 브랜치 (기본: 현재브랜치)
head 브랜치정도만 지정해줘도 
base 브랜치 지정, 타이틀 입력, 본문 입력(템플릿선택) 등등 대화형으로 알아서 잘 진행되고 중간에 취소도 가능.

`list, view, comment` 등 issue와 동일

`gh pr diff <pr-number>`

`gh pr checks <pr-number>`  
CI(GitHub Actions Job) 수행 상태를 확인한다.

`gh pr checkout <pr-number>`
pr 내용을 로컬로 가져와서 확인한다.(로컬 브랜치 생성)

이외에 merge, close 등..

`gh pr review` 가 있는데, 특정 라인에 코멘트를 붙이는 것은 아니다. approve, request changes 등을 찍는다는 뜻.


`gh status`
나에게 할당된 이슈, requested reviews 등.. 확인


이외에 GitHub Actions Job을 trigger 할 수 있고,
GitHub API 를 사용할수 있고 등등..


---

#### Copilot Cli

github cli 에서는 copilot 을 사용할 수 있다.
https://docs.github.com/en/copilot/github-copilot-in-the-cli/using-github-copilot-in-the-cli

현재는
`gh copilot suggest`
`gh copilot explain`
2가지 명령어를 제공한다.