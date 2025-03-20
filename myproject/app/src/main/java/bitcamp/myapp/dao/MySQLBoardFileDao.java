package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardFileDao implements BoardFileDao {

  private SqlSessionFactory sqlSessionFactory;
  private Connection con;

  public MySQLBoardFileDao(Connection con, SqlSessionFactory sqlSessionFactory) {
    this.con = con;
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
    String sql = "delete from ed_attach_file" +
            "      where board_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, boardNo);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
