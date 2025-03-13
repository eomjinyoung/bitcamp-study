package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import java.util.List;

public class DefaultBoardService implements BoardService {

  private BoardDao boardDao;

  public DefaultBoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  public List<Board> list() {
    return boardDao.findAll();
  }

  public void add(Board board) {
    boardDao.insert(board);
  }

  public Board get(int no) {
    return boardDao.findByNo(no);
  }

  public void update(Board board) {
    boardDao.update(board);
  }

  public void delete(int no) {
    boardDao.delete(no);
  }
}
