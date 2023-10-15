
#### SCP (Secure Copy)  
`scp 파일명 계정명@원격서버IP:<파일이 저장될 경로>`  

예시  
(EC2) `scp -i /path/key-pair-name.pem /path/my-file.txt ec2-user@instance-public-dns-name:path/`

로컬->원격 (push)
`scp -P 포트 로컬경로/파일명 계정@서버주소:원격경로/파일명`

원격->로컬 (pull)
`scp -P 포트 계정@서버주소:원격경로/파일명 로컬경로/파일명`

-r 옵션주면 폴더도 가능.(폴더내용물이아닌 그 폴더째로 옮겨감)

---  

