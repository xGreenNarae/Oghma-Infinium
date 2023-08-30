#### git squash
- 여러 개의 이력을 하나로 합칠 때 사용.
- squash commit : 합치고자 하는 커밋의 부모 커밋으로 rebase, 남길 커밋을 제외하고 squash.
- squash & merge : merge 시에 squash 를 사용하는 것. 대략 깔끔해지는 대신, atomic rollback 이 불가능.  

---  

#### git push origin main의 의미  
`git push origin src:dest`의 경우, 로컬의 src브랜치에서 원격 저장소의 dest브랜치로 push 하는것을 의미한다.  
`git push origin main`의 경우, 로컬브랜치 이름이 생략되었기 때문에, 로컬브랜치와 원격저장소의 브랜치이름이 모두 main이라고 가정하고 수행되는 명령어이다.  
따라서 로컬에 main브랜치가 존재하지 않으면 에러가 발생한다.  

---  

#### .gitmodules 서브모듈  
특정 디렉토리 내용을 다른 GitHub의 repository를 참조하도록 만들 수 있다.  
예를들어 비밀번호 정보같은것을 별도의 private repository에 연결시키고 서브모듈로 관리한다면, 상위 repository는 public이더라도 이것을 내려받은 계정이 서브모듈 접근권한이 없다면 서브모듈 부분은 내려받을 수 없다. -> 이것은 결과적으로 좋은 practice는 아닌것으로 보인다. 비밀번호는 GitHub에서 제공하는 Secret 서비스를 활용하도록 하자.  
maven repository 등 공식 저장소에서 제공하는 외부 의존성 모듈들은 maven, gradle 등으로 관리가 충분하다고 보이지만, 자체적으로 운용하는 repository에서 구현된 모듈들을 관리할때 좋다!  

---  

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

#### Software Version  
Major.Minor.Patch+Build  

Major : 기존버전과 호환되지 않으며, API가 변경됨.  
Minor : 기존버전과 호환되며, 새로운 기능이 추가됨.  
Patch : 기존버전과 호환되며, 버그가 수정됨.  

첫 배포 이전에는 0.1.0 으로 시작하도록 하고..  
첫 릴리즈부터 1.0.0 으로 한다.  
1.0.1 등으로 패치가 이루어지고 있는 상태에서 Minor버전이 증가하였을 경우, 1.2.0 으로 한다. 마찬가지로 Major버전이 증가하였을 경우도 동일.  

GitHub 릴리즈 버전이 변경될 경우, Gradle등의 프로젝트관리도구에 명시된 버전도 같이 변경해주도록 유의하자.  

---
 




	
			