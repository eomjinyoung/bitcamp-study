package bitcamp.myapp.config;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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

  @Value("${jwt.public.key}")
  RSAPublicKey key;

  @Value("${jwt.private.key}")
  RSAPrivateKey priv;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.debug("SecurityFilterChain 준비!");
    return http
            // 1) 요청 URL의 접근권한 설정
            .authorizeHttpRequests()
                .anyRequest().authenticated() // 나머지 요청 URL은 인증을 검사한다.
                .and() // 접근제어권한설정 등록기를 만든 HttpSecurity 객체를 리턴한다.

            // 2) 인가되지 않은 요청인 경우 Spring Security 기본 로그인 화면으로 보내기
            .formLogin()
              .loginPage("/error") // 커스터마이징 로그인 페이지로 설정Z
              .loginProcessingUrl("/auth/login") // 로그인 폼의 action 값 설정. 이 요청은 Spring Security에서 처리한다.
              .successForwardUrl("/auth/success") // 로그인 성공 후 페이지 컨트롤러로 포워딩
              .failureForwardUrl("/auth/failure") // 로그인 실패 후 페이지 컨트롤러로 포워딩
              .usernameParameter("email") // 클라이언트가 보내는 이메일의 파라미터 명을 "username"에서 "email"로 바꾼다.
              .passwordParameter("password") // 암호를 보내는 파라미터 이름을 설정한다.
              .permitAll()
              .and()

            // 3) 로그아웃 설정
            // - JWT stateless 방식이므로 세션을 사용하지 않는다.
            // - 클라이언트 측에서 localStorage나 sessionStorage에 저장된 JWT 토큰을 삭제하면 된다.
            //.logout()

            // 4) CSRF(Cross-Site Request Forgery) 설정
            // - JWT 인증을 사용하는 경우 CSRF 비활성화 한다.
            // - CSRF는 세션, 쿠키 기반 인증에서만 필요하다.
            .csrf().disable()

            // 5) CORS(Cross-Origin Resource Sharing) 설정: 기본이 활성된 상태다.
            .cors()
              .and()

            // 6) 세션없이 JWT만 사용
            .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()

            // 7) OAuth2 인증 적용
            // - 클라이언트가 JWT 토큰을 자체 서버에서 발급 받는다.
            // - 클라이언트가 API 요청 시 "Authorization: Bearer JWT토큰" 헤더를 추가해야 한다.
            // - Spring Security가 JWT 토콘을 자동으로 검증한다.
            // - OAuth2 표준을 준수한다.
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

            // SecurityFilterChain 준비
            .build();
  }

  // CORS 필터 설정
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
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

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(this.key).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }
}

