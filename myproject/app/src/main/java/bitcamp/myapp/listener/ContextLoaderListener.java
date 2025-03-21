package bitcamp.myapp.listener;

import bitcamp.myapp.controller.BoardController;
import bitcamp.myapp.controller.HomeController;
import bitcamp.myapp.controller.AuthController;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLBoardFileDao;
import bitcamp.myapp.dao.MySQLMemberDao;
import bitcamp.myapp.service.*;
import bitcamp.transaction.SqlSessionFactoryProxy;
import bitcamp.transaction.TransactionProxyFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  private ServletContext servletContext;
  private Connection con;
  private ArrayList<Class> classList = new ArrayList<>();
  private HashMap<String,Object> beanMap = new HashMap<>();

  private void createObjects(String packageName) throws Exception {
    String packagePath = packageName.replaceAll("\\.", "/");
    File path = Resources.getResourceAsFile(packagePath);

    findClasses(path);
  }

  private void findClasses(File path) throws Exception {
    File[] files = path.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class")); // 현재 경로에 있는 모든 디렉토리와 파일을 알아낸다.
    for (File file : files) {
      if (file.isDirectory()) {
        findClasses(file);
      } else {
        System.out.println(file.getName());
      }
    }
  }



  @Override
  public void contextInitialized(ServletContextEvent sce) {

    this.servletContext = sce.getServletContext();

    try {
      String userHome = System.getProperty("user.home");
      Properties appProps = new Properties();
      appProps.load(new FileReader(userHome + "/config/bitcamp-study.properties"));

      // DataSource 구현체 준비: DB 커넥션풀 준비
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(appProps.getProperty("jdbc.driver"));
      dataSource.setUrl(appProps.getProperty("jdbc.url"));
      dataSource.setUsername(appProps.getProperty("jdbc.username"));
      dataSource.setPassword(appProps.getProperty("jdbc.password"));

      // 트랜잭션 제어 객체 준비
      TransactionFactory transactionFactory = new JdbcTransactionFactory();

      // 위에서 준비한 DataSource와 TransactionFactory를 가지고 DB 환경을 준비.
      Environment environment = new Environment("development", transactionFactory, dataSource);

      // Mybatis 설정에 DB 환경을 등록한다.
      Configuration configuration = new Configuration(environment);

      // bitcamp.myapp.vo 패키지에 있는 모든 클래스에 별명을 추가한다.
      TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
      typeAliasRegistry.registerAliases("bitcamp.myapp.vo");

      // SQL 매퍼 파일을 등록한다.
      String[] resources = {
              "bitcamp/myapp/mapper/BoardDao.xml",
              "bitcamp/myapp/mapper/BoardFileDao.xml",
              "bitcamp/myapp/mapper/MemberDao.xml"
      };
      for (String resource : resources) {
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
          XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(
                  inputStream, configuration, resource, configuration.getSqlFragments());
          mapperBuilder.parse(); // XML 작성한 SQL을 읽어서 SqlSession이 사용할 수 있도록 내부에 보관한다.
        }
      }

      // SqlSessionFactory 준비: 단 멀티 스레드 기반으로 SqlSession을 다룰 수 있도록 프록시 객체로 감싼다.
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder().build(configuration));
      beanMap.put("sqlSessionFactory", sqlSessionFactory);

      // bitcamp.myapp 패키지를 뒤져서 @Component와 @Controller 애노테이션이 붙은 클래스를 찾아 객체를 자동 생성한다.
      createObjects("bitcamp.myapp");


      ServletContext ctx = sce.getServletContext();

      MySQLMemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
      MySQLBoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
      MySQLBoardFileDao boardFileDao = new MySQLBoardFileDao(sqlSessionFactory);

      ctx.setAttribute("sqlSessionFactory", sqlSessionFactory);

      // 서비스 객체의 트랜잭션을 처리할 프록시 객체 생성기
      TransactionProxyFactory transactionProxyFactory = new TransactionProxyFactory(sqlSessionFactory);

      MemberService memberService = transactionProxyFactory.createProxy(
              new DefaultMemberService(memberDao),
              MemberService.class);

      BoardService boardService = transactionProxyFactory.createProxy(
              new DefaultBoardService(boardDao, boardFileDao),
              BoardService.class);

      NCPObjectStorageService storageService = new NCPObjectStorageService(appProps);

      // 페이지 컨트롤러 등록하기
      ctx.setAttribute("/home", new HomeController());

      AuthController authController = new AuthController(memberService);
      ctx.setAttribute("/auth/login-form", authController);
      ctx.setAttribute("/auth/login", authController);
      ctx.setAttribute("/auth/logout", authController);

      BoardController boardController = new BoardController(boardService, storageService);
      ctx.setAttribute("/board/list", boardController);
      ctx.setAttribute("/board/detail", boardController);
      ctx.setAttribute("/board/form", boardController);
      ctx.setAttribute("/board/add", boardController);
      ctx.setAttribute("/board/update", boardController);
      ctx.setAttribute("/board/delete", boardController);
      ctx.setAttribute("/board/file/download", boardController);
      ctx.setAttribute("/board/file/delete", boardController);

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
