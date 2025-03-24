package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface BoardDao {

  List<Board> findAll();

  int insert(Board board);

  Board findByNo(int no);

  int update(Board board);

  int delete(int no);

  int updateViewCount(@Param("no") int no, @Param("increment") int increment);
}
