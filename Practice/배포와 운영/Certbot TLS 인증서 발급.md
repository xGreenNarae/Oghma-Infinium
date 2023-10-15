
EC2 domain (....compute.amazonaws.com) 은 정책 상 인증서 발급이 금지되어 있음.
별도의 도메인을 마련해야 한다.


**Certbot docker image를 이용한 1회성(90일) Webroot 방식 예제**

Nginx에 먼저, 해당 경로 접속 설정이 되어있어야한다.
```
# dafult.conf

server {
     listen [::]:80;
     listen 80;

     server_name domain.com www.domain.com;

     location /.well-known/acme-challenge {
         root /var/www/certbot;
     }
} 
```


```
도커 컨테이너로 Certbot 실행

docker run -it --rm --name certbot \
	-v "/etc/letsencrypt:/etc/letsencrypt" \
	-v "/var/lib/letsencrypt:/var/lib/letsencrypt" \
	certbot/certbot \
	certonly \
	--webroot \
	-w /var/lib/letsencrypt \
	-d userdomain.com

```


Nginx ssl_certificate 설정
```
server { 
	listen 80; 
	server_name userdomain.com; 
	location / { 
		return 301 https://$host$request_uri;
		// 또는 rewrite ^ https://$server_name$request_uri? permanent; 		
	} 
} 

server { 
	listen 443 ssl; 
	server_name userdomain.com; 
	location / { 
		...
	} 
	ssl_certificate /etc/letsencrypt/live/userdomain.com/fullchain.pem; 
	ssl_certificate_key /etc/letsencrypt/live/userdomain.com/privkey.pem; 
}
```

자동 갱신이 필요하다면 crontab 등을 이용한다..