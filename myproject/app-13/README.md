# 13. 기능 별로 패키지를 나누기

## 학습목표

- 기능 별로 패키지를 만들어 MVC 컴포넌트를 배치할 수 있다.
- Mybatis의 @Mapper 애노테이션을 사용할 수 있다.
- 스프링부트의 application.properties 내용을 동적으로 설정할 수 있다.

## 작업

### 1. application.properties 내용을 동적으로 변경하기

외부 프로퍼티 파일에 등록된 DB 접속 정보를 적용하여 DataSource 객체 준비하기

- App 클래스 변경
  - 생성자 추가
  - init() 메서드 추가

### 2. 기능 분리하기

- bitcamp.myapp.member
  - Member, MemberDao, MemberService, DefaultMemberService, AuthController 를 옮긴다.
  - MemberDao.xml
- bitcamp.myapp.board
  - Board, AttachedFile, BoardDao, BoardFileDao, BoardService, DefaultBoardService, BoardController를 옮긴다.
  - BoardDao.xml, BoardFileDao.xml
- bitcamp.myapp.cloud
  - StorageService, StorageServiceException, NCPObjectStorageService
- bitcamp.myapp.common
  - HomeController
- DAO 인터페이스에 @Mapper 애노테이션 적용
- VO 클래스에 @Alias 애노테이션 적용
- App 클래스 변경
  - @MapperScan 애노테이션 제거
- application.properties 변경
  - mybatis.type-aliases-package 값 변경