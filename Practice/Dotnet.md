
.NET Framework는 windows 전용
.NET Core는 크로스 플랫폼 지원.

.NET Core의 버전 5부터는 .NET 5, .NET 6 등으로 부름

`dotnet` 이라는 명령어로 관리됨.
파일 확장자는 `.cs`
파일 명과 클래스 명이 동일하지 않아도 된다. 파일 내 여러개의 public 클래스가 존재할 수 있다.

기본적으로 단일 파일을 컴파일하지 않고 프로젝트 단위로 관리한다.
프로젝트 관리 파일은 `.csproj` (이름은 프로젝트명)
종속성 관리에 `packages.config`
xml 로 구성한다.(다른 방식은 사용하지 않는듯.)

단일 파일 컴파일과 실행을 위해서는 `dotnet tool install -g dotnet-script` 를 이용할 수 있다고 함.

디렉토리에서 프로젝트 초기화
`dotnet new`


패키지 관리자는 NuGet
`dotnet tool install..` 의 명령어가 내부적으로 NuGet을 사용한다고 한다.


`dotnet build`
`dotnet run`

...