package bitcamp.myapp.config.security03;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 학습 목표:
// - Spring Security에서 인증을 수행한 후 세션 처리를 위해 페이지 컨트롤러에게 넘기기
//   - formLogin() 설정 변경
//     - successForwardUrl("/auth/login")
//   - logout() 설정 추가

//@Configuration
public class SecurityConfig2 {

  private static final Log log = LogFactory.getLog(SecurityConfig2.class);

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.debug("SecurityFilterChain 준비!");
    return http
            // 1) 요청 URL의 접근권한 설정
            .authorizeHttpRequests()
                .mvcMatchers("/home", "/css/**").permitAll() // "/home" 과 "/css/**" 는 인증을 검사하지 않는다.
                .anyRequest().authenticated() // 나머지 요청 URL은 인증을 검사한다.
                .and() // 접근제어권한설정 등록기를 만든 HttpSecurity 객체를 리턴한다.

            // 2) 인가되지 않은 요청인 경우 Spring Security 기본 로그인 화면으로 보내기
            .formLogin()
              .successForwardUrl("/auth/login") // 로그인 성공 후 페이지 컨트롤러로 포워딩
              .permitAll()
              .and()

            // 3) 로그아웃 설정
            // - formLogin()을 커스터마이징한다면, /logout 경로가 비활성화된다.
            // - 따라서 다음과 같이 명시적으로 설정해야 한다.
            .logout()
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // GET 요청 허용
              //.logoutUrl("/logout") // 로그아웃 URL 설정. 기본은 POST 요청에만 동작한다.
              .logoutSuccessUrl("/home") // 로그아웃 성공 후 이동 페이지
              .invalidateHttpSession(true) // 세션 무효화 설정
              .deleteCookies("JSESSIONID") // 톰캣 서버에서 세션 ID를 전달할 때 사용하는 쿠키
              .permitAll()
              .and()

            // 4) CSRF(Cross-Site Request Forgery) 기능 비활성화 : 기본은 활성화이다.
            // - 로그아웃 할 때 세션을 무효화시키면 세션에 보관된 CSRF 토큰도 함께 삭제된다.
            // - 클라이언트가 요청했을 때 그 클라이언트에게 발급된 유효한 CSRF 토큰이 없으면
            //   CSRF 공격으로 보고 요청을 거절한다.
            // - 이 기능을 당분간 무효화시킨다.
            //.csrf().disable()

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
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
      }
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    log.debug("PasswordEncoder 준비!");
    return new BCryptPasswordEncoder();
  }
}

