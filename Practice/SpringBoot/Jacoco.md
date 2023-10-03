#### Jacoco  
Java Code Coverage  
테스트 이후 실행되는 코드 커버리지 측정 도구  

대충 Gradle 예제.
```
plugins {  
	...  

	id 'jacoco'  
}

jacoco {
	toolVersion = "0.8.10" // 최신버전이 뭔가 -> https://www.jacoco.org/jacoco/
}

jacocoTestReport {
	reports {
		xml.required = true // Gradle 8.0.2 .. 아무튼 최신버전에서 바뀐 부분이라 주의.
		csv.required = false
		html.required = true
		html.destination file("${buildDir}/reports/jacocoReport")
	}
	finalizedBy jacocoTestCoverageVerification
}

jacocoTestCoverageVerification { // 실패하면 빌드실패.  
	violationRules {
		rule { // 브랜치, 라인 커버리지가 80% 이상이어야 한다.
			enabled = true
			limit {
				counter = 'LINE'
				value = 'COVEREDRATIO'
				minimum = 0.8
			}

			limit {
				counter = 'BRANCH'
				value = 'COVEREDRATIO'
				minimum = 0.8
			}
		}
	}
}
```  

커버리지를 충족시키지 못한경우, 빌드 과정을 실패시키는 예제이고..  
`jacocoTestCoverageVerification` 부분을 생략한 뒤, GitHub Actions 등의 CI과정에서 report.xml만 읽어서 PR과 함께 띄워주는 방식으로 사용하는 것을 생각해볼 수도 있겠다.  
[Jacoco PR Comment Report Actions](https://github.com/marketplace/actions/jacoco-report)  

GitHub Actions Workflow 예제
```
- name: Add Jacoco Test coverage to PR
        uses: madrapps/jacoco-report@v1.6.1
        with:
          paths: |
            ${{ github.workspace }}/ci-test/build/reports/jacoco/test/jacocoTestReport.xml
          token: ${{ secrets.GITHUB_TOKEN }}
          min-coverage-overall: 80
          min-coverage-changed-files: 80
```


---  