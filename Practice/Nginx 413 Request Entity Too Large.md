
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