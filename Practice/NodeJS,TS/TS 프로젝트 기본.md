
`tsc` 명령어는 기본적으로 전달받은 인자의 파일들을 js로 컴파일한다.

`tsconfig.json` 이라는 파일이 설정파일인데, `tsc --init` 으로 기본생성해주고..

여기서
`include, exclude, files, outDir` 등의 속성으로 어떤 파일들을 어디에 컴파일할지 결정한다.

```
1. `"target"`: 컴파일된 JavaScript 코드가 어떤 ECMAScript 버전을 따를지 지정합니다.
2. `"module"`: 사용할 모듈 시스템을 지정합니다 (예: `commonjs`, `es2015` 등).
3. `"strict"`: 모든 엄격한 타입-체킹 옵션을 활성화합니다.
4. `"esModuleInterop"`: CommonJS와 ES 모듈 간의 상호 운용성을 개선합니다.
5. `"outDir"`: 컴파일된 파일들이 저장될 디렉토리를 지정합니다.
6. `"sourceMap"`: 소스 맵을 생성하여 디버깅을 용이하게 합니다.
7. `"noImplicitAny"`: 암시적 'any' 타입을 허용하지 않도록 설정합니다.
8. `"strictNullChecks"`: `null`과 `undefined`를 엄격하게 체크합니다.
9. `"moduleResolution"`: 모듈 해석 방식을 지정합니다.
10. `"baseUrl"`: 비상대적 모듈 이름을 해석하기 위한 기본 디렉토리를 설정합니다.
11. `"paths"`: 모듈 이름에 대한 경로 매핑을 설정합니다.
12. `"allowJs"`: JavaScript 파일을 프로젝트에 포함시킬지 여부를 결정합니다.
13. `"checkJs"`: JavaScript 파일에 대한 타입 체킹을 활성화합니다.
14. `"declaration"`: `.d.ts` 선언 파일을 생성합니다.
15. `"removeComments"`: 출력에서 주석을 제거합니다.
16. `"noEmitOnError"`: 오류가 발생하면 출력 파일을 생성하지 않습니다.
17. `"forceConsistentCasingInFileNames"`: 파일 이름의 대소문자 일관성을 강제합니다.
18. `"skipLibCheck"`: 모든 `.d.ts` 파일의 타입 체크를 생략합니다.
19. `"resolveJsonModule"`: JSON 모듈의 가져오기를 허용합니다.
20. `"noFallthroughCasesInSwitch"`: 스위치 문에서의 case 문의 fallthrough를 허용하지 않습니다.
```
대충 이런 속성들이 있다고 함.


---

#### TS 프로젝트 기본 생성 스크립트

tsc 는 전역에 설치되어 있어야 함.
```
#!/bin/zsh
# tsinit.sh

# 디렉토리 설정
if [ -z "$1" ]; then
    DIR=$(pwd)
else
    DIR=$1
    mkdir -p "$DIR"
fi

cd "$DIR"

# npm 초기화
npm init -y

# ts-node, @types/node 및 jest 설치
npm install \
    @types/node \
    ts-node \
    jest \
    ts-jest \
    @types/jest \
    --save-dev


# test 디렉토리생성
mkdir -p test

# jest.config.js 파일 생성 및 설정
echo "module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
};" > jest.config.js

# tsconfig.json 파일 생성
tsc --init
# tsconfig.json 주석 제거
sed '/^ *\/\//d; s/\/\*.*\*\///g' tsconfig.json > temp.json && mv temp.json tsconfig.json
# tsconfig 설정
jq '.compilerOptions += {"outDir": "./dist"}' tsconfig.json > temp.json && mv temp.json tsconfig.json


# src 디렉토리 및 app.ts 파일 생성
mkdir -p src
echo "console.log('Hello, TypeScript');" > src/app.ts

# npm scripts 추가
jq '.scripts += {
  "start": "tsc && node dist/app.js",
  "dev": "ts-node src/app.ts",
  "build": "tsc",
  "test": "jest"
}' package.json > temp.json && mv temp.json package.json


echo "TypeScript project initialized in $DIR"


```