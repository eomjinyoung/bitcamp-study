package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인이 필요합니다.");
      }

      int no = Integer.parseInt(req.getParameter("no"));

      BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
      Board board = boardService.get(no);

      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      // 네이버 클라우드 Object Storage에 업로드한 파일 삭제
      StorageService storageService = (StorageService) getServletContext().getAttribute("storageService");
      for (AttachedFile attachedFile : board.getAttachedFiles()) {
        storageService.delete("board/" + attachedFile.getFilename());
      }

      boardService.delete(no);
      resp.sendRedirect("/board/list");

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
