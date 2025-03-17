package bitcamp.myapp.listener;

import bitcamp.myapp.dao.*;
import bitcamp.myapp.service.*;
import org.checkerframework.checker.units.qual.N;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  private Connection con;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      String userHome = System.getProperty("user.home");
      Properties appProps = new Properties();
      appProps.load(new FileReader(userHome + "/config/bitcamp-study.properties"));

      con = DriverManager.getConnection(
              appProps.getProperty("jdbc.url"),
              appProps.getProperty("jdbc.username"),
              appProps.getProperty("jdbc.password"));

      ServletContext ctx = sce.getServletContext();

      MySQLMemberDao memberDao = new MySQLMemberDao(con);
      MySQLBoardDao boardDao = new MySQLBoardDao(con);
      MySQLBoardFileDao boardFileDao = new MySQLBoardFileDao(con);

      DefaultMemberService memberService = new DefaultMemberService(memberDao);
      ctx.setAttribute("memberService", memberService);

      DefaultBoardService boardService = new DefaultBoardService(boardDao, boardFileDao);
      ctx.setAttribute("boardService", boardService);

      NCPObjectStorageService storageService = new NCPObjectStorageService(appProps);
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
