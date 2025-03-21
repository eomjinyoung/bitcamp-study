package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BoardController {

  private BoardService boardService;
  private StorageService storageService;

  public BoardController(BoardService boardService, StorageService storageService) {
    this.boardService = boardService;
    this.storageService = storageService;
  }

  @RequestMapping("/board/list")
  public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    List<Board> list = boardService.list();
    req.setAttribute("list", list);
    return "/board/list.jsp";
  }

  @RequestMapping("/board/detail")
  public String detail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    int no = Integer.parseInt(req.getParameter("no"));
    boardService.increaseViewCount(no);
    Board board = boardService.get(no);
    req.setAttribute("board", board);
    return "/board/detail.jsp";
  }

  @RequestMapping("/board/form")
  public String form(HttpServletRequest req, HttpServletResponse resp) {
    return "/board/form.jsp";
  }

  @RequestMapping("/board/add")
  public String add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    Board board = new Board();
    board.setTitle(req.getParameter("title"));
    board.setContent(req.getParameter("content"));
    board.setWriter(loginUser);

    Collection<Part> parts = req.getParts();
    ArrayList<AttachedFile> fileList = new ArrayList<>();

    for (Part part : parts) {
      if (!part.getName().equals("files")) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      storageService.upload("board/" + filename, part.getInputStream());

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(filename);
      attachedFile.setOriginFilename(part.getSubmittedFileName());
      fileList.add(attachedFile);
    }
    board.setAttachedFiles(fileList);
    try {
      boardService.add(board);
    } catch (Exception e) {
      for (AttachedFile file : board.getAttachedFiles()) {
        storageService.delete("board/" + file.getFilename());
      }
      throw e;
    }
    return "redirect:list";
  }

  @RequestMapping("/board/update")
  public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    Board board = new Board();
    board.setNo(Integer.parseInt(req.getParameter("no")));
    board.setTitle(req.getParameter("title"));
    board.setContent(req.getParameter("content"));

    Board oldBoard = boardService.get(board.getNo());
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다.");
    }

    Collection<Part> parts = req.getParts();
    ArrayList<AttachedFile> fileList = new ArrayList<>();

    for (Part part : parts) {
      if (!part.getName().equals("files")) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      storageService.upload("board/" + filename, part.getInputStream());

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(filename);
      attachedFile.setOriginFilename(part.getSubmittedFileName());
      attachedFile.setBoardNo(board.getNo());
      fileList.add(attachedFile);
    }

    board.setAttachedFiles(fileList);

    try {
      boardService.update(board);
    } catch (Exception e) {
      for (AttachedFile file : board.getAttachedFiles()) {
        storageService.delete("board/" + file.getFilename());
      }
      throw e;
    }

    return "redirect:list";
  }

  @RequestMapping("/board/delete")
  public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Member loginUser = (Member) req.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    int no = Integer.parseInt(req.getParameter("no"));

    Board board = boardService.get(no);
    if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("삭제 권한이 없습니다.");
    }

    for (AttachedFile attachedFile : board.getAttachedFiles()) {
      storageService.delete("board/" + attachedFile.getFilename());
    }

    boardService.delete(no);
    return "redirect:list";
  }

  @RequestMapping("/board/file/download")
  public String fileDownload(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    int fileNo = Integer.parseInt(req.getParameter("fileNo"));
    AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

    resp.setHeader("Content-Disposition", "attachment; filename=" + attachedFile.getOriginFilename());
    storageService.download(
            "board/" + attachedFile.getFilename(), // 스토리지 서비스의 버킷에서 다운로드 할 파일의 경로
            resp.getOutputStream() // 다운로드 한 데이터를 보낼 클라이언트 출력 스트림
    );
    return null;
  }

  @RequestMapping("/board/file/delete")
  public String fileDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      Member loginUser = (Member) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인이 필요합니다.");
      }

      int fileNo = Integer.parseInt(req.getParameter("no"));
      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);
      Board board = boardService.get(attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      storageService.delete("board/" + attachedFile.getFilename());
      boardService.deleteAttachedFile(fileNo);
      return "redirect:../detail?no=" + board.getNo();
  }
}
