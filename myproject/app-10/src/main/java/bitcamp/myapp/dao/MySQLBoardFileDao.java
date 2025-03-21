package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.stereotype.Component;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySQLBoardFileDao implements BoardFileDao {

  private SqlSessionFactory sqlSessionFactory;

  public MySQLBoardFileDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int insert(AttachedFile attachedFile) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("BoardFileDao.insert", attachedFile);
    }
  }

  public AttachedFile findByNo(int fileNo) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("BoardFileDao.findByNo", fileNo);
    }
  }

  @Override
  public int delete(int fileNo) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardFileDao.delete", fileNo);
    }
  }

  @Override
  public int deleteAllByBoardNo(int boardNo) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardFileDao.deleteAllByBoardNo", boardNo);
    }
  }
}
