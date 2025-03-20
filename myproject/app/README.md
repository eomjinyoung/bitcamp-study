# 09. Front Controller 패턴 적용하기 II - 페이지 컨트롤러를 POJO로 바꾸기

## 학습목표

- POJO의 뜻을 설명할 수 있다.
- Reflection API를 사용하여 특정 애노테이션이 붙은 메서드를 찾아 호출할 수 있다.  

## 작업

### 1. 페이지 컨트롤러 역할을 수행하는 서블릿을 POJO로 변환

- XxxServlet ==> XxxController 로 이름 변경
  - POJO 클래스로 변환
- ContextLoaderListener 변경
  - POJO로 변환한 페이지 컨트롤러를 ServletContext에 보관한다.
- DispatcherServlet 변경
  - 클라이언트 요청이 들어오면 그 요청을 처리할 객체를 ServletContext에서 꺼내 실행한다.

### 2. 클라이언트 요청을 처리할 메서드에 붙일 애노테이션 정의

- RequestMapping 애노테이션 생성

### 3. 클라이언트 요청을 처리할 메서드에 애노테이션을 붙인다.

- XxxController 변경
  - 요청을 처리할 메서드에 애노테이션을 붙인다.
- DispatcherServlet 변경
  - ServletContext에서 꺼낸 객체에서 애노테이션이 붙은 메서드를 찾아 호출한다.
