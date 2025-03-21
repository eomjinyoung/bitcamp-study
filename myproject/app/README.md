# 11. SpringBoot 도입하기

## 학습목표

- 웹프로젝트에 SpringBoot를 적용할 수 있다. 

## 작업

### 1. SpringBoot 라이브러리 추가

- build.gradle 변경
  - spring.io 사이트의 Spring Initializr 를 사용하여 웹 프로젝트를 구성한 후 라이브러리 정보 추출
- App 변경
  - SpringBoot 실행 코드 추가
- application.properties 생성
  - 프로젝트 이름, 포트 번호 설정, 컨텍스트 루트 패스 설정

### 2. 외부 프로퍼티 파일 로딩하기

- App 변경
  - 웹애플리케이션 경로가 아닌 다른 경로에 있는 프로퍼티를 로딩한다.
  - 보통 보안이 필요한 값은 외부 경로에 두기 때문이다.
    - 예) ~/config/bitcamp-study.properties

### 3. Mybatis 설정

- App 변경
  - DataSource 객체를 생성하는 메서드 준비
  - TransactionManager 객체를 생성하는 메서드 준비
  - SqlSessionFactory 객체를 생성하는 메서드 준비

### 2. DAO 에 적용

- 구현체 제거
  - MySQLBoardDao, MySQLBoardFileDao, MySQLMemberDao 클래스 제거
- 예외 클래스 제거
  - DaoException 제거
- 인터페이스 변경
  - BoardDao, BoardFileDao, MemberDao 변경
- 


