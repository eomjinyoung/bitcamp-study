# 11. SpringBoot 도입하기

## 학습목표

- 웹프로젝트에 SpringBoot를 적용할 수 있다. 

## 작업

### 1. SpringBoot 라이브러리 추가

- build.gradle 변경
  - spring.io 사이트의 Spring Initializr 를 사용하여 웹 프로젝트를 구성한 후 라이브러리 정보 추출

### 2. DAO 에 적용

- 구현체 제거
  - MySQLBoardDao, MySQLBoardFileDao, MySQLMemberDao 클래스 제거
- 예외 클래스 제거
  - DaoException 제거
- 인터페이스 변경
  - BoardDao, BoardFileDao, MemberDao 변경


