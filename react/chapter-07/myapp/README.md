# webpack 프로젝트

## 1 프로젝트 디렉토리 준비

```bash
$ mkdir myapp
$ cd myapp
$ npm init -y
```

## 2 react 프로젝트 라이브러리 준비

```bash
$ npm install react@16 react-dom@16 serve
```

## 3 프로젝트 구조 설정

```
package.json
package-lock.json
node_modules/
index.html
/src
  index.js
/data
  ingredients-data.json
```

## 4 webpack 설정

- webpack 설치

```bash
$ npm install webpack webpack-cli --save-dev
```

- webpack 설정 파일 생성

  - `webpack.config.js`

## 5 babel 설정

- babel 설치

```bash
$ npm install babel-loader @babel/core --save-dev
$ npm install @babel/preset-env @babel/preset-react --save-dev
```

- babel 설정 파일 생성
  - `.babelrc`

## 6 webpack 빌드

```bash
$ npx webpack --mode development
```
