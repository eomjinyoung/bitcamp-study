package bitcamp.myapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication // Gradle에서 bootRun 태스크를 수행할 때 실행시킬 메인 클래스를 지정하는 애노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")

// @Transactional 애노테이션 활성화
//@EnableTransactionManagement

// DAO 구현체 자동 생성을 설정하는 방법 1: 애노테이션으로 설정하기
@MapperScan("bitcamp.myapp.dao")
public class App {
  public static void main(String[] args) {
    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);
  }
}
