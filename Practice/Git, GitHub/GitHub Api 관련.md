
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

---

#### 사용자 언어 통계 분석

GitHub는 [Linguist](https://github.com/github-linguist/linguist)라는 오픈소스 라이브러리를 사용하여 Git 저장소의 언어 통계를 분석한다.
프로그래밍 언어 또는 마크업 언어가 포함된 파일들의 라인 수와 비율을 계산하는 것 같다.
(파일이 10만개 미만인 repository에서만 작동한다고 함)

위 방식으로 미리 계산된 통계를
GitHub REST API에서 다음과 같은 링크로 제공한다.
https://api.github.com/repos/xGreenNarae/Oghma-Infinium/languages

```
{
  "Java": 29133,
  "JavaScript": 3539
}
```

언어 사용량 비중을 repo별, commit별로 제공하는 훌륭한 서비스가 있어서,
이것을 이용하도록 한다.
https://github.com/vn7n24fzkq/github-profile-summary-cards

![](http://github-profile-summary-cards.vercel.app/api/cards/repos-per-language?username=xGreenNarae&theme=default) 
![](http://github-profile-summary-cards.vercel.app/api/cards/most-commit-language?username=xGreenNarae&theme=default)

---

#### Rate Limit
https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-ap. ?apiVersion=2022-11-28

인증되지 않은 사용자는 시간당 60.
토큰을 같이 쓰면, 시간당 5000
엔터프라이즈 계정은 시간당 15000 이다.

`GET https://api.github.com/rate_limit` 
엔드포인트에서 잔여량을 확인 할 수 있다.

