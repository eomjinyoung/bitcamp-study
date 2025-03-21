# 10. IoC 컨테이너 구현하기

## 학습목표

- Reflection API를 사용하여 객체를 자동 생성할 수 있다.
- IoC와 DI의 개념을 이해하고 그 관계를 설명할 수 있다.

## 작업

### 1. IoC 컨테이너에서 관리되는 객체를 표시할 애노테이션 정의

- @Component 애노테이션 정의
  - 일반 객체에 붙이는 용도
- @Controller 애노테이션 정의
  - 페이지 컨트롤러에게 붙이는 용도

### 2. IoC 컨테이너에서 생성할 객체에 애노테이션을 붙인다.

- DAO와 Service 클래스에 @Component 를 붙인다.
- Controller 클래스에 @Controller를 붙인다.