package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardDao implements BoardDao {

  private SqlSessionFactory sqlSessionFactory;
  private Connection con;

  public MySQLBoardDao(Connection con, SqlSessionFactory sqlSessionFactory) {
    this.con = con;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public List<Board> findAll() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardDao.findAll");
    }
  }

  public int insert(Board board) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.insert("BoardDao.insert", board);
      sqlSession.commit();
      return count;
    }
  }

  public Board findByNo(int no) {
    String sql = "select" +
            "         b.board_id," +
            "         b.title," +
            "         b.content," +
            "         b.create_date," +
            "         b.view_count," +
            "         m.member_id," +
            "         m.name," +
            "         af.af_id," +
            "         af.filename," +
            "         af.origin_filename" +
            "     from ed_board b" +
            "         inner join ed_member m on b.member_id=m.member_id" +
            "         left outer join ed_attach_file af on b.board_id=af.board_id" +
            "     where" +
            "         b.board_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {

        Board board = null;
        ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

        while (rs.next()) {
          if (board == null) {
            board = new Board();
            board.setNo(rs.getInt("board_id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setCreateDate(rs.getDate("create_date"));
            board.setViewCount(rs.getInt("view_count"));

            Member member = new Member();
            member.setNo(rs.getInt("member_id"));
            member.setName(rs.getString("name"));

            board.setWriter(member);
            board.setAttachedFiles(attachedFiles);
          }

          if (rs.getInt("af_id") > 0) {
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setNo(rs.getInt("af_id"));
            attachedFile.setFilename(rs.getString("filename"));
            attachedFile.setOriginFilename(rs.getString("origin_filename"));

            attachedFiles.add(attachedFile);
          }
        }
        return board;
      }

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public int update(Board board) {
    String sql = "update ed_board set " +
            "          title=?," +
            "          content=?" +
            "      where" +
            "          board_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());

      return stmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public int delete(int no) {
    String sql = "delete from ed_board" +
            "    where board_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int updateViewCount(int no, int increment) {
    String sql = "update ed_board set " +
            "          view_count = view_count + ?" +
            "      where board_id = ?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, increment);
      stmt.setInt(2, no);
      return stmt.executeUpdate();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
