# 19. JWT 인증 적용하기

## 학습목표

- JWT 인증 원리를 이해하고 구성할 수 있다.
- HandlerMethodArgumentResolver의 구동 원리를 이해하고 사용할 수 있다.

## 작업

### 1 JWT 관련 의존 라이브러리 추가

- `build.gradle` 변경
  - `implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'` 추가
  - 
### 2 RSA 키 쌍 준비

JWT 토큰을 암호화하거나 암호해제할 때 사용할 RSA 키(공개키와 개인키)를 준비한다.

- `RSAKeyGenerator` 생성
  - `private.pem`, `public.pem` 파일 생성
  - `src/main/resources` 폴더로 옮긴다.
    - 파일을 찾기 쉽도록 CLASSPATH 경로에 둔다.

### 3 JWT 인코더와 디코더 객체 준비

JWT 토큰을 암호화하거나 암호 해제하는 일을 한다.

- application.properties 변경
  - 자바 코드에서 찾기 쉽도록 pem 파일의 경로를 등록한다.
  - `jwt.private.key=classpath:private.pem`
  - `jwt.public.key=classpath:public.pem`
- SpringSecurity 변경
  - RSA 파일의 데이터를 필드로 읽어 온다.
    - RSAPrivateKey, RSAPublicKey 필드 추가
  - JwtEncoder/JwtDecoder 생성
  - OAuth2 인증 필터 설정
    - oauth2ResourceServer() 추가
  - 세션 사용하지 않도록 설정
    - sessionManagement() 추가
  - 세션과 쿠키를 이용하지 않기(Stateless) 때문에 이와 관련된 설정을 제거한다.
    - logout() 제거
    - csrf().disable() 변경
- AuthController 변경
  - success() 변경
    - 로그인 회원 정보를 JWT 토큰에 담아서 클라이언트로 전달한다.

### 4 웹 페이지 변경

- login-form.html 변경
  - 서버에서 받은 JWT 토큰을 sessionStorage에 보관한다.
- /js/common.js 변경
  - JWT 토큰 값을 글로벌 변수(__jwtToken)에 저장해 둔다.
  - getUserInfo() 메서드에서 서버에 요청할 때 JWT 토큰 값을 Authorization 헤더로 보낸다.
- AuthController 변경
  - userInfo() 변경
    - JWT 토큰에서 사용자 정보를 추출하여 리턴한다.
- BoardController 변경
  - 세션이 아니라 JWT 에서 로그인 사용자 정보를 추출하도록 변경
  - 게시글 관련 페이지 변경
    - 요청할 때 JWT 토큰을 포함하기
    - Spring Security는 요청 헤더 Authorization에 JWT 토큰이 있을 때만 요청을 받아 들인다.

### 5 파라미터로 JWT에서 추출한 사용자 정보를 받기

- @LoginUser 애노테이션 생성
- HandlerMethodArgumentResolver 구현체 생성
  - LoginUserArgumentResolver 클래스 생성
- App 클래스 변경
  - WebMvcConfigurer 구현
  - addArgumentResolvers() 추가