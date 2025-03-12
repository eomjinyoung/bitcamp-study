package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

  private Connection con;

  public BoardDao(Connection con) {
    this.con = con;
  }

  public List<Board> findAll() throws Exception {
    /*
    select
      b.board_id,
      b.title,
      b.create_date,
      b.view_count,
      m.member_id,
      m.name
    from ed_board b
         join ed_member m on b.member_id=m.member_id;
     */
    String sql = "select" +
            " b.board_id," +
            " b.title," +
            " b.create_date," +
            " b.view_count," +
            " m.member_id," +
            " m.name" +
            " from ed_board b" +
            " join ed_member m on b.member_id=m.member_id";

    try (Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
    ) {

      ArrayList<Board> list = new ArrayList<>();

      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("title"));
        board.setCreateDate(rs.getDate("create_date"));
        board.setViewCount(rs.getInt("view_count"));

        Member member = new Member();
        member.setNo(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        board.setWriter(member);

        list.add(board);
      }

      return list;
    }
  }
}
