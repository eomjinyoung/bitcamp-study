package bitcamp.myapp.config.security03;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
// - Spring Security에서 인증을 수행한 후 UserDetails 에 로그인 사용자 정보 저장하기
//   - UserDetails 커스터마이징
//     - CustomUserDetails 클래스 생성
//      - 로그인 사용자 정보(Member 객체)를 보관한다.
// - 페이지 컨트롤러에서 CustomUserDetails 사용하기
// - 이메일이 유효하지 않을 때 처리하기
//   - loadUserByUsername() 변경
//     - member 객체가 null 경우, 임의의 데이터를 넣은 객체 생성

//@Configuration
public class SecurityConfig3 {

  private static final Log log = LogFactory.getLog(SecurityConfig3.class);

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
              .successForwardUrl("/auth/success") // 로그인 성공 후 페이지 컨트롤러로 포워딩
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

