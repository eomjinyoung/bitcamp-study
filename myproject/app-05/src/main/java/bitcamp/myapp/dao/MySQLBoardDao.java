package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardDao implements BoardDao {

  private Connection con;

  public MySQLBoardDao(Connection con) {
    this.con = con;
  }

  public List<Board> findAll() {
    String sql = "select" +
            " b.board_id," +
            " b.title," +
            " b.create_date," +
            " b.view_count," +
            " m.member_id," +
            " m.name" +
            " from ed_board b" +
            " join ed_member m on b.member_id=m.member_id";

    try (PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery();
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
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public int insert(Board board) {

    String sql = "insert into ed_board(title, content, member_id) values (?, ?, ?)";


    try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getWriter().getNo());

      int count = stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys(); // 자동 생성 번호 PK 값을 꺼낼 객체 준비
      rs.next(); // 이 객체를 사용하여 서버에서 자동 생성된 PK 값을 가져온다.
      board.setNo(rs.getInt(1)); // 가져온 PK 값 중에서 첫 번째 값을 꺼낸다. PK가 여러 컬럼으로 되어 있을 경우를 대비함.

      return count;
    } catch (Exception e) {
      throw new DaoException(e);
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
