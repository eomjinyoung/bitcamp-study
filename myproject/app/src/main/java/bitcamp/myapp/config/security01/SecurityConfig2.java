package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig2 {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 1) /home 요청은 인증을 검사하지 않게 설정
    // => 요청 URL의 권한 설정 등록기를 준비한다.
    AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry 접근권한등록기 =
            http.authorizeHttpRequests();

    // => 접근 권한 설정 1
    접근권한등록기.mvcMatchers("/home", "/css/**").permitAll();

    // => 접근 권한 설정 2
    // anyRequest() 메서드의 경우 리턴 타입이 C 라는 제네릭의 타입 파라미터이다.
    // 이 문장으로 메서드의 리턴 값이 무엇인지 컴파일러가 추론(type inference)할 수 없다.
    // 따라서 개발자가 직접 이 메서드가 리턴하는 객체의 타입으로 형변환을 수행해야 한다.
    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) 접근권한등록기.anyRequest()).authenticated();

    // 위에서 설정한 대로 작업할 필터 체인을 구성한다.
    return http.build();
  }
}
