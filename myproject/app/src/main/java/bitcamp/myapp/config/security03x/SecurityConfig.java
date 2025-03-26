package bitcamp.myapp.config.security03x;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity // Spring Boot에서 자동으로 활성화시킨다.
public class SecurityConfig {

  public SecurityConfig() {
    System.out.println("SecurityConfig 생성자 호출!");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    class CustomerImpl implements Customizer<AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry> {
      @Override
      public void customize(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry authRegistry) {
        AuthorizeHttpRequestsConfigurer.AuthorizedUrl authorizedUrl =
                (AuthorizeHttpRequestsConfigurer.AuthorizedUrl) authRegistry.anyRequest();
        authorizedUrl.authenticated();
      }
    }

    return http
            .authorizeHttpRequests()
              .anyRequest()
              .authenticated()
              .and()
            // SecurityFilterChain 준비
            .build();
  }
}
