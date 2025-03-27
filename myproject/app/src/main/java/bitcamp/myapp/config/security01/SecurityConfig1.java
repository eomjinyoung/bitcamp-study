package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// 학습 목표:
// - Spring Security를 설정하는 방법
// - /home 페이지는 사용자 인증 없이 접근 가능하게 한다.
//   - /home 페이지에서 사용하는 .css 파일도 사용자 인증 없이 접근 가능하게 한다.
// - 그 외 다른 페이지는 접근 불가하게 한다.

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig1 {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) /home 요청은 인증을 검사하지 않게 설정
    // => 요청 URL의 권한 설정 등록기를 준비한다.
    AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry 접근권한등록기 =
            http.authorizeHttpRequests();

    // => 등록기에 접근을 제어할 요청 URL을 설정한다.
    AuthorizeHttpRequestsConfigurer.AuthorizedUrl 접근권한제어URL = 접근권한등록기.mvcMatchers("/home", "/css/**");
    // => 해당 요청 URL의 접근을 설정한다.
    접근권한제어URL.permitAll();

    AuthorizeHttpRequestsConfigurer.AuthorizedUrl 접근권한제어URL2 =
            (AuthorizeHttpRequestsConfigurer.AuthorizedUrl) 접근권한등록기.anyRequest();
    접근권한제어URL2.authenticated();


    // 위에서 설정한 대로 작업할 필터 체인을 구성한다.
    SecurityFilterChain securityFilterChain = http.build();

    return securityFilterChain;
  }
}
