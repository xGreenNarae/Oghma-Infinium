
바로가기 파일인데, 
확장자 보기를 체크해둔 상태로 파일 이름이 어떻게 되어있는지와 별개로
항상 program 코드에서 '.lnk'로 끝난다.

경로를 읽는 등의 작업을 할때는 pylnk3 을 사용하도록 한다.
npm쪽의 패키지들은 지원이 좀 더 약하다.

참조하고 있던 실제 파일의 경로(이름)이 변경된 경우, lnk파일의 targetPath는 즉시 변경되지 않는다.
실행 시점에 어떤 복잡한 windows 의 마법을 통해 targetPath가 업데이트된다.

이것을 프로그램에서 가져오기 위해서는, python의 경우 subprocess.Popen 등을 이용해서 실제로 실행시키는 로직을 만드는 것이 간단할 것이다.