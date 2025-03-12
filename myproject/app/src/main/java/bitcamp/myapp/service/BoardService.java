package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.util.List;

public class BoardService {

  private BoardDao boardDao;

  public BoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  public List<Board> list() throws Exception {
    return boardDao.findAll();
  }
}
