
메모리가 1GB 정도 되어서, 뭔가 돌리면 뻗어버리곤 함.
HDD를 Swap Memory로  사용하는 설정


4GB
`sudo dd if=/dev/zero of=/swapfile bs=128M count=32`

`sudo chmod 600 /swapfile`

`sudo mkswap /swapfile`

`sudo swapon /swapfile`

반영되었는지 확인
`sudo swapon -s`


부팅 시 스왑파일 생성 설정(재부팅해도 유지)
`sudo vi /etc/fstab`
파일 끝에 다음 추가
`/swapfile swap swap defaults 0 0`


`free` 명령어를 통해 확인할 수 있다.