package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig3 {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) /home 요청은 인증을 검사하지 않게 설정
    http.authorizeHttpRequests()
            .mvcMatchers("/home", "/css/**").permitAll() // "/home" 과 "/css/**" 는 인증을 검사하지 않는다.
            .anyRequest().authenticated(); // 나머지 요청 URL은 인증을 검사한다.

    // 위에서 설정한 대로 작업할 필터 체인을 구성한다.
    return http.build();
  }
}
