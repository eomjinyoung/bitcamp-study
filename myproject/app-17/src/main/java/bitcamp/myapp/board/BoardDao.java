package bitcamp.myapp.board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {

  List<Board> findAll();

  int insert(Board board);

  Board findByNo(int no);

  int update(Board board);

  int delete(int no);

  int updateViewCount(@Param("no") int no, @Param("increment") int increment);
}
