package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.transaction.Transactional;

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

  @Override
  public List<Board> list() {
    return boardDao.findAll();
  }

  @Transactional
  @Override
  public void add(Board board) {
    System.out.println("DefaultBoardService.add() 호출됨!");

    boardDao.insert(board);

    int count = 0;
    for (AttachedFile file : board.getAttachedFiles()) {
      if (count > 1) {
        throw new ServiceException("일부러 예외 발생!");
      }
      file.setBoardNo(board.getNo());
      boardFileDao.insert(file);
      count++;
    }
  }

  @Override
  public Board get(int no) {
    return boardDao.findByNo(no);
  }

  @Transactional
  @Override
  public void update(Board board) {
    boardDao.update(board);

    for (AttachedFile file : board.getAttachedFiles()) {
      boardFileDao.insert(file);
    }
  }

  @Transactional
  @Override
  public void delete(int no) {
    boardFileDao.deleteAllByBoardNo(no);
    boardDao.delete(no);
  }

  @Transactional
  @Override
  public void increaseViewCount(int no) {
    boardDao.updateViewCount(no, 1);
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Transactional
  @Override
  public void deleteAttachedFile(int fileNo) {
    boardFileDao.delete(fileNo);
  }
}
