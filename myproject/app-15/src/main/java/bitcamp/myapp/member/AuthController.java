package bitcamp.myapp.member;

import bitcamp.myapp.config.security04.CustomUserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);

  private MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("login-form")
  public void form(
          @CookieValue(value="email", required=false) String email,
          Model model) {
    model.addAttribute("email", email);
  }

  @PostMapping("success")
  public String success(
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

    return "redirect:/home";
  }

}
