package bitcamp.myapp.config.security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 학습 목표:
// - 사용자 인증이 필요한 페이지를 요청할 시 기본 로그인 폼으로 보내기

//@Configuration
public class SecurityConfig1 {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            // 1) 요청 URL의 접근권한 설정
            .authorizeHttpRequests()
                .mvcMatchers("/home", "/css/**").permitAll() // "/home" 과 "/css/**" 는 인증을 검사하지 않는다.
                .anyRequest().authenticated() // 나머지 요청 URL은 인증을 검사한다.
                .and() // 접근제어권한설정 등록기를 만든 HttpSecurity 객체를 리턴한다.
            // 2) 인가되지 않은 요청인 경우 Spring Security 기본 로그인 화면으로 보내기
            .formLogin(Customizer.withDefaults())
            // SecurityFilterChain 준비
            .build();
  }
}
