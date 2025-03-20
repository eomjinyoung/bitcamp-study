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

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인이 필요합니다.");
      }

      int fileNo = Integer.parseInt(req.getParameter("no"));

      BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);
      Board board = boardService.get(attachedFile.getBoardNo());

      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      StorageService storageService = (StorageService) getServletContext().getAttribute("storageService");
      storageService.delete("board/" + attachedFile.getFilename());

      boardService.deleteAttachedFile(fileNo);

      req.setAttribute("viewUrl", "redirect:../detail?no=" + board.getNo());

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
