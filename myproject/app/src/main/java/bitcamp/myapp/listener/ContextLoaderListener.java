package bitcamp.myapp.listener;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLMemberDao;
import bitcamp.myapp.service.*;
import org.checkerframework.checker.units.qual.N;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  private Connection con;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      // 1. JDBC Driver 로딩(java.sql.Driver 구현체 로딩)
//      Class.forName("com.mysql.jdbc.Driver");

      // 2. Driver 구현 객체 생성
//      Driver driver = new com.mysql.jdbc.Driver();

      // 3. Driver 객체를 JDBC 드라이버 관리자에 등록
//      DriverManager.registerDriver(driver);

      // 4. DB에 연결
      con = DriverManager.getConnection(
              "jdbc:mysql://db-32e40j-kr.vpc-pub-cdb.ntruss.com:3306/studentdb",
              "student",
              "bitcamp123!@#");

      ServletContext ctx = sce.getServletContext();

      MySQLMemberDao memberDao = new MySQLMemberDao(con);
      MySQLBoardDao boardDao = new MySQLBoardDao(con);

      DefaultMemberService memberService = new DefaultMemberService(memberDao);
      ctx.setAttribute("memberService", memberService);

      DefaultBoardService boardService = new DefaultBoardService(boardDao);
      ctx.setAttribute("boardService", boardService);

      NCPObjectStorageService storageService = new NCPObjectStorageService();
      ctx.setAttribute("storageService", storageService);

      System.out.println("웹애플리케이션 실행 환경 준비!");

    } catch (Exception e) {
      System.out.println("웹애플리케이션 실행 환경 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }

      System.out.println("웹애플리케이션 자원 해제!");

    } catch (Exception e) {
      System.out.println("웹애플리케이션 실행 환경 해제 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
