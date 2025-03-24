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
    - @PropertySource 애노테이션 활용
  - 보통 보안이 필요한 값은 외부 경로에 두기 때문이다.
    - 예) ~/config/bitcamp-study.properties

### 3. Mybatis 설정

- App 변경
  - DataSource 객체를 생성하는 메서드 준비
    - @Value 애노테이션 활용
  - TransactionManager 객체를 생성하는 메서드 준비
  - SqlSessionFactory 객체를 생성하는 메서드 준비
  - MapperScannerConfigurer 객체를 생성하는 메서드 준비
    - @MapperScan 애노테이션으로 대체하는 방법

### 2. DAO 에 적용

- 구현체 제거
  - MySQLBoardDao, MySQLBoardFileDao, MySQLMemberDao 클래스 제거
- 예외 클래스 제거
  - DaoException 제거
- 인터페이스 변경
  - BoardDao, BoardFileDao, MemberDao 변경

### 3. Service 컴포넌트 변경

- DefaultXxxService 변경
  - Spring의 @Service 애노테이션을 붙인다.
- NCPObjectStorageService 변경
  - @Value를 이용하여 클라우드 접속 정보 설정
  - @PostContruct 애노테이션을 사용하여 초기화 함수 제작
    - AWS API 객체 초기화

### 4. 페이지 컨트롤러 변경

- XxxController 변경
  - Spring의 @Controller로 변경
- JSP 링크 URL 변경
  - `/app` 에서 `/`으로 변경
  
### Spring으로 대체한 코드 제거

- DispatcherServlet 삭제
- 트랜잭션 관련 클래스 삭제
- 필터 및 리스너 클래스 삭제

