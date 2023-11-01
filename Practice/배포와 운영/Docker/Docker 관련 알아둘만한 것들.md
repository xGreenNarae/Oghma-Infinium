
컨테이너들은 프로세스 마냥 호스트 자원을 사용 가능한 최대치만큼 써먹는게 아니라, 각자에게 할당된 만큼만 사용한다. 즉 서로 경쟁하는 구도가 아니다.

Docker Container상에서는 Process가 Foreground로 실행되지 않으면 Container는 종료한다

docker container 의 기본 게이트웨이는 `172.17.0.1` 이다.  


Container 내부에서 외부의 Docker를 사용할 때(Jenkins 등), 볼륨 마운트를 할 수 있는데 이 때 외부 경로로 지정하는 곳은 실행하는 주체 자신이 아니라 Docker를 소유하고 있는 상위 호스트의 경로가 된다.