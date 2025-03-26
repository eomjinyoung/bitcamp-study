package bitcamp.myapp.config.security03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig1 {

  public SecurityConfig1() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) 사용자 정의(개발자가 만든) 로그인폼 사용하는 필터 설정
    http.formLogin(formLoginConfigurer -> {
        formLoginConfigurer.loginPage("/auth/login-form"); // 로그인 폼 URL 설정
        formLoginConfigurer.loginProcessingUrl("/auth/login"); // 로그인 처리 URL 설정
        formLoginConfigurer.usernameParameter("email"); // 기본은 "username"이다.
        formLoginConfigurer.passwordParameter("password"); // 기본은 "password"이다.
        formLoginConfigurer.defaultSuccessUrl("/home"); // 로그인 성공 후 리다이렉트 URL 설정
        formLoginConfigurer.permitAll(); // 모든 권한 부여
      });

    // 2) 접근 권한 검사(인가; authorization) 필터 설정
    http.authorizeHttpRequests(authorize -> {
      // 모든 요청에 대해 권한 검사를 수행한다.
      // 로그인 되어 있지 않은 경우 로그인 폼으로 리다이렉트 한다.
      authorize.anyRequest().authenticated();
    });

    // SecurityFilterChain 준비
    return http.build();
  }
}
