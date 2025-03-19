package bitcamp.myapp.listener;

import bitcamp.myapp.dao.*;
import bitcamp.myapp.service.*;
import bitcamp.transaction.TransactionProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.checkerframework.checker.units.qual.N;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
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

      String resource = "bitcamp/myapp/config/mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource); // 클래스 경로를 절대 경로로 바꿔 리턴
      SqlSessionFactory sqlSessionFactory =
              new SqlSessionFactoryBuilder().build(inputStream);


      con = DriverManager.getConnection(
              appProps.getProperty("jdbc.url"),
              appProps.getProperty("jdbc.username"),
              appProps.getProperty("jdbc.password"));

      ServletContext ctx = sce.getServletContext();

      MySQLMemberDao memberDao = new MySQLMemberDao(con);
      MySQLBoardDao boardDao = new MySQLBoardDao(con);
      MySQLBoardFileDao boardFileDao = new MySQLBoardFileDao(con);

      // 서비스 객체의 트랜잭션을 처리할 프록시 객체 생성기
      TransactionProxyFactory transactionProxyFactory = new TransactionProxyFactory(con);

      DefaultMemberService memberService = new DefaultMemberService(memberDao);
      ctx.setAttribute("memberService",
              transactionProxyFactory.createProxy(memberService, MemberService.class));

      DefaultBoardService boardService = new DefaultBoardService(boardDao, boardFileDao);
      ctx.setAttribute("boardService",
              transactionProxyFactory.createProxy(boardService, BoardService.class));

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
