package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import java.util.List;

public class DefaultBoardService implements BoardService {

  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  public DefaultBoardService(BoardDao boardDao, BoardFileDao boardFileDao) {
    this.boardDao = boardDao;
    this.boardFileDao = boardFileDao;
  }

  public List<Board> list() {
    return boardDao.findAll();
  }

  public void add(Board board) {
    boardDao.insert(board);

    for (AttachedFile file : board.getAttachedFiles()) {
      file.setBoardNo(board.getNo());
      boardFileDao.insert(file);
    }
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
