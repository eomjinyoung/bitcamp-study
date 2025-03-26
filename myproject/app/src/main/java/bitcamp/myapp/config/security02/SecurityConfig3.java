package bitcamp.myapp.config.security02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
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
            .formLogin(Customizer.withDefaults())
            // SecurityFilterChain 준비
            .build();
  }

  // 사용자 인증을 수행할 객체를 준비
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    log.debug("UserDetailsService 준비!");

    // 임시 사용자 정보 생성
    // 사용자가 입력한 암호를 저장할 때 날 것 그대로 저장하는 것이 아니라,
    // 특별한 알고리즘으로 가공하여 저장한다.
    // 로그인을 처리할 때도 사용자가 입력한 암호를 비교할 때,
    // 내부에서 지정된 알고리즘으로 가공한 후에 저장된 값과 비교한다.
    UserDetails[] userDetails = {
            User.builder()
                    .username("user1@test.com")
                    .password(passwordEncoder.encode("1111"))
                    .roles("USER")
                    .build(),
            User.builder()
                    .username("user2@test.com")
                    .password(passwordEncoder.encode("1111"))
                    .roles("USER")
                    .build(),
            User.builder()
                    .username("user3@test.com")
                    .password(passwordEncoder.encode("1111"))
                    .roles("USER")
                    .build(),

    };

    // 메모리에 사용자 목록을 두고 검사를 수행하는 객체 리턴
    return new InMemoryUserDetailsManager(userDetails);
  }

  // Spring Security에 기본 장착된 PasswordEncoder를 우리가 만든 인코더로 바꾼다.
  // Spring Security에서 사용자가 입력한 로그인 정보 중에 암호를 비교할 때,
  // 이 메서드가 리턴한 PasswordEncoder를 사용하여 비교한다.
  // 즉 SimplePasswordEncoder.matches() 메서드를 호출하여 비교한다.
  // 리턴 값이 true이면 로그인 성공이다.
  @Bean
  public PasswordEncoder passwordEncoder() {
    log.debug("PasswordEncoder 준비!");
    // 사용자가 입력한 암호를 그대로 보지 못하도록 가공하는 클래스 정의
    // => 일단 테스트를 위해 암호를 가공하지 않고 그대로 리턴하게 한다.
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence rawPassword) {
        return rawPassword.toString(); // 가공하지 않는다. 원래 암호 그대로 리턴.
      }
      @Override
      public boolean matches(
              CharSequence rawPassword, // 로그인 폼에 입력한 암호
              String encodedPassword // UserDetailsService 에 저장된 암호
      ) {
        log.debug("암호 비교:");
        log.debug("로그인 폼에 입력한 암호: " + rawPassword);
        log.debug("저장되어 있는 사용자 암호: " + encodedPassword);
        // 저장된 암호와 비교하기 전에 사용자 입력한 암호를 먼저 가공한다.
        String 가공된암호 = this.encode(rawPassword);
        return encodedPassword.equals(가공된암호);
      }
    };
  }


}
