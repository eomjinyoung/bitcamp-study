package bitcamp.myapp.member;

import bitcamp.myapp.common.JsonResult;
import bitcamp.myapp.common.JwtAuth;
import bitcamp.myapp.config.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);

  private JwtEncoder encoder;

  public AuthController(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @PostMapping("success")
  public JsonResult success(@AuthenticationPrincipal CustomUserDetails principal) throws Exception {
    log.debug("Spring Security에서 로그인 성공한 후 마무리 작업 수행!");

    Member member = principal.getMember();

    Instant now = Instant.now();
    long expiry = 36000L;
    String scope = principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(principal.getUsername())
        .claims(map -> {
          // 사용자 권한 정보
          map.put("scope", scope);

          // 사용자 기타 정보
          map.put("no", String.valueOf(member.getNo()));
          map.put("name", member.getName());
          map.put("email", member.getEmail());
        })
        .build();

    return JsonResult.builder()
        .status(JsonResult.SUCCESS)
        .data(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
        .build();
  }

  @PostMapping("failure")
  public JsonResult failure() {
    return JsonResult.builder().status(JsonResult.FAILURE).build();
  }

  @GetMapping("user-info")
  public JsonResult userInfo() {

    Member member = JwtAuth.extractUserInfo();
    if (member == null) {
      return JsonResult.builder()
          .status(JsonResult.FAILURE)
          .build();
    }

    return JsonResult.builder()
        .status(JsonResult.SUCCESS)
        .data(member)
        .build();
  }

}
