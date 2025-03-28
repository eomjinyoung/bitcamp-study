# 16. CSRF 공격을 방어하기

## 학습목표

- CSRF(Cross-Site Request Forgery) 공격의 원리를 이해하고 다룰 수 있다.

## 작업

### 1. CSRF 공격 시연

- CSRF를 활성화시켰을 때 비활성화시켰을 때의 form 태그의 값 확인
  - `_csrf` 토큰 값 확인
- delete 요청이 GET으로 되어 있을 때 CSRF 공격이 가능한 것을 확인
  - GET 요청은 Spring Security 에서 `_csrf` 토큰 값을 검사하지 않는다.
  
### 2. CSRF 공격 방어

- delete 요청을 `GET` 방식에서 `POST` 방식으로 전환
  - board/detail.html 변경
    - 게시글 삭제 버튼 클릭 시 POST 요청 수행
    - 첨부파일 삭제 버튼 클릭 시 POST 요청 수행
  - BoardController 변경
    - delete 요청을 GET 에서 POST 로 변경
    - file/delete 요청을 GET 에서 POST 로 변경
- logout 요청을 `GET` 방식에서 `POST` 방식으로 전환
  - SecurityConfig 변경
    - logout() 설정 변경
  - header.html 변경
    - 로그아웃 요청을 `GET` 방식에서 `POST` 방식으로 전환

