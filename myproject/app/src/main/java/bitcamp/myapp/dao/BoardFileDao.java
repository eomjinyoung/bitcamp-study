package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;

public interface BoardFileDao {

  int insert(AttachedFile attachedFile);
  AttachedFile findByNo(int fileNo);
  int delete(int fileNo);
  int deleteAllByBoardNo(int boardNo);

}
