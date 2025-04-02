package bitcamp.myapp.config;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

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

  @Value("${jwt.private.key}")
  RSAPrivateKey privateKey;

  @Value("${jwt.public.key}")
  RSAPublicKey publicKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.debug("SecurityFilterChain 준비!");
    return http
            // 1) 요청 URL의 접근권한 설정
            .authorizeHttpRequests()
                .regexMatchers(".*\\.html").permitAll() // 정규표현식과 일치하는 요청은 인증을 검사하지 않는다.
                .mvcMatchers("/css/**", "/js/**").permitAll() // 지정된 URL의 요청은 인증을 검사하지 않는다.
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
            // - JWT 는 Stateless 방식이므로 세션을 사용하지 않는다.
            // - 클라이언트에서 단지 JWT 토큰을 삭제하면 된다.
            // .logout()

            // 4) CSRF(Cross-Site Request Forgery) 설정
            // - CSRF 토큰은 쿠키, 세션 기반 인증에서만 유효하다.
            // - JWT는 쿠키, 세션을 사용하지 않기 때문에 CSRF 토큰을 전달할 수 없다.
            // - 클라이언트에서는 JWT 토큰을 localStorage나 sessionStorage에 저장한다.
            .csrf().disable()

            // 5) CORS 설정
            // - 기본으로 비활성화된 상태다.
            // - 즉 Cross-Origin 요청(다른 사이트에서 AJAX로 요청하는 것)을 차단한다.
            // - 다른 사이트에서 요청하는 것을 허용하려면 CORS를 활성화시켜야 한다.
            .cors()
              .and()

            // 6) OAuth2 설정
            // - 이 필터는 JWT 토큰을 검증하는 일을 한다.
            // - JWT 토큰은 이 서버에서 발급한다.
            // - 클라이언트는 서버에 요청할 때, 서버가 발급한 JWT 토큰을
            //   "Authorization: Bearer <JWT토큰>" 헤더에 붙여 보내야 한다.
            // - OAuth2 인증 표준을 준수하는 필터다. 다른 OAuth2 인증 서버와 연동 가능하다.
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

            // SecurityFilterChain 준비
            .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 허용할 도메인
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*")); // 모든 요청 헤더를 수락한다.
    configuration.setAllowCredentials(true); // 다른 사이트와의 쿠키 및 세션, HTTP 인증 헤더 전송을 허용

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
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

  /**
   * JWT 서명을 위한 JwtEncoder 생성
   */
  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }

  /**
   * JWT 검증을 위한 JwtDecoder 생성
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }
}

