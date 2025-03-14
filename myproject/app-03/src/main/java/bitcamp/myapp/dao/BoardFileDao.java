package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;

public interface BoardFileDao {

  int insert(AttachedFile attachedFile) throws DaoException;
  AttachedFile findByNo(int fileNo) throws DaoException;
  int delete(int fileNo);
}
