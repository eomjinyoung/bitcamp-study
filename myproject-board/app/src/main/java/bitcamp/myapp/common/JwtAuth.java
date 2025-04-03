package bitcamp.myapp.common;

import bitcamp.myapp.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuth {
  public static Member extractUserInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() instanceof Jwt jwt) { // instanceof 의 패턴 매칭 문법은 17부터 이용 가능
      //Jwt jwt = (Jwt) authentication.getPrincipal();
      Member member = new Member();
      member.setNo(Integer.parseInt(jwt.getClaim("no")));
      member.setEmail(jwt.getSubject());
      member.setName(jwt.getClaim("name"));
      member.setPhoto(jwt.getClaim("photo"));

      return member;
    }

    return null;
  }
}
