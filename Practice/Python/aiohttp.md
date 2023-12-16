
```python
async with aiohttp.ClientSession() as session:  
    response = await session.get(url, headers=headers)  
    response = await response.json(content_type=None)
```

`response.json` 을 호출할 때, 이 블록 바깥에 있으면 정상 동작하지 않는다.