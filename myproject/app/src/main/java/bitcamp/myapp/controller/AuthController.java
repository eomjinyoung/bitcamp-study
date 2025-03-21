package bitcamp.myapp.controller;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;
import bitcamp.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

  private MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/auth/login-form")
  public String form(HttpServletRequest req, HttpServletResponse resp) {
    return "/auth/login-form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    String saveEmail = req.getParameter("saveEmail");

    Member member = memberService.get(email, password);
    if (member == null) {
      return "redirect:login-form";
    }

    if (saveEmail != null) {
      Cookie emailCookie = new Cookie("email", email);
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
      resp.addCookie(emailCookie);
    } else {
      Cookie emailCookie = new Cookie("email", "");
      emailCookie.setMaxAge(0);
      resp.addCookie(emailCookie);
    }

    req.getSession().setAttribute("loginUser", member);
    return "redirect:../home";
  }

  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest req, HttpServletResponse resp) {
    req.getSession().invalidate();
    return "redirect:../home";
  }
}
