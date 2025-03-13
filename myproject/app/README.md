# 03. 네이버 클라우드의 Object Storage 활용: 게시글 첨부파일 보관

## 학습목표

- 네이버 클라우드의 Object Storage 사용할 수 있다.
- 멀티파트 파일 업로드 다룰 수 있다.

## 작업

### 1. SOLID 원칙에서 OCP 적용하기

- CharacterEncodingFilter 클래스 생성
  - 기존 서블릿 클래스를 손대지 않고(Closed) 요청 데이터의 문자 집합을 지정하기(Open)


### 2. 게시글 입력 폼 변경 

- /board/form.jsp 변경
  - <form> 태그에 enctype="multipart/form-data" 지정.

### 3. 네이버 클라우드의 Storage Object 서비스 설정

- 
  
  
## HTTP 프로토콜 POST 요청
### application/x-www-form-urlencoded
POST /board/add HTTP/1.1
Host: 192.168.0.10:8888
Content-Length: 126
Pragma: no-cache
Cache-Control: no-cache
Origin: http://192.168.0.10:8888
Content-Type: application/x-www-form-urlencoded
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Referer: http://192.168.0.10:8888/board/form
Accept-Encoding: gzip, deflate
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,cs;q=0.5
Cookie: JSESSIONID=33220E6042E6755B37E9878D1876CB86
Connection: keep-alive

title=ABC%EA%B0%80%EA%B0%81&content=ABC%EA%B0%80%EA%B0%81%EA%B0%84&files=IMG_1624.jpeg&files=IMG_1627.jpeg&files=IMG_1713.jpeg

### multipart/form-data
POST /board/add HTTP/1.1
Host: 192.168.0.10:8888
Content-Length: 5320188
Pragma: no-cache
Cache-Control: no-cache
Origin: http://192.168.0.10:8888
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryv4NOk2pPS6PJbYGY
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Referer: http://192.168.0.10:8888/board/form
Accept-Encoding: gzip, deflate
Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6,cs;q=0.5
Cookie: JSESSIONID=6CAA97882A73E7E2FC404DB084455BD9
Connection: keep-alive

------WebKitFormBoundaryv4NOk2pPS6PJbYGY
Content-Disposition: form-data; name="title"

aaa
------WebKitFormBoundaryv4NOk2pPS6PJbYGY
Content-Disposition: form-data; name="content"

aaaaaa
------WebKitFormBoundaryv4NOk2pPS6PJbYGY
Content-Disposition: form-data; name="files"; filename="IMG_1624.jpeg"
Content-Type: image/jpeg

...
------WebKitFormBoundaryv4NOk2pPS6PJbYGY
Content-Disposition: form-data; name="files"; filename="IMG_1627.jpeg"
Content-Type: image/jpeg

...
------WebKitFormBoundaryv4NOk2pPS6PJbYGY--