package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLMemberDao implements MemberDao {

  private Connection con;

  public MySQLMemberDao(Connection con) {
    this.con = con;
  }

  public Member findByEmailAndPassword(String email, String password) {
    String sql = "select" +
            "      m.member_id," +
            "      m.name," +
            "      m.email" +
            "    from ed_member m" +
            "    where m.email=? and m.pwd = sha2(?, 256)";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setString(1, email);
      stmt.setString(2, password);

      try (ResultSet rs = stmt.executeQuery()) {

        if (!rs.next()) {
          return null;
        }

        Member member = new Member();
        member.setNo(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));

        return member;
      }
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
