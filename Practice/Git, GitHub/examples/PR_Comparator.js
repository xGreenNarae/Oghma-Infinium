/**
 * release branch(ex, main)에 PR이 들어왔을 경우,
 * develop branch(주간 모든 PR이 merge되는 브랜치)에 누적된 지난 release PR 이후의 PR 개수를 측정하는 스크립트
 * 
 * 마지막 release branch의 날짜를 기준으로(처음이라면 Date(+0) = 1970-01-01...)
 * 이후 develop branch에 쌓인 PR 개수를 측정한다.
 * 
 * 새로 발생한 PR이 100개를 넘는 경우는 예외사항
 * (GitHub API가 지원하지 않기 때문에 구현을 변경해야함. 불가능하지는 않으나, 필요하지 않을 것 같아서 구현하지 않음)
 * 
 * TOKEN을 사용하지 않을 경우, 시간 당 요청수가 60개로 제한된다고 한다.(사용 시 5000개)
 * 또한 당연히 private repository에 사용할 경우, 반드시 TOKEN 이 있어야한다.
 * 
 */

const { Octokit } = require("@octokit/core");

const HEADERS = { 'X-GitHub-Api-Version': '2022-11-28' }

const API = `GET /search/issues`;
const OWNER = `OWNER | ORGANIZATION`
const REPO = `REPO`

const TOKEN = '<GITHUB_TOKEN>';
const PAGINATION = 100; // max
const RETRY_WAIT_TIME = 5000; // 5 seconds
const RETRY_COUNT = 6;

const BASE_BRANCH = 'main'; // release branch 등
const DEVELOP_BRANCH = 'develop'; // 주간 작업의 모든 PR들이 merge되는 branch

const octokit = new Octokit({
  // auth: TOKEN
})


// PUBLIC ----------------------------------------------------------------------------


async function getLastMainPRDate() {
  let response = await retryRequest(API , {
    q: `repo:${OWNER}/${REPO} is:pr is:closed sort:created-desc base:${BASE_BRANCH}`, // 반드시 closed + created-desc 로 할것. 그렇지 않으면 심연을 들여다보게 된다.
    headers: HEADERS,
    per_page: 1, // 마지막 1개의 날짜만 필요하다.
  });

  if(response.data.items != undefined && 
    response.data.items != null && 
    response.data.items.length > 0){ // 깔끔하게 처리할 수 있는 방법을 알려주세요.
    return new Date(response.data.items[0].closed_at);
  }  
  return new Date(+0); // repo가 비어있는 경우, 1970-01-01을 반환한다.
}

async function getNewPRList(fromDate) {
  let response = await retryRequest(API, {
    q: `repo:${OWNER}/${REPO} is:pr is:closed sort:created-desc base:${DEVELOP_BRANCH} created:>${fromDate.toISOString()}`,
    headers: HEADERS,
    per_page: PAGINATION,
  });

  return response.data.items;
}


async function main() {
  const lastMainPRDate = await getLastMainPRDate();
  const newPRList = await getNewPRList(lastMainPRDate);

  console.log(`NEW PR COUNT: ${newPRList.length}`);
  lineBreak();
  for(item of newPRList) {
    console.log(`#${item.number} ${item.title}`);    
    console.log(`closed_at: ${new Date(item.closed_at).toLocaleString('ko-KR', { timeZone: 'Asia/Seoul' })}`); // Asia/Seoul Timezone

    lineBreak();
  }
}

main();








// PRIVATE ----------------------------------------------------------------------------




/**
 * cache가 안되어있을때, status는 202이기 때문에, error로 취급되지 않음에 주의.
 */
async function retryRequest(route, params) {
  let count = 0;
  let response = await octokit.request(route, params);

  while(response.status != 200 && count < RETRY_COUNT) {
    console.log(`${response.status}: wait for retry ...`);
    
    await new Promise(resolve => setTimeout(resolve, RETRY_WAIT_TIME));

    response = await octokit.request(route, params);
  }
  return response;
}

function lineBreak() {
  console.log("---------------------------------");
}

