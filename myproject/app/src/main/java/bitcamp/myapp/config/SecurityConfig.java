package bitcamp.myapp.config;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 학습 목표:
// - 로그인 페이지 커스터마이징
//   - formLogin() 변경
//     - loginPage() 추가
//     - loginProcessingUrl() 추가
//     - successForwardUrl() 변경
//     - usernameParameter() 변경 "username" ---> "email"
//     - passwordParamter() 변경 "password" ---> "password". 나중에 암호 파라미터 이름을 변경할 경우를 대비해서.

@Configuration
public class SecurityConfig {

  private static final Log log = LogFactory.getLog(SecurityConfig.class);

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.debug("SecurityFilterChain 준비!");
    return http
            // 1) 요청 URL의 접근권한 설정
            .authorizeHttpRequests()
                .regexMatchers(".*\\.html").permitAll() // 정규표현식과 일치하는 요청은 인증을 검사하지 않는다.
                .mvcMatchers("/css/**", "/js/**", "/auth/**").permitAll() // 지정된 URL의 요청은 인증을 검사하지 않는다.
                .anyRequest().authenticated() // 나머지 요청 URL은 인증을 검사한다.
                .and() // 접근제어권한설정 등록기를 만든 HttpSecurity 객체를 리턴한다.

            // 2) 인가되지 않은 요청인 경우 Spring Security 기본 로그인 화면으로 보내기
            .formLogin()
              .loginPage("/error") // Spring Security에서 로그인 폼을 제공하지 않는다.
              .loginProcessingUrl("/auth/login") // 로그인 폼의 action 값 설정. 이 요청은 Spring Security에서 처리한다.
              .successForwardUrl("/auth/success") // 로그인 성공 후 페이지 컨트롤러로 포워딩
              .failureForwardUrl("/auth/failure") // 로그인 실패 후 페이지 컨트롤러로 포워딩
              .usernameParameter("email") // 클라이언트가 보내는 이메일의 파라미터 명을 "username"에서 "email"로 바꾼다.
              .passwordParameter("password") // 암호를 보내는 파라미터 이름을 설정한다.
              .permitAll()
              .and()

            // 3) 로그아웃 설정
            .logout()
              .logoutUrl("/logout") // 로그아웃 URL 설정. 기본은 POST 요청에만 동작한다.
              // 로그아웃 결과를 직접 출력한다.
              .logoutSuccessHandler((request, response, authentication) -> {
                  response.setStatus(HttpServletResponse.SC_OK);
                  response.setContentType("application/json");
                  response.getWriter().println("{\"status\":\"success\"}");
                })
              .invalidateHttpSession(true) // 세션 무효화 설정
              .deleteCookies("JSESSIONID") // 톰캣 서버에서 세션 ID를 전달할 때 사용하는 쿠키
              .permitAll()
              .and()

            // 4) CSRF(Cross-Site Request Forgery) 설정: 기본이 활성화된 상태다.
            // - 데이터 변경에 관련된 요청(POST, PUT, PATCH, DELETE)을 받을 때 CSRF 검증을 수행한다.
            //   즉, 서버에서 발급한 유효한 CSRF 토큰이 있을 경우에만 해당 요청을 처리한다.
            //   만약 CSRF 토큰이 없거나 무효하다면 요청을 거절한다.
            // - Server-Side Rendering 방식에서는 Thymeleaf 가 CSRF 토큰을 HTML <form>에 자동 삽입했다.
            // - 그러나 Client-Side Rendering 방식에서는 HTML 폼에 CSRF 토큰을 자동으로 삽입할 수 없다.
            // - 해결책?
            //   클라이언트가 CSRF 토큰을 사용할 수 있도록 서버에서 응답할 때 쿠키로 보내도록 설정한다.
            //   XSRF-TOKEN 이라는 이름의 쿠키로 클라이언트에게 전송된다.
            // - 클라이언트에서는 데이터 변경에 관련된 요청을 할 때 마다.
            //   폼 파라미터 값(_csrf)이나 요청 헤더(X-XSRF-TOKEN)로 CSRF 토큰 값을 서버에 보내야 한다.
            .csrf()
              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
              .and()

            // 4) CORS 설정
            // - 기본으로 비활성화된 상태다.
            // - 즉 Cross-Origin 요청(다른 사이트에서 AJAX로 요청하는 것)을 차단한다.
            // - 다른 사이트에서 요청하는 것을 허용하려면 CORS를 활성화시켜야 한다.
            .cors()
              .and()

            // SecurityFilterChain 준비
            .build();
  }

  // 사용자 인증을 수행할 객체를 준비
  @Bean
  public UserDetailsService userDetailsService(MemberService memberService, PasswordEncoder passwordEncoder) {
    log.debug("DBUserDetailsService 준비!");

    // DB에 저장된 모든 사용자 암호를 BcriptPasswordEncoder를 사용해서 암호화한다.
    // => 테스트를 위해 딱 한 번만 수행한다.
//    memberService.changeAllPassword(passwordEncoder.encode("1111"));

    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.get(username);
        if (member == null) {
          member = new Member();
          member.setEmail(username);
          member.setPassword("");
        }
        return new CustomUserDetails(member);
      }
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    log.debug("PasswordEncoder 준비!");
    return new BCryptPasswordEncoder();
  }
}

