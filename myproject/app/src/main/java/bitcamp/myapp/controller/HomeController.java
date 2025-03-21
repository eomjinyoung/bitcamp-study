package bitcamp.myapp.controller;

import bitcamp.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
  @RequestMapping("/home")
  public String home(HttpServletRequest req, HttpServletResponse resp) {
    return "/home.jsp";
  }
}
