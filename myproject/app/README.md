# 14. Thymeleaf 적용하기

## 학습목표

- Thymeleaf를 적용할 수 있다.

## 작업

### 1. Thymeleaf 라이브러리 준비

- build.gradle 파일 변경

### 2. Thymeleaf에서 사용할 템플릿 폴더 준비

- src/main/resources/templates 폴더 생성
  - Thymeleaf 가 사용할 템플릿 파일을 둔다.
- src/main/resources/static 폴더 생성
  - html, css, javascript, image 파일 등 정적 파일을 둔다.
- application.properties 파일 변경
  - 개발하는 동안 정적 파일이나 템플릿 파일을 실행할 때 바로 적용할 수 있도록 디렉토리 경로 설정
  - `spring.web.resources.static-locations=file:src/main/resources/static`
  - `spring.thymeleaf.prefix=file:src/main/resources/templates/`

### 3. JSP 파일을 Thymeleaf 템플릿 파일로 마이그레이션

- src/main/resources/templates/*.html 파일 변경

### 4. lombok 라이브러리 적용

- build.gradle 변경
  - lombok 관련 라이브러리 추가