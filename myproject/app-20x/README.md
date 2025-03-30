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

### 3. HTML 페이지 변경
