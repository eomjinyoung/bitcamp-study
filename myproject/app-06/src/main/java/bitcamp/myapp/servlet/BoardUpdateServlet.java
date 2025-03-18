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
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인이 필요합니다.");
      }

      Board board = new Board();
      board.setNo(Integer.parseInt(req.getParameter("no")));
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
      Board oldBoard = boardService.get(board.getNo());

      if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("변경 권한이 없습니다.");
      }

      // 파일 업로드 처리
      StorageService storageService =
              (StorageService) getServletContext().getAttribute("storageService");

      Collection<Part> parts = req.getParts();
      ArrayList<AttachedFile> fileList = new ArrayList<>();

      for (Part part : parts) {
        if (!part.getName().equals("files")) {
          continue;
        }
        // 업로드 할 때 사용할 파일명 준비
        String filename = UUID.randomUUID().toString();

        // 클라우드에 업로드
        storageService.upload("board/" + filename, part.getInputStream());

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilename(filename);
        attachedFile.setOriginFilename(part.getSubmittedFileName());
        attachedFile.setBoardNo(board.getNo());

        fileList.add(attachedFile);
      }

      board.setAttachedFiles(fileList);

      boardService.update(board);
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
