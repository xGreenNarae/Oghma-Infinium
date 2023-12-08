
https://github.com/AmrDeveloper/GQL (Rust, 23.06 ~ )
https://github.com/filhodanuvem/gitql (Go, 14.02 ~ )

SQL 형식으로 .git 파일을 검색하는 오픈소스 도구


.git 디렉토리가 존재하는 경로에서 `gitql -r .` 
대화형 Cli 시작.




예제

최초 커밋을 가져온다.
```
SELECT *
FROM commits
ORDER BY ASC
LIMIT 1
```

