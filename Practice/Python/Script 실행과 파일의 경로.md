
```python
import os  
  
# 이 파일의 경로가 
# ~/project/script/file.py 라고 하자.

# 그리고 
# ~/project/runner/ 경로에서 이 파일을 실행시킨다고 하자.


# 실행
# ~/project/runner python ../script/file.py

print(os.path.dirname(os.path.abspath(__file__)))  
# /Users/username/project/script
  
print(os.path.dirname(__file__))
# /Users/username/project/runner/../script

print(os.path.abspath(__file__))  
# /Users/username/project/script/file.py
  
```
