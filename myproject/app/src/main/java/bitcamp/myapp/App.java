package bitcamp.myapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication // Gradle에서 bootRun 태스크를 수행할 때 실행시킬 메인 클래스를 지정하는 애노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")
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

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 허용할 도메인
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*")); // 모든 요청 헤더를 수락한다.
    configuration.setAllowCredentials(true); // 다른 사이트와의 쿠키 전송 허용

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  public static void main(String[] args) {
    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);
  }

}
