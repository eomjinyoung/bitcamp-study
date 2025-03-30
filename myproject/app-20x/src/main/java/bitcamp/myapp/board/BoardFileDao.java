package bitcamp.myapp.board;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardFileDao {

  int insert(AttachedFile attachedFile);
  AttachedFile findByNo(int fileNo);
  int delete(int fileNo);
  int deleteAllByBoardNo(int boardNo);

}
