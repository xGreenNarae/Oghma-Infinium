
#### GitHub Project  

모노 레포에서 backend, frontend 디렉토리를 각각 두고 운용할 경우, 하나의 타임라인으로 진행되는 작업으로 보고, 롤백 시 서로의 작업이 같이 롤백되는 것은 지극히 정상이라고 봐야 맞겠다.  

**초기 설정**  
Project Kanban Board 설정.    
Label 설정. Label은 이슈나 PR들의 주제를 한 눈에 구분하기 쉽게 만드는 목적이 큰 듯하다. 너무 다양할 필요는 없어보인다.  
Kanban을 3단계(Backlog - In progress - Done) 으로 운영할 경우, "제안"이 ISSUE로 취급되면 Backlog에 들어가게 된다. 이 경우 아직 논의가 완료되지 않은 제안과 실제 해야할 작업 사이의 구별이 어려워질 수 있으므로, "제안"은 Discussion을 이용하는 것이 좋겠다. 또는 Kanban의 단계를 늘리거나.  
ISSUE TEMPLATE은 Repository Settings에서 설정할 수 있다.  
PR TEMPLATE은 root directory의 `.github` 폴더 아래 `pull_request_template.md` 파일로 직접 만들어야 하는 것 같다.  

**작업 프로세스**  
ISSUE 생성은 Kanban에서 하도록 한다.  
이후 Assignee를 직접 또는 논의를 거친 뒤 할당하고 In progress로 옮긴다.  
Kanban에서 이슈의 체크리스트를 해결해가며 작업을 진행한다. 체크리스트를 commit-message 단위로 계획해두면 커밋단위를 작게 유지하는데에 도움이 될 수도 ?  

---  

#### PR을 로컬로 가져오기  
일단 브랜치를 하나만들어서 가져올테니 브랜치를 하나만들어서 switch 하고..  
해당 PR이 20번이라면,  
`git pull origin pull/20/head:main`  

---  

#### GitHub 권한, Branch Protection..    
오픈소스 방식..  
Contributor는 자유롭게 repository를 fork하여 작업 이후 PR을 제출할 수 있고, Collaborator들은 이 PR을 merge 할 수 있다.  

개인 계정으로 만든 public/private repository에서는 저장소 생성자가 admin 역할을 갖는다. 이외에 collaborator들의 역할을 지정하건 변경할 수 없다.  

organization으로 만들 경우, role을 지정 및 변경 할 수 있다.  

settings - branch 에서 간단한 protection 설정을 할 수 있고, 이외의 역할이나 팀 별 권한 차등을 강제하기 위해서는 ruleset을 지정해야 한다.  

**Require signed commits**  
대략.. GitHub에 올라오는 것들은 실제로 로컬에서 GitHub계정 주인이 commit한 것인지를 보장할 수 없다고 함. 따라서 로컬에서 gpg key.. 를 이용해 서명하는 것(GitHub에도 key가 등록되어야 함). GitHub에서 Verified 표시를 확인할 수 있다고 한다.    

**Require status checks to pass before merging**  
merge 이전에 특정 Actions Job등을 반드시 pass해야하는것을 강제하는 것이라고 한다.  

**Require linear history**  
Merge commit을 금지하고, Squash and merge와 Rebase and merge만 허용한다. history를 하나의 라인으로 단순하게 유지하며 쉽게 추적할 수 있게 하기 위함이라고 하는데..    

**Do not allow bypassing the above settings**  
적용하지 않으면 위의 제한사항들에 경고를 표시해주지만 실행은 허용하게 된다.(PR없이 PUSH 등..)  

---  

#### 원격 브랜치 삭제  
`git push <remote-repo-alias> -d <branch-to-delete>`  

---

#### GitHub 에서 특정 파일의 변경기록을 빠르고 편하게 확인하는 법  
파일을 보고있는 주소의 도메인을 `github-history.netlify.app` 으로 바꾼다.   

---

#### gitignore 에서 ! 문자는 not을 의미한다. 예를들어 어떤 패턴을 적용시켜두었으나 예외적으로 git에 올리고 싶은 파일패턴은 !를 써준다.

---

