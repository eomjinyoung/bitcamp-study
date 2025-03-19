# 07. Mybatis 퍼시스턴스 프레임워크 적용하기

## 학습목표

- Mybatis 퍼시스턴스 프레임워크의 구동 원리를 이해하고 적용할 수 있다. .

## 작업

### 1. Mybatis 라이브러리 설치

- build.gradle 변경
  - 'org.mybatis:mybatis:3.5.19' 추가
  - 'org.apache.commons:commons-dbcp2:2.13.0' 추가

### 2. Mybatis 설정 및 객체 준비

- jdbc.properties 생성
  - DB 접속 정보 설정
- mybatis-config.xml 생성
  - Mybatis 설정
- ContextLoaderListener 변경
  - Mybatis 관련 객체 준비

### 3. Mybatis 적용

`Connection`을 직접 사용하는 대신에 Mybatis의 `SqlSession`을 사용하여 SQL 실행
 
- MySQLBoardDao 변경
  - SQL문을 SQL 매퍼 파일로 옮긴다.

- bitcamp/myapp/mapper/BoardDao.xml 생성
- MySQLBoardDao에서 사용할 SQL 문을 보관
  

