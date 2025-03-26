package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig {

  public SecurityConfig() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  
}
