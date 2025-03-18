# 05. 트랜잭션 다루기

## 학습목표

- 트랜잭션의 개념을 설명할 수 있다.
- MySQL에서 트랜잭션을 다룰 수 있다.

## 작업

### 1. MySQL 클라이언트를 통해 트랜잭션 제어

- 트랜잭션을 시작하기
  - `mysql> set autocommit = 0`
- 데이터 변경 작업 실행하기
  - `insert into ed_board(member_id, title, content) values(101, 'aaa1', 'aaaaa');`
  - `insert into ed_board(member_id, title, content) values(102, 'aaa1', 'aaaaa');`
  - `insert into ed_board(member_id, title, content) values(103, 'aaa1', 'aaaaa');`
  - `update ed_board set title='xxxx' where board_id=27;`
- 데이터 결과 조회
  - 클라이언트1: select 실행 및 결과 확인
  - 클라이언트2: select 실행 및 결과 확인
  - 결과의 차이점을 이해
- commit 실행 후
  - 클라이언트1과 클라이언트2의 select 실행 결과 확인
- rollback 실행 후 
  - 클라이언트1과 클라이언트2의 select 실행 결과 확인

### 2. JDBC 에서 트랜잭션 제어

- DefaultBoardService 변경
  - add(), update(), delete() 메서드에 트랜잭션 적용
