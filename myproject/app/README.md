# 17. CSR 방식으로 전환하기

## 학습목표

- SSR(Server-Side Rendering)과 CSR(Client-Side Rendering)의 동작 원리를 이해하고 CSR 구조로 변경할 수 있다.

## 작업

### 1. Thymeleaf 뷰 템플릿 엔진을 제거한다.

- resources/templates 폴더의 파일을 static 폴더로 옮긴다.
- templates 폴더를 삭제한다.
- build.gradle 변경
  - thymeleaf 와 관련된 라이브러리 제거

### 2. Spring Security 설정을 변경한다.

- authorizeHttpRequests() 변경
  - `*.html` 요청에 대해 인증을 검사하지 않도록 변경: 정규표현식 패턴 매칭 사용
- csrf() 설정
  - Cookie 로 CSRF 토큰 값을 주고 받을 수 있게 설정
- formLogin() 설정
  - loginPage() 변경: 로그인 폼 페이지 변경
    - failureForwardUrl() 추가: 로그인 실패 처리 추가
- logout() 설정
  - logoutSuccessUrl() 삭제: 로그아웃 후에 포워딩 페이지 제거
  - logoutSuccessHandler() 추가: 로그아웃 결과를 클라이언트에게 직접 출력

### 3. HTML 페이지 변경

- /home.html 변경
  - /header.html 변경
  - /js/common.js 추가
- /auth/login-form.html 변경
  - /js/common.js 변경
  - AuthController 변경
- /board/list.html 변경
- /board/detail.html 변경

### 4. 예외 상황 처리 컨트롤러 추가 

- ErrorController 구현체 생성
  - CustomErrorController 클래스 추가