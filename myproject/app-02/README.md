# 02. 서비스, DAO 컴포넌트에 인터페이스 도입

## 학습목표

- SOLID 객체지향 설계 원칙의 이해
  - SRP(Single Responsibility Principle): 한 클래스는 한 역할만 담당한다. 
    - 예) 컴포넌트를 역할에 따라 MVC 구조로 나눈 것.
  - OCP(Open/Closed Principle): 기존 코드를 변경하지 않고 기능 확장한다.
    - 예) 리스너, 서블릿, 필터를 추가한 것. 
  - LSP(Liskov Substitution Principle): 상위 타입을 하위 타입으로 교체해도 정상 동작한다.
    - 예) Truck 클래스와 그 하위 클래스인 DumpTruck, PickupTruck 클래스
  - ISP(Interface Segregation Principle): 객체를 용도에 맞춰서 제한적으로 바라볼 수 있게 한다.
    - 예) 컬렉션 클래스의 Collection, Iterator 인터페이스
  - DIP(Dependency Inversion Principle): 구체적인 클래스에 의존하기 보다는 추상화에 의존한다.
    - 예) BoardService가 사용하는 의존 객체를 인터페이스로 설정한다. 
- GRASP 설계 패턴의 이해

## 작업

### 1. SOLID-DIP(Dependency Inversion Principle)

- 구체적인 구현에 의존하지 말고 인터페이스나 추상 클래스에 의존하라
  - 클래스를 직접 사용하지 말고 인터페이스나 추상클래스를 사용하라!
  - 요구사항 변경에 맞춰 클래스를 교체하기 쉽다.

- BoardDao 클래스 변경
  - BoardDao 인터페이스 생성
  - MySQLBoardDao 클래스 생성
- BoardService 클래스 변경
  - BoardService 인터페이스 생성
  - BoardServiceImpl 또는 DefaultBoardService 클래스 생성
- MemberDao 클래스 변경
  - MemberDao 인터페이스 생성
  - MySQLMemberDao 클래스 생성
- MemberService 클래스 변경
  - MemberService 인터페이스 생성
  - MemberServiceImpl 또는 DefaultMemberService 클래스 생성

### 2. RuntimeException과 예외 처리 커스터마이징

- DaoException 예외 클래스 생성
  - 예외 상황을 좀 더 상세하게 분류하기 위해 서브 클래스를 만든다.
  - 예외 발생 시 어느 클래스에서 예외가 발생했는지 확인하기 쉽다.
  - 상위 호출자에게 전달하기 쉽도록 RuntimeException 예외로 만든다.
