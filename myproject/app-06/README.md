# 06. 트랜잭션 제어 코드를 캡슐화하기

## 학습목표

- 애노테이션을 정의하고 다룰 수 있다.
- GoF의 Proxy 패턴을 이해하고 구현할 수 있다.

## 작업

### 1. 트랜잭션을 표시할 애노테이션 정의

- Transactional 애노테이션 추가

### 2. 트랜잭션 애노테이션 적용

- DefaultBoardService 변경
  - 트랜잭션을 다루는 메서드에 Transactional 애노테이션을 붙인다.

### 3. 프록시 객체 팩토리 준비

- TransactionProxyFactory 클래스 생성

### 4. 트랜잭션 코드를 처리할 대행자 준비

- TransactionInvocationHandler 클래스 생성

### 5. 서비스 객체에 적용

- ContextLoaderList 클래스 변경