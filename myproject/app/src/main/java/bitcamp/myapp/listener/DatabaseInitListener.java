package bitcamp.myapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

@WebListener
public class DatabaseInitListener implements ServletContextListener {

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

      System.out.println("데이터베이스에 연결됨!");

    } catch (Exception e) {
      System.out.println("데이터베이스 초기화 중 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }
    } catch (Exception e) {
      System.out.println("데이터베이스 종료 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
