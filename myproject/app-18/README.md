# 18. Backend와 Frontend를 분리하기

## 학습목표

- Cross-Domain 간에 AJAX 요청이 가능하게 설정할 수 있다.
- Nodejs 에서 live-server 모듈을 사용하여 개발용 웹서버를 구동할 수 있다.

## 작업

### 1 프론트엔드 프로젝트 디렉토리 생성

```bash
$ mkdir myproject-frontend 
$ cd myproject-frontend
$ mkdir app
```

### 2 프론트엔드 관련 파일을 복사

- `myproject/app/src/main/resources/static/` 폴더의 파일을 `myproject-frontend/app/` 폴더로 복사해온다.

### 3. 개발용 웹서버 준비

- 노드 프로젝트 설정 파일(package.json) 준비
  - `package.json` : node 프로젝트 관련 정보나 의존 라이브러리 정보를 포함하고 있다.
```bash
$ npm init -y 
```

- 개발용 웹서버 노드 모듈 설치
```bash
$ npm install live-server --save-dev
```

### 4 live-server 설정 및 실행

#### 4.1 직접 실행

- 기본 실행
  - 포트번호: 8080
  - 자동으로 웹브라우저 실행되어서 localhost:8080/ 페이지를 요청한다.
```bash
$ npx live-server
```

- 포트번호 변경
- 자동으로 웹브라우저 띄우지 않기
```bash
$ npx live-server --port=3000 --no-browser
```

#### 4.2 npm 으로 live-server를 실행하기

- package.json 변경
  - npm 으로 live-server를 실행할 수 있도록 설정한다.
  - gradle로 Spring Boot를 실행할 때 `gradle bootRun` 명령을 실행하는 것과 유사한 방법이다.
```json
{
  ...
  "script": {
    "start": "live-server --port=3000 --no-browser"
  },
  ...
}
```
- npm 실행
  - package.json 설정에서 `scripts` 항목 안에 `start` 프로퍼티 설정된 명령을 실행한다.
```bash
$ npm run start
```

### 5 Spring Security CORS 설정

- CORS(Cross-Origin Resource Sharing) 설정
  - SecurityConfig 변경
    - cors() 추가
    - CorsConfigurationSource 객체 준비
      - CorsConfiguration 객체를 통해 CORS 설정
      - UrlBasedCorsConfigurationSource 객체를 이용하여 요청 검증

### 6 credential 설정

Cross-Domain 간에는 쿠키, 세션, HTTP 인증 헤더를 전송할 수 없다. CORS를 수행하려면 허용해야 한다.

- 서버측 설정
  - CorsConfiguration 설정할 때,
    - configuration.setAllowCredentials(true) 
- 클라이언트측 설정
  - AJAX 요청 시 credential을 true로 설정해야 한다.
    - xhr.withCredentials = true;

