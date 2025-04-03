# React

## 1. 개발용 웹서버 준비

- 노드 프로젝트 설정 파일(package.json) 준비
  - `package.json` : node 프로젝트 관련 정보나 의존 라이브러리 정보를 포함하고 있다.

```bash
$ npm init -y
```

- 개발용 웹서버 노드 모듈 설치

```bash
$ npm install live-server --save-dev
```

## 2 live-server 설정 및 실행

### 2.1 직접 실행

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

### 2.2 npm 으로 live-server를 실행하기

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

## 3 참고 자료

### 3.1 Learning React(2nd, O'Reilly)

- 원서(https://github.com/moonhighway/learning-react)
- 번역서(https://github.com/enshahar/learning-react-kor/tree/seconded)
