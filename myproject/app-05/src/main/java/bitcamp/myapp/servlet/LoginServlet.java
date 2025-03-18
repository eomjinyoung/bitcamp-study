package bitcamp.myapp.servlet;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String email = req.getParameter("email");
      String password = req.getParameter("password");
      String saveEmail = req.getParameter("saveEmail");

      MemberService memberService = (MemberService) getServletContext().getAttribute("memberService");
      Member member = memberService.get(email, password);
      if (member == null) {
        resp.sendRedirect("/auth/login-form");
        return;
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
      resp.sendRedirect("/home");

    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      e.printStackTrace(printWriter);

      RequestDispatcher 요청배달자 = req.getRequestDispatcher("/error.jsp");
      req.setAttribute("exception", stringWriter.toString()); // JSP에게 오류 정보 전달
      요청배달자.forward(req, resp); // 오류가 발생하기 직전까지 출력했던 것은 버린다.
    }
  }
}
