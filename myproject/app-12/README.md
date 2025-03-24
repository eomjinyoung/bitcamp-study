# 12. 스프링 부트 기반 설정으로 최적화 수행

## 학습목표

- application.properties의 다양한 속성의 역할을 이해한다.
- Spring WebMVC 설정에 맞춰 페이지 컨트롤러를 최적화 할 수 있다.

## 작업

### 1. AWS S3 API 버전 1을 사용할 때 발생하는 경고 메시지 제거

- NCPObjectStorageService 변경
  
### 2. Mybatis 설정 최적화

- application.properties 변경
```properties
spring.datasource.url=jdbc:mysql://db-32e40j-kr.vpc-pub-cdb.ntruss.com:3306/studentdb
spring.datasource.username=student
spring.datasource.password=bitcamp123!@#
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

mybatis.mapper-locations=classpath:bitcamp/myapp/mapper/*Dao.xml
mybatis.type-aliases-package=bitcamp.myapp.vo
```

- mybatis.mapper-locations 프로퍼티 설정을 생략
  - SQL 매퍼 파일을 인터페이스의 패키지 경로 및 이름과 같게 한다.

### 3. JSP를 외부 노출에서 감추기

- `/WEB-INF/view` 폴더 아래로 JSP 파일을 옮긴다.
- application.properties 변경
```properties
spring.mvc.view.prefix=/WEB-INF/view/
spring.mvc.view.suffix=.jsp
```

### 4. 페이지 컨트롤러 최적화

- @RequestMapping 교체
  - @GetMapping, @PostMapping 으로 교체
  - 클래스에 기본 URL 지정하고 메서드에는 나머지 URL 설정
- 클라이언트가 보낸 요청 파라미터 값을 도메인 객체로 직접 받기
  - BoardController의 add(), update()에서 Board 타입 파라미터 사용하기
- request handler의 파라미터 정리
  - 메서드에서 사용하지 않는 파라미터 제거
  - 프론트 컨트롤러에서 직접 받을 수 있는 것은 직접 받기 
  - 첨부 파일을 받을 때 Part 와 MultipartFile 로 받기 
- request handler의 리턴 값 정리
  - prefix, suffix를 고려하여 JSP 뷰 이름을 리턴하기
  - 리턴 타입을 void로 설정하면 요청 URL을 view name으로 사용한다.