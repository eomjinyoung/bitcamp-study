package bitcamp.myapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication // Gradle에서 bootRun 태스크를 수행할 때 실행시킬 메인 클래스를 지정하는 애노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")

// @Transactional 애노테이션 활성화: SpringBoot는 기본으로 활성화시킨다.
//@EnableTransactionManagement

@MapperScan("bitcamp.myapp.dao")
public class App {

  @Value("${jdbc.driver}")
  private String driver;

  @Value("${jdbc.url}")
  private String url;

  @Value("${jdbc.username}")
  private String username;

  @Value("${jdbc.password}")
  private String password;

  // 스프링부트의 프로퍼티 값(application.properties)을 커스터마이징 할 때 사용하는 객체
  private final ConfigurableEnvironment configurableEnvironment;

  public App(ConfigurableEnvironment configurableEnvironment) {
    this.configurableEnvironment = configurableEnvironment;
  }

  @PostConstruct // 생성자 호출 후에 스프링 IoC 컨테이너가 호출하는 메서드
  public void init() throws Exception {
    HashMap<String,Object> datasourceProperties = new HashMap<>();
    datasourceProperties.put("spring.datasource.driver-class-name", driver);
    datasourceProperties.put("spring.datasource.url", url);
    datasourceProperties.put("spring.datasource.username", username);
    datasourceProperties.put("spring.datasource.password", password);

    MapPropertySource propertySource = new MapPropertySource("dynamicProperties", datasourceProperties);
    configurableEnvironment.getPropertySources().addFirst(propertySource);
  }

  public static void main(String[] args) {
    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);
  }

}
