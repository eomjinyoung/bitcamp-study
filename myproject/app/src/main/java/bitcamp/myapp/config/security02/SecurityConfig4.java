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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig4 {

  private static final Log log = LogFactory.getLog(SecurityConfig4.class);

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

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    log.debug("PasswordEncoder 준비!");

    // Spring에서 제공하는 PasswordEncoder 사용하기
    return new BCryptPasswordEncoder() {
      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.printf("사용자 입력 암호: %s\n", rawPassword);
        System.out.printf("encode(사용자 입력 암호): %s\n", encode(rawPassword));
        System.out.printf("저장된 암호: %s\n", encodedPassword);
        return super.matches(rawPassword, encodedPassword);
      }
    };
  }


}

