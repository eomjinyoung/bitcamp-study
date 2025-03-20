package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 10 * 5
)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String requestPath = req.getPathInfo();

      // 클라이언트에서 요청을 받으면 그 요청을 처리할 서블릿으로 위임한다.
      req.getRequestDispatcher(requestPath).include(req, resp);
      Exception exception = (Exception) req.getAttribute("exception");
      if (exception != null) {
        throw exception;
      }

      // 서블릿 실행이 끝나면 JSP를 실행한다.
      String viewUrl = (String) req.getAttribute("viewUrl");
      if (viewUrl == null) {
        // 페이지 컨트롤러에서 직접 응답할 경우
        return;
      } else if (viewUrl.startsWith("redirect:")) {
        resp.sendRedirect(viewUrl.substring(9));
        return;
      }

      resp.setContentType("text/html; charset=UTF-8");
      req.getRequestDispatcher(viewUrl).include(req, resp);


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
