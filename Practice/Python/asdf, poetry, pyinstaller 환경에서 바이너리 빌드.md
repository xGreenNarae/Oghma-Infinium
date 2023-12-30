
#### poetry install, build 시 Package를 못 찾는 오류
pyproject.toml 에서

```
[tool.poetry]  
name = "my_app"  
version = "0.1.0"

...

```

부분에서, my_app 이라고 명시된 이름의 패키지가 존재해야 한다.
패키지 이름에 `-`, `_`, 대문자 등이 들어가면 오류가 난다는 이야기도 있다.
패키지로 인식되도록 `__init__.py` 도 첨부하자.

---

#### pyinstaller 바이너리 빌드를 성공한 한 가지 방법

`poetry add pyinstaller` 를 통해 "가상환경 내"에 pyinstaller를 설치해주자.
(정확히 이래야 하는지는 모르겠다)

`python = ">=3.8,<3.13"`
파이썬 버전 문제로 pyinstaller를 설치할 수 없다고 해서 pyproject.toml 의 파이썬 버전을 위와 같이 바꿔주었다.

pyinstaller 빌드 시, 외부 라이브러리와 main.py 이외 참조하고 있는 스크립트를 못찾는 경우가 있다.
(런타임 참조 오류 발생)

직접 작성한 모듈 참조의 경우 패키지 이름을 함께 명시해주자.
즉,
`from module_name import class_name` 이 아니라,
`from package_name.module_name import class_name` 과 같이 쓰자.

module는 .py 파일을 의미하고, package는 .py파일들이 위치한 디렉토리(`__init__.py`를 가진) 을 의미한다.

외부 라이브러리를 못 찾는 경우,
...
가상환경 경로를 직접 명시해주었다.
`poetry run pyinstaller --onefile --paths=/Users/greennarae/Library/Caches/pypoetry/virtualenvs/myproject-TEYUM9qJ-py3.12 ./myproject/main.py
`

참고로 pyinstaller가 생성하는 바이너리 파일의 이름을 지정하는옵션은 `--name` 이라고 한다.