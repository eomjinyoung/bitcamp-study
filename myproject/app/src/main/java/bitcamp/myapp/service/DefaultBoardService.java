package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import java.sql.Connection;
import java.util.List;

public class DefaultBoardService implements BoardService {

  private BoardDao boardDao;
  private BoardFileDao boardFileDao;
  private Connection con;


  public DefaultBoardService(
          BoardDao boardDao,
          BoardFileDao boardFileDao,
          Connection con) {
    this.boardDao = boardDao;
    this.boardFileDao = boardFileDao;
    this.con = con;
  }

  public List<Board> list() {
    return boardDao.findAll();
  }

  public void add(Board board) {
    try {
      con.setAutoCommit(false);

      boardDao.insert(board);

      int count = 0;
      for (AttachedFile file : board.getAttachedFiles()) {
        file.setBoardNo(board.getNo());
        boardFileDao.insert(file);
        count++;
        if (count > 1) {
          throw new Exception("일부러 예외 발생!");
        }
      }

      con.commit();

    } catch (Exception e) {
      try {con.rollback();} catch (Exception e2) {}
      throw new ServiceException(e);

    } finally {
      try {con.setAutoCommit(true);} catch (Exception e) {}

    }
  }

  public Board get(int no) {
    return boardDao.findByNo(no);
  }

  public void update(Board board) {
    boardDao.update(board);

    for (AttachedFile file : board.getAttachedFiles()) {
      boardFileDao.insert(file);
    }
  }

  public void delete(int no) {
    boardFileDao.deleteAllByBoardNo(no);
    boardDao.delete(no);
  }

  @Override
  public void increaseViewCount(int no) {
    boardDao.updateViewCount(no, 1);
  }

  public AttachedFile getAttachedFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public void deleteAttachedFile(int fileNo) {
    boardFileDao.delete(fileNo);
  }
}
