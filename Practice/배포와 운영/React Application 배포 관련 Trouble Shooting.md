

어떤 버전부터인지는 모르겠으나, `package.json`에 다음과 같은 스크립트가 생겨있었다.

```
"scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
},
```

`node-modules` 내부에 `react-scripts/scripts` 에 들어가보면 `start`, `build` 등의 이름으로 스크립트가 존재한다.

`process.env` 로 환경변수에 접근할 수 있는 스크립트가 존재하는 듯함.

---

#### FATAL ERROR: Ineffective mark-compacts near heap limit Allocation failed - JavaScript heap out of memory

빌드 시 발생하는 오류인데

몇 가지 해결책들이 있는 모양.
1. https://gist.github.com/tjunghans/90ff3bbf575b8b1da41f3fb56e374931
`npm build` 를 실행하는 컨텍스트에서 환경변수에 `NODE_OPTIONS=--max-old-space-size=8192` 이런식으로 넣어주면 잘 된다. 
사이즈 설정의 단위가 메가바이트라고 써있긴 하고.. 메모리 크기를 할당하고 그런 느낌이지만 정확한 것은 없다. 
1GB 메모리가 할당되어 있는 Container에서 `900`을 집어넣어도 잘 되고 `4096`을 집어넣어도 잘 되었다. 
다만 빌드시간이 좀 느려진 것 같은 느낌은 있다.(역시 정확하지 않다) 
`docker stats` 로 메모리 사용현황을 실시간으로 들여다봐도 딱히 뭔가 차이가 보이지는 않았다.

2. `GENERATE_SOURCEMAP=false` (사용해보지 않았음)
마찬가지로 환경변수 설정으로 적용할 수 있는 부분 인 듯한데, 
CRA의 경우 빌드하고나면 생성되는 난독화된 js파일과 원본 소스파일을 연결시켜서,
(xx.map이라는 파일 생성되고 클라이언트에 도달하지 않게 하는 목적이라면 이걸 수동으로 지워도 된다고 함) 
개발자 도구를 통해 디버깅하기 쉽게 만들어주는 설정이라고 한다. 이게 메모리를 많이 먹는다고 함. 디버깅 하기 어려워진다는 뜻이라 적용해보지 않았다.











