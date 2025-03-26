package bitcamp.myapp.config.security03x;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig2 {

  public SecurityConfig2() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http
            // 1) 사용자 정의(개발자가 만든) 로그인폼 사용하는 필터 설정
            .formLogin(formLoginConfigurer -> {
              formLoginConfigurer.loginPage("/auth/login-form"); // 로그인 폼 URL 설정
              formLoginConfigurer.loginProcessingUrl("/auth/login"); // 로그인 처리 URL 설정
              formLoginConfigurer.usernameParameter("email"); // 기본은 "username"이다.
              formLoginConfigurer.passwordParameter("password"); // 기본은 "password"이다.
              formLoginConfigurer.defaultSuccessUrl("/home"); // 로그인 성공 후 리다이렉트 URL 설정
              formLoginConfigurer.permitAll(); // 모든 권한 부여
            })
            // 2) 접근 권한 검사(인가; authorization) 필터 설정
            .authorizeHttpRequests(authorize -> {
              authorize
                      // "/css/*" 요청은 로그인 없이 허용한다.
                      .mvcMatchers("/css/**", "/home").permitAll()
                      // 기타 다른 모든 요청은 인증 받아야 한다. 로그인 화면으로 보낸다.
                      .anyRequest().authenticated();
            })
            // SecurityFilterChain 준비
            .build();
  }
}
