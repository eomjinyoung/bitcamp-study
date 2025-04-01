package bitcamp.myapp.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultBoardService implements BoardService {

  private BoardDao boardDao;
  private BoardFileDao boardFileDao;


  public DefaultBoardService(
          BoardDao boardDao,
          BoardFileDao boardFileDao) {
    this.boardDao = boardDao;
    this.boardFileDao = boardFileDao;
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
    System.out.println(board.getNo());

//    int count = 0;
    for (AttachedFile file : board.getAttachedFiles()) {
//      if (count > 1) {
//        throw new ServiceException("일부러 예외 발생!");
//      }
      file.setBoardNo(board.getNo());
      boardFileDao.insert(file);
//      count++;
    }
  }

  @Override
  public Board get(int no) {
    Board board =  boardDao.findByNo(no);

    // 게시글의 첨부파일 데이터가 없더라도, BoardDao는 빈 값을 갖는 AttachedFile 객체를 List 담는다.
    // 페이지 컨트롤러에게 리턴하기 전에 불필요한 첨부파일 데이터를 제거한다.
    List<AttachedFile> files = board.getAttachedFiles();
    if (files.size() == 1 && files.get(0).getNo() == 0) {
      files.remove(0);
    }

    return board;
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
