package bitcamp.myapp.servlet;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    MemberService memberService = (MemberService) getServletContext().getAttribute("memberService");
    Member member = memberService.get(email, password);
    if (member == null) {
      resp.sendRedirect("/auth/login-form");
      return;
    }

    resp.setContentType("text/html; charset=UTF-8");
    RequestDispatcher 요청배달자 = req.getRequestDispatcher("/auth/login.jsp");
    요청배달자.include(req, resp);
  }
}
