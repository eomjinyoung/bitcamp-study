package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface BoardDao {

  List<Board> findAll() throws DaoException;

  int insert(Board board) throws DaoException;

  Board findByNo(int no) throws DaoException;

  int update(Board board) throws DaoException;

  int delete(int no) throws DaoException;
}
