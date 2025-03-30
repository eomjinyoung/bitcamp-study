# Nodejs 기반 프론트엔드 myproject

## 1 프로젝트 준비

### 1.1 프로젝트 폴더 생성

```bash
$ mkdir myproject-frontend-node
$ cd myproject-frontend-node
$ mkdir app
$ cd app
```

### 1.2 프론트엔드 관련 파일 복사

- myproject/app/src/main/resources/static 폴더의 파일을 복사해 온다.

## 2 개발용 웹서버 준비

### 2.1 Live Server 설치

```bash
$ npm init -y
$ npm install live-server --save-dev
```

### 2.2 live-server 실행 설정: package.json

```json
{
  ...
  "scripts": {
    "start": "live-server --port=3000 --no-browser",
    ...
  }
  ...
}
```

### 2.3 서버 실행

- 간접 실행: package.json 에서 설정된 대로 서버를 실행한다.
```bash
$ npm run start
```


- 직접 실행
```bash
$ npx live-server --port=3000 --no-browser
```

## 3 HTML 변경


