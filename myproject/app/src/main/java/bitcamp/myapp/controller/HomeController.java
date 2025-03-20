package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController {
  @RequestMapping("/home")
  public String home(HttpServletRequest req, HttpServletResponse resp) {
    return "/home.jsp";
  }
}
