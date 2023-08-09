#### Redirect 와 Location
브라우저는 300번 대 status code를 기준으로 redirect를 결정하는 듯. 이 때, Header의 Location 필드를 보고 redirect를 수행한다.
예를들어, Created(201) + Location Header는 redirect 를 수행하지는 않는다.