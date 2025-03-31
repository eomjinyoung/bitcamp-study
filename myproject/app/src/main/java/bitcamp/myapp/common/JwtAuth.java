package bitcamp.myapp.common;

import bitcamp.myapp.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuth {
  public static Member extractUserInfo() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() instanceof Jwt jwt) {
      Member member = new Member();
      member.setNo(Integer.parseInt(jwt.getClaim("no")));
      member.setName(jwt.getClaim("name"));
      member.setEmail(jwt.getClaim("email"));
      return member;
    }

    return null;
  }
}
