package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      int no = Integer.parseInt(req.getParameter("no"));

      BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
      boardService.increaseViewCount(no);
      Board board = boardService.get(no);

      req.setAttribute("board", board);

      resp.setContentType("text/html; charset=UTF-8");
      req.setAttribute("viewUrl", "/board/detail.jsp");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
