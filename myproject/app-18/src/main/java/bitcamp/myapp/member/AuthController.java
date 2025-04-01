package bitcamp.myapp.member;

import bitcamp.myapp.common.JsonResult;
import bitcamp.myapp.config.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @PostMapping("success")
  public JsonResult success(
          String saveEmail,
          @AuthenticationPrincipal CustomUserDetails principal,
          HttpSession session,
          HttpServletResponse resp) throws Exception {

    log.debug("Spring Security에서 로그인 성공한 후 마무리 작업 수행!");

    Member member = principal.getMember();
    session.setAttribute("loginUser", member);

    if (saveEmail != null) {
      Cookie emailCookie = new Cookie("email", member.getEmail());
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
      resp.addCookie(emailCookie);
    } else {
      Cookie emailCookie = new Cookie("email", "");
      emailCookie.setMaxAge(0);
      resp.addCookie(emailCookie);
    }

    return JsonResult.builder().status(JsonResult.SUCCESS).build();
  }

  @PostMapping("failure")
  public JsonResult failure() {
    return JsonResult.builder().status(JsonResult.FAILURE).build();
  }

  @GetMapping("user-info")
  public JsonResult userInfo(HttpSession session) {
    Member member = (Member) session.getAttribute("loginUser");
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
