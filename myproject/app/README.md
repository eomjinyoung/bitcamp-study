# 19. JWT 인증 적용하기

## 학습목표

- JWT 인증 원리를 이해하고 구성할 수 있다.

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
  - RSA 파일의 데이터를 읽어 온다.
  - RSAPrivateKey, RSAPublicKey 필드 추가
  - JwtEncoder 생성
  - JwtDecoder 생성
  - OAuth2 인증 필터 설정
    - oauth2