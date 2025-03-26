package bitcamp.myapp.config.security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig3 {

  public SecurityConfig3() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) 사용자 정의(개발자가 만든) 로그인폼 사용

    // 로그인 폼 설정 객체 준비: 익명 클래스를 파라미터에 직접 적용
    http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
      @Override
      public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
        formLoginConfigurer.loginPage("/auth/login-form"); // 로그인 폼 URL 설정
        formLoginConfigurer.loginProcessingUrl("/auth/login"); // 로그인 처리 URL 설정
        formLoginConfigurer.usernameParameter("email"); // 기본은 "username"이다.
        formLoginConfigurer.passwordParameter("password"); // 기본은 "password"이다.
        formLoginConfigurer.defaultSuccessUrl("/home"); // 로그인 성공 후 리다이렉트 URL 설정
        //formLoginConfigurer.permitAll(); // 모든 권한 부여
      }
    });

    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
}
