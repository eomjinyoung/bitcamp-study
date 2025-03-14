package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.AttachedFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/board/file/download")
public class BoardFileDownloadServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      int fileNo = Integer.parseInt(req.getParameter("fileNo"));
      BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

      // 응답헤더를 통해 클라이언트에 보내는 파일의 이름을 알려준다.
      // 그러면 웹브라우저는 이 이름으로 다운로드 할 수 있게 다운로드 창을 띄운다.
      resp.setHeader("Content-Disposition", "attachment; filename=" + attachedFile.getOriginFilename());

      StorageService storageService = (StorageService) getServletContext().getAttribute("storageService");
      storageService.download(
              "board/" + attachedFile.getFilename(), // 스토리지 서비스의 버킷에서 다운로드 할 파일의 경로
              resp.getOutputStream() // 다운로드 한 데이터를 보낼 클라이언트 출력 스트림
      );

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
