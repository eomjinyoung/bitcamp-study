# 11. 스프링 부트 기반 설정으로 최적화 수행

## 학습목표

- application.properties의 다양한 속성의 역할을 이해한다.

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