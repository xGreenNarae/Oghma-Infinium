#### k8s 이론 대략  

모든 컨테이너는 pod 안에서 실행된다.  
pod는 volumes 를 갖는다.  
pod는 재시작될수있고 주소도 바뀔수있음. 따라서 접근을 persistent하게 만들기 위해 service를 사용한다.  
deployment는 pod의 scaling이나 failover등 을 관리한다.  

pod, service, deployment 등을 resource type이라고 하는듯..  

statefulset 이라는 리소스도 있는데, 상태를 가지는 애플리케이션.  
상태를가진다는것은 DB같은것을 의미하는듯하다.   
인스턴스가 새롭게 만들어지건, 재시작하건 상태를 유지한다는 뜻.  
이것은 레플리카셋을 생성하지않고 이름이 0, 1, 2.. 등 순차적으로 증가한다.  

---  


#### kubectl 명령어 대략  

kubectl 설치  
 
로컬에서 실행하기 위한 minikube 설치..  
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube  

바이너리 설치  
curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"  
또는  
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" (공식문서)  


chmod +x ./ kubectl  

sudo mv ./kubectl /user/local/bin/kubectl  

kubectl version --client  
버전확인.  


**kubectl get**  
```
kubectl get pods   
kubectl get pods -o wide # IP까지 보여줌  
kubectl get po 

kubectl get services  
kubectl get svc  

kubectl get deployments  

kubectl get all
```

**pod 생성**  
```
kubectl create ......

kubectl apply -f <filename>.yaml  
kubectl apply -k <kustomization.yaml이 있는경로>  
# k 옵션은 kustomization 의 축약이다.

kubectl replace ......

create는 기존 리소스가 존재하면 에러  
apply는 선언적구문으로 업데이트같은것도..  
```

kubectl logs <pod-name>  

kubectl describe <resource-type> <resource-name>  

kubectl delete pod <pod-name>  
kubectl delete service <service-name>  
...  
kubectl delete --all pod <pod-name>  
kubectl delete all --all  
...  




kubectl exec -it pod-name /bin/bash  

kubectl run <name> --image <image-name> --port=80  

kubectl run -it --rm mysql --image=mysql:latest --port=3306 --env="MYSQL_ROOT_PASSWORD=<password>"

rm 은 실행 후 종료된 pod를 자동으로 제거해주는 옵션.  


---



#### minikube  
로컬에서 테스트용 클러스터를 실행하는 느낌이다.  

minikube start  

minikube ip  

minikube service --url <service-name>  # 브라우저에서접근안될때 이주소로..  

**minikube에 local docker image들을 사용하고싶다면**  
minikube docker-env 입력하면 도커설정들이나옴..  
eval $(minikube -p minikube docker-env) 를 입력하면 연결됨.  


---  

#### ConfigMap은 String:String 이다.  
예를들어  
```
passwd: 123456  # 이렇게하지말고
passwd: "123456" # 이렇게하라는뜻.

```  
---  

#### ConfigMap과 Secret은 다르다.  
읽을때도, configMapKeyRef, SecretKeyRef ..  


---  
  
  
#### 로컬이미지를 사용할때 yaml에 반드시, imagePullPolicy: Never 를 써주도록하자!  

```
containers:
      - name: mysql
        image: mysql:latest
        imagePullPolicy: Never
```

---  

#### kustomization.yaml 예제  

.. 추가

---  