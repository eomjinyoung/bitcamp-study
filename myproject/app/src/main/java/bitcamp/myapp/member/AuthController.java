package bitcamp.myapp.member;

import bitcamp.myapp.common.JsonResult;
import bitcamp.myapp.common.JwtAuth;
import bitcamp.myapp.common.LoginUser;
import bitcamp.myapp.config.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);

  private JwtEncoder jwtEncoder;

  public AuthController(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  @PostMapping("success")
  public JsonResult success(@AuthenticationPrincipal CustomUserDetails principal) throws Exception {

    log.debug("Spring Security에서 로그인 성공한 후 마무리 작업 수행!");

    Member member = principal.getMember();

    // 사용자 권한 정보 --> String
    String roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    log.debug("사용자 역할: " + roles);

    // 현재 시간 알아내기
    Instant now = Instant.now();

    // JWT 토큰 생성을 위한 설정
    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuer("bitcamp-myproject") // JWT 토큰 발급자 이름
            .issuedAt(now) // JWT 토큰 발급 시간
            .expiresAt(now.plus(1, ChronoUnit.HOURS)) // 만료 시간 (1시간 후)
            .subject(principal.getUsername()) // 로그인할 때 넘어온 username 값

            // 사용자 역할 데이터 추가
            .claim("roles", roles)

            // 토큰에 저장할 추가 데이터.(추가 값을 설정하는 객체를 파라미터로 넘긴다.)
            .claims(map -> {
              map.put("no", String.valueOf(member.getNo()));
              map.put("email", member.getEmail());
              map.put("name", member.getName());
              map.put("photo", member.getPhoto());
            })

            .build();

    // JWT 설정을 사용하여 JWT 생성에 사용할 파라미터 준비
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);

    // JWT 토큰 생성
    Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

    // JWT 토큰을 String으로 변환
    String jwtToken = jwt.getTokenValue();

    return JsonResult.builder()
            .status(JsonResult.SUCCESS)
            .data(jwtToken)
            .build();
  }

  @PostMapping("failure")
  public JsonResult failure() {
    return JsonResult.builder().status(JsonResult.FAILURE).build();
  }

  @GetMapping("user-info")
  public JsonResult userInfo(@LoginUser Member member) {
    //Member member = JwtAuth.extractUserInfo();

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
