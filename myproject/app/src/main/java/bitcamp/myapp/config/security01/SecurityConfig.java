package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig {

  public SecurityConfig() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) Spring Security에서 제공하는 기본 로그인 폼 사용.
    //    기본 URL: /login
    http.formLogin(Customizer.withDefaults());
    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
}
