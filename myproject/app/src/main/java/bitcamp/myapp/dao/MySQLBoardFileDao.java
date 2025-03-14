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
}
