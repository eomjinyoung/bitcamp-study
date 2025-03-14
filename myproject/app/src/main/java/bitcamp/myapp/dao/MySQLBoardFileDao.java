package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.sql.Connection;
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
    /*
      insert into ed_attach_file(board_id, filename, origin_filename)
      values (1, 'aaaa', 'a.gif')
     */
    String sql = "insert into ed_attach_file(board_id, filename, origin_filename) values (" +
            attachedFile.getBoardNo() + ", " +
            "'" + attachedFile.getFilename() + "', " +
            "'" + attachedFile.getOriginFilename() + "')";


    try (Statement stmt = con.createStatement()) {
      return stmt.executeUpdate(sql);
    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

  public AttachedFile findByNo(int fileNo) {
    /*
      select
        af_id,
        board_id,
        filename,
        origin_filename
      from ed_attach_file
      where af_id=fileNo
     */
    String sql = "select" +
            "        af_id," +
            "        board_id," +
            "        filename," +
            "        origin_filename" +
            "      from ed_attach_file" +
            "      where af_id=" + fileNo;

    try (Statement stmt = con.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
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
}
