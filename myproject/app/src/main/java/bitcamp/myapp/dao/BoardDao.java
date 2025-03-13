package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface BoardDao {

  List<Board> findAll() throws Exception;

  int insert(Board board) throws Exception;

  Board findByNo(int no) throws Exception;

  int update(Board board) throws Exception;

  int delete(int no) throws Exception;
}
