### Docker  
docker container �� �⺻ ����Ʈ���̴� `172.17.0.1` �̴�.  

`docker images`  
`docker rmi imageName`  

`docker container ps -a`  
`docker rm containerName`  
`docker start` , `docker stop` , `docker restart`  

MySQL ���� ����  
`docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=<password> -d -p 3306:3306 mysql:latest`  
-p : ��Ʈ���ε�. �ܺ���Ʈ : ������Ʈ  
-d : ��׶������  
-it : ��ȭ������ ����ϱ� ����  
-e : �����̳�ȯ�溯������. Dockerfile ENV�� ���  

-v : ��������Ʈ. �ܺε��丮:���ε��丮  
-w : WORKDIR �����. 
--privileged : �ý��۸��ҽ� ����, Ŀ�� ��� �� ..  
--network=host : ��Ʈ��ũ��带 ȣ��Ʈ�� ����  

`docker exec -it containerName /bin/bash`  

`docker build -t imageName .`  

 �̹��� ���� �����
`docker save �̹����� > ���ϸ�.tar`  
`docker load < ��������ϸ�`  

NONE �̹�������  
`docker rmi $(docker images -f "dangling=true" -q)`  

�̸�����
�����̳��̸�����: `docker rename <old_name> <new_name>`  
�̹��� �̸����� : 
	`docker image tag <���� tag> <�� tag>`
	`docker rmi <���� tag>`

�����̳� ����, ����������  
`docker attach �����̳��̸�`  
Ctrl+D , exit �ϸ� �����̳� ����,  
Ctrl + P + Q �ϸ� ������̳���.  

�����̳� �������� ����/���� ����. -r ������ ��� ������ �˾Ƽ� ���������.  
`docker cp CONTAINER:<src_path> <dst_path>`  
`docker cp <src_path> container:<dst_path>`  

�����̳�����Ȯ�� (��Ʈ��ũ ��)  
`docker container inspect <container_name>`  

kill all containers :
```
docker stop $(docker ps -a -q) && \
docker rm $(docker ps -a -q)
```

�����̳ʰ� ������ �ȵǰ� �������ȴٸ�? �α׸� ����.  
`docker logs containerName`  

---  