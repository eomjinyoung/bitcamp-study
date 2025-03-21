package bitcamp.myapp.controller;

import bitcamp.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloController {
  @RequestMapping("/hello")
  public String hello(HttpServletRequest req, HttpServletResponse resp) {
    return "/hello.jsp";
  }

  @RequestMapping("/hello2")
  public String hello2(HttpServletRequest req, HttpServletResponse resp) {
    return "/hello2.jsp";
  }
}
