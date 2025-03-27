package bitcamp.myapp.config.security02;

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

// 학습 목표:
// - UserDetailsService와 UserDetails 사용법
// - User 클래스를 이용하여 UserDetails 객체를 만드는 방법
// - InMemoryUserDetailsManager 클래스를 사용하여 UserDetailsService 객체 만들기


//@Configuration
public class SecurityConfig2 {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    System.out.println("SecurityFilterChain 준비!");
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
  public UserDetailsService userDetailsService() {
    System.out.println("UserDetailsService 준비!");

    // 임시 사용자 정보 생성
    // 사용자가 입력한 암호를 저장할 때 날 것 그대로 저장하는 것이 아니라,
    // 특별한 알고리즘으로 가공하여 저장한다.
    // 로그인을 처리할 때도 사용자가 입력한 암호를 비교할 때,
    // 내부에서 지정된 알고리즘으로 가공한 후에 저장된 값과 비교한다.
    UserDetails[] userDetails = {
            User.withDefaultPasswordEncoder().username("user1@test.com").password("1111").roles("USER").build(),
            User.withDefaultPasswordEncoder().username("user2@test.com").password("1111").roles("USER").build(),
            User.withDefaultPasswordEncoder().username("user3@test.com").password("1111").roles("USER").build(),
    };

    // 메모리에 사용자 목록을 두고 검사를 수행하는 객체 리턴
    return new InMemoryUserDetailsManager(userDetails);
  }
}
