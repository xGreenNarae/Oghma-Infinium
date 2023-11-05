
현재 폴더 하위 파일 중 .c, .h 확장자를 가진 파일들의 라인 수를 센다. 공백 줄은 제외한다.
`Get-ChildItem .\* -Include *.c, *.h -Recurse | Get-Content | Measure-Object –Line`

모든 확장자 파일 세기
`Get-ChildItem .\* -Include *.* -Recurse | Get-Content | Measure-Object –Line`

