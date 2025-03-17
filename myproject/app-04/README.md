# 04. SQL 삽입 공격 차단하기 및 부모 테이블 데이터 삭제하기

## 학습목표

- SQL 삽입 공격의 개념을 이해한다.
- SQL 삽입 공격의 예를 보일 수 있다.
- SQL 삽입 공격의 보안 약점을 해소하는 코드를 작성할 수 있다. 
  - PreparedStatement를 사용할 수 있다.
- 자식 테이블에 데이터가 있는 부모 테이블의 데이터를 삭제할 수 있다.


## 작업

### 1. 삽입 공격 방어하기

- MySQLBoardDao 변경
  - Statement를 PreparedStatement로 변경한다.
- MySQLBoardFileDao 변경
  - Statement를 PreparedStatement로 변경한다.
- MySQLMemberDao 변경
  - Statement를 PreparedStatement로 변경한다.

### 2. 첨부 파일이 있는 게시글 삭제 오류 해결

- 게시글의 첨부 파일 데이터 모두 삭제하기
  - BoardFileDao.deleteAllByBoardNo() 메서드 추가
  - MySQLBoardFileDao.deleteAllByBoardNo() 메서드 구현
- 게시글 삭제할 때 첨부 파일 데이터를 먼저 삭제하기
  - DefaultBoardService.delete() 메서드 변경
- 게시글 삭제할 때 네이버 클라우드의 업로드 파일도 삭제하기
  - BoardDeleteServlet 변경
