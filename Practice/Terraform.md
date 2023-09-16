AWS EC2 예제  

우분투에 terraform 설치 예제  
```
curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
sudo apt-add-repository "deb [arch=$(dpkg --print-architecture)] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
sudo apt install terraform
```

설치확인  
```
terraform version
apt policy terraform
```  
이런느낌이다.  
`sudo apt-get update && sudo apt-get install -y gnupg software-properties-common curl` 이런것이 필요할수도.  


프로비저닝 과정은 대략 다음과 같다.  
1. 클라우드 계정과 API 키 준비.  
2. HCL 리소스 파일 작성  
3. Plan 확인 - Apply  

**1**  
AWS에서 API 키 생성이란, IAM 계정을 생성하고 액세스키와 시크릿키를 발급받는것을 의미함.  

**2**  
Provider 라는 개념이 있는데  
AWS, GCP 등의 범용 클라우드 서비스 뿐 아니라, GitHub, DataDog 등 특정 기능을 제공하는 서비스, 그리고 MySQL, RabbitMQ, Docker 등 로컬 서비스 등도 제공된다.  
Resource란, Provider가 제공해주는 조작가능한 대상의 최소 단위.  

특정 폴더 내의 .tf 파일을 모두 읽고 작업을 수행하는 방식.  
`provider.tf`, `web_infra.tf` 두 가지 파일을 가지고 예제를 진행한다.  


**provider.tf**  
```
terraform {
        required_version = "1.5.6"

        required_providers {
                aws = ">= 5.14.0"
        }
}


provider "aws" {
        access_key = "AKIAS5S..생략"
        secret_key = "/9bGf6J06BT7Z/4rMWM...생략"
        region = "ap-northeast-2"
}
```
terraform 블록은 버전을 고정시켜둔다는 의미.(아래 init명령어 이후 `terrform version` 을 통해 확인가능)  
이 부분들은 환경변수로 처리할수 있음.  

이제 `terraform init` 을 통해 프로젝트를 초기화 해준다!  

**web_infra.tf**  
```
resource "aws_key_pair" "web_admin" {
        key_name = "web_admin"
        public_key = file("~/.ssh/web_admin.pub")
}

resource "aws_security_group" "ssh" {
  name = "allow_ssh_from_all"
  description = "Allow SSH port from all"
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

data "aws_security_group" "default" {
  name = "default"
}

resource "aws_instance" "web" {
  ami = "ami-0a93a08544874b3b7" # amzn2-ami-hvm-2.0.20200207.1-x86_64-gp2
  instance_type = "t2.micro"
  key_name = aws_key_pair.web_admin.key_name
  vpc_security_group_ids = [
    aws_security_group.ssh.id,
    data.aws_security_group.default.id
  ]
}
```  
ssh 키 생성은 `ssh-keygen -t rsa -b 4096 -C "<EMAIL_ADDRESS>" -f "$HOME/.ssh/web_admin" -N ""` 이런식으로..  
22번 포트를 열어두는것은 쉘 접근을 위함이고..  
data블록은 VPC의 기본 시큐리티그룹을 불러오는 예제. 자세한 의미는 모르겠음.  

마지막 resource 부분은 ec2 instance를 생성하는 블록이다.  


`terraform plan` 으로 실행계획을 눈으로 확인하고, `terraform apply`를 통해 실행하도록 한다!  

`terraform console` 로 콘솔접속가능.  
`aws_instance.web.public_ip` 로 public ip 를 확인할수있음.  
`ssh -i ~/.ssh/web_admin ec2-user@192.168.0.1` 쉘 접속을해보려면. 아마존 리눅스 기본유저는 ec2-user 라고 한다.  

**Terraform의 힘을 느껴보려면**  
terraform은 HCL 파일에 정의된 '최종상태'와 '현재상태'를 동일하게 만드는 방식으로 작동한다.  
따라서 현재상태에서 web_infra.tf 파일을 제거하거나 다른 경로로 옮겨버리면 apply 수행 시 리소스들이 모두 제거되는 것.  
물론 `terraform destroy` 명령을 통해 파일을 건드리지 않고 리소스를 모두 제거할수 있다.  
plan, apply, destroy 를 간단하게 반복하는것으로 프로비저닝 자동화의 힘을 간단히 느껴볼수있다.  

`terraform show` 는 현재 리소스의 상태들을 보여준다.  

[참고자료](https://www.44bits.io/ko/post/terraform_introduction_infrastrucute_as_code)  

직접 콘솔 조작보다 실제로 효율적으로 사용하려면 좀 더 HCL과 클라우드 제품 및 서비스들에 숙련될 필요가 있을듯.  

