package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardFileDao implements BoardFileDao {

  private Connection con;

  public MySQLBoardFileDao(Connection con) {
    this.con = con;
  }

  public int insert(AttachedFile attachedFile) {
    String sql = "insert into ed_attach_file(board_id, filename, origin_filename) values (?, ?, ?)";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setInt(1, attachedFile.getBoardNo());
      stmt.setString(2, attachedFile.getFilename());
      stmt.setString(3, attachedFile.getOriginFilename());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

  public AttachedFile findByNo(int fileNo) {
    String sql = "select" +
            "        af_id," +
            "        board_id," +
            "        filename," +
            "        origin_filename" +
            "      from ed_attach_file" +
            "      where af_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setInt(1, fileNo);

      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return null;
      }

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setNo(rs.getInt("af_id"));
      attachedFile.setBoardNo(rs.getInt("board_id"));
      attachedFile.setFilename(rs.getString("filename"));
      attachedFile.setOriginFilename(rs.getString("origin_filename"));

      return attachedFile;

    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

  @Override
  public int delete(int fileNo) {
    String sql = "delete from ed_attach_file" +
            "      where af_id=?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, fileNo);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
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
