package bitcamp.myapp.board;

import bitcamp.myapp.cloud.StorageService;
import bitcamp.myapp.common.JsonResult;
import bitcamp.myapp.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board")
public class BoardController {

  private BoardService boardService;
  private StorageService storageService;

  public BoardController(BoardService boardService, StorageService storageService) {
    this.boardService = boardService;
    this.storageService = storageService;
  }

  @GetMapping("list")
  public JsonResult list() throws Exception {
    List<Board> list = boardService.list();
    return JsonResult.builder()
            .status(JsonResult.SUCCESS)
            .data(list)
            .build();
  }

  @GetMapping("detail")
  public JsonResult detail(int no) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      return JsonResult.builder().status(JsonResult.FAILURE).build();
    }

    boardService.increaseViewCount(no);

    return JsonResult.builder()
            .status(JsonResult.SUCCESS)
            .data(board)
            .build();
  }

  @PostMapping("add")
  public JsonResult add(
          Board board,
          Part[] files,
          HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    board.setWriter(loginUser);

    ArrayList<AttachedFile> fileList = new ArrayList<>();

    for (Part part : files) {
      if (part.getSize() == 0) {
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
      return JsonResult.builder().status(JsonResult.FAILURE).build();
    }
    return JsonResult.builder().status(JsonResult.SUCCESS).build();
  }

  @PostMapping("update")
  public String update(
          Board board,
          MultipartFile[] files,
          HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }

    Board oldBoard = boardService.get(board.getNo());
    if (oldBoard.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다.");
    }

    ArrayList<AttachedFile> fileList = new ArrayList<>();

    for (MultipartFile part : files) {
      if (part.getSize() == 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      storageService.upload("board/" + filename, part.getInputStream());

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(filename);
      attachedFile.setOriginFilename(part.getOriginalFilename());
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

  @PostMapping("delete")
  public String delete(int no, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인이 필요합니다.");
    }

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

  @GetMapping("file/download")
  public void fileDownload(int fileNo, HttpServletResponse resp) throws Exception {

    AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

    resp.setHeader("Content-Disposition", "attachment; filename=" +
            URLEncoder.encode(attachedFile.getOriginFilename(), StandardCharsets.UTF_8));
    storageService.download(
            "board/" + attachedFile.getFilename(), // 스토리지 서비스의 버킷에서 다운로드 할 파일의 경로
            resp.getOutputStream() // 다운로드 한 데이터를 보낼 클라이언트 출력 스트림
    );
  }

  @PostMapping("file/delete")
  public String fileDelete(
          @RequestParam("no") int fileNo,
          HttpSession session) throws Exception {

      Member loginUser = (Member) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인이 필요합니다.");
      }

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
