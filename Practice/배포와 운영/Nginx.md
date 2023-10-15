

비활성화 된 설정들  
`/etc/nginx/sites-available/default`  

활성화된 설정은 이곳에  
`/etc/nginx/sites-enabled/default` 
여기에 ln -s 심볼릭링크로 위 경로의 .conf 파일을 연결한다.  
설정이끝나면 nginx 재시작.  

`sudo service nginx start` # nginx 실행  
`sudo service nginx restart` #nginx 셧다운 후 재실행  
`sudo servcie nginx reload` #수정된 설정 파일 적용하여 nginx 실행  
`sudo service nginx stop` #nginx 중지  
`nginx -t` #nginx 설정파일의 문법이 올바른지 확인  

---  

#### Nginx 413 Request Entity Too Large

was에서 업로드 사이즈를 늘려봤자 nginx에서 막히는 것을 보고 이 페이지에 도달했을 것으로 추측해 봄.

```
# nginx.conf 파일

http {
    client_max_body_size 5M;

    ...
}
```

기본 값은 `1M` 이다.(mb)
`0` 으로 설정하면 제한이 없다.

---

