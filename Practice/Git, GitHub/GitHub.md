
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

#### GitHub API를 사용한 프로젝트 생산성 측정 시도

API 요청에 대해서, 사용자가 인증된 요청(with Bearer Token)이라면 시간 당 요청 수 5000, 인증되지 않은 요청은 60개의 제한이 있다.

**repository statistics**
GitHub API 문서에 따르면 
"비용이 많이 들어가는 통계 연산이 수행되기 때문에 가능하면 캐시된 응답을 반환하고자 한다. 따라서 캐시된 값이 만들어지지 않은 경우 202와 함께 빈 body응답이 반환될 수 있다. 일정시간을 기다린 뒤 다시 요청해야 한다."
해당 모든 통계의 경우 Merge commit이 제외되고, contributors 통계의 경우 empty commit이 제외된다고 한다.

**commit additions 와 deletions**
```
Hello World!
```
가 다음과 같이 수정되었다면
```
Hello
```
수정된 글자의 양과 관계없이, 해당 라인 1 개가 삭제되고 Hello 라는 새로운 라인이 추가된 것이다. 따라서 addtions: +1, deletions: -1 이다.

빈 줄을 하나 넣어 수 많은 라인을 아래로 한 줄 민다고 해서 전부 additions 로 취급되지는 않는다.
additions: +1 일 뿐이다.

**다만, 파일 이름을 변경할 경우, 해당 파일의 모든 라인이 additions와 deletions로 취급된다.**

GitHub API의 codeFrequency는 default branch의 변화 만을 참조하는 듯 하다.

[공식문서](https://docs.github.com/en/rest/metrics/statistics?apiVersion=2022-11-28#about-repository-statistics)

**위의 통계 지표들은 GitHub Repository의 Insights 탭에서 훌륭하게 시각화하여 확인할 수 있다.**


**PR 분석**
**검색 API 를 사용한다.**

[예제 코드](./examples/PR_Comparator.js)



**아래의 방법은 사용하지 않도록 한다. 비슷한 기능을 하는 또 다른 API 이지만, 기능이 불충분하다. GitHub API와 GitHub Search API 가 다르다!!**
```

`GET /repos/{owner}/{repo}/pulls`

query-params

state: 'closed',
base: 'develop', // PR을 받는 브랜치
per_page: 100, // 기본30, 최대100
page: 1 // 기본 1

기본 값으로 created날짜를 desc로 정렬한 페이지를 받는다.


body 의 유의미한 데이터는..

[
  {
    "id": 1527456082,
    "number": 45,
	"created_at": "2023-09-23T16:14:48Z",
    "updated_at": "2023-09-23T16:14:53Z",
    "closed_at": "2023-09-23T16:14:53Z",
    "merged_at": "2023-09-23T16:14:53Z",
   },
   ...
]


pull_number 를 이용해서 commit 개수를 확인할 수 있다(최대 250개). 이것을 이용해서 PR 개별 규모를 측정하는 시도도 해볼 수 있겠음..

```