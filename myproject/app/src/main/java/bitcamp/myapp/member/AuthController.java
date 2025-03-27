package bitcamp.myapp.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

  @PostMapping("login")
  public String login(
          String username,
          HttpSession session) throws Exception {

    log.debug("=============> /auth/login 요청 처리!");

    Member member = memberService.get(username);
    if (member == null) {
      return "redirect:login-form";
    }
/*
    if (saveEmail != null) {
      Cookie emailCookie = new Cookie("email", email);
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
      resp.addCookie(emailCookie);
    } else {
      Cookie emailCookie = new Cookie("email", "");
      emailCookie.setMaxAge(0);
      resp.addCookie(emailCookie);
    }
*/
    session.setAttribute("loginUser", member);
    return "redirect:/home";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/home";
  }
}
