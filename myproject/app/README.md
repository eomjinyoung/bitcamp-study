# 02. 서비스, DAO 컴포넌트에 인터페이스 도입

## 학습목표

- SOLID 객체지향 설계 원칙의 이해
- GRASP 설계 패턴의 이해

## 작업

### 1. SOLID-DIP(Dependency Inversion Principle)

- 구체적인 구현에 의존하지 말고 인터페이스나 추상 클래스에 의존하라
  - 클래스를 직접 사용하지 말고 인터페이스나 추상클래스를 사용하라!
  - 요구사항 변경에 맞춰 클래스를 교체하기 쉽다.

- BoardDao 클래스 변경
  - BoardDao 인터페이스 생성
  - BoardDaoImpl 클래스 생성

