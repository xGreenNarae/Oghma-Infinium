#### Spring Security Authentication  
**개요**  
로그인이 완료되면 Security Context에 Authentication객체가 저장되고, 이것을 통해 매 요청마다 로그인을 다시하지 않아도 되게 한다. 즉, 기본적으로 세션방식을 사용한다    

UserDetailsService 인터페이스를 구현하면, 대부분 서비스에서 사용할법한 내용들을 선언해둔 인터페이스들을 구현할 수 있다.  
GrantedAuthority라는 리스트는 어떤 유저객체가 가지고있는 권한.  
getAuthorities는 어떤 권한을 가지고있는지 등..  
이외에 NonExpired, isEnabled.. 등은 30일마다 비밀번호를 바꾸세요, 오랫동안 사용하지않아서 휴면계정이 되었습니다 등의 기능을 위한 것들이라고 보면 된다.  
중요한것은 loadUserByUsername. 이것은 UserDetails 를 반환해야한다. 구체적인 도메인에서 사용하는객체를 UserDetails로 변환해줘야 한다.  
여기서 User라는 객체가 UserDetails를 구현하고있기 때문에 이것을 사용해서 email, password, authorities 등을 집어넣고 반환해주면 된다.  

**STATELESS**  
JWT등을 사용할경우, 서버를 Stateless하게 만들어주어야 의미가 있는데..  
```
http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

http.formLogin().disable();
```  
위와 같은 코드가 그 역할을 한다.  

하지만 인증을 처리하는 시점에서 SecurityFilter가 제대로 동작하려면 Authentication객체를 SecurityContext에 저장하는것은 여전히 필요하다.  
`SecurityContextHolder.getContext().setAuthentication(authentication);`  
이런 부분이다. 물론 위에서 stateless 설정을 해주었기 때문에, 요청주기에 따라 서버에서 해당 객체의 정보를 유지하지 않고 삭제하게 된다.  

[잘 설명된 블로그](https://datamoney.tistory.com/334)  

Controller의 Parameter에서 인증정보를 가져올때..  
Filter에서 SecurityContextHolder에 Authentication객체를 등록할때, `UsernamePasswordAuthenticationToken`을 등록한다면 첫번째 인자로 UserDetails를 상속하는 타입의 Principal이 담기는데..  
`Authentication` 타입을 이용한다면, getPrincipal()로 이것을 가져와서 사용할수있고.. `@AuthenticationPrincipal` 어노테이션을 사용한다면 그 자체로 Principal 이다.  

---  