package bitcamp.myapp.listener;

import bitcamp.myapp.controller.BoardController;
import bitcamp.myapp.controller.HomeController;
import bitcamp.myapp.controller.AuthController;
import bitcamp.myapp.dao.MySQLBoardDao;
import bitcamp.myapp.dao.MySQLBoardFileDao;
import bitcamp.myapp.dao.MySQLMemberDao;
import bitcamp.myapp.service.*;
import bitcamp.stereotype.Component;
import bitcamp.stereotype.Controller;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  private ServletContext servletContext;
  private Connection con;
  private ArrayList<Class> classList = new ArrayList<>();
  private ArrayList<Object> objectList = new ArrayList<>();

  private HashMap<String, Object> beanMap = new HashMap<>();

  private void createObjects(String packageName) throws Exception {
    String packagePath = packageName.replaceAll("\\.", "/");
    File path = Resources.getResourceAsFile(packagePath);

    // @Component 또는 @Controller 애노테이션이 붙은 클래스를 찾는다.
    findClasses(path, packageName);

    // 찾은 클래스의 인스턴스를 생성한다.
    prepareObjects();
  }

  private void prepareObjects() throws Exception {
    int count = 1;
    while (classList.size() > 0) {
      int size = classList.size();
      ArrayList<Class> removeList = new ArrayList<>();
      for (Class clazz : classList) {
        Constructor constructor = clazz.getConstructors()[0];
        Parameter[] parameters = constructor.getParameters();
        try {
          Object[] arguments = findArguments(parameters);
          Object obj = constructor.newInstance(arguments);
          objectList.add(obj);
          removeList.add(clazz);
          System.out.println(clazz.getName() + "객체 생성!");

        } catch (Exception e) {
          System.out.println(clazz.getName() + "클래스는 다음에 다시 시도!");
        }
      }
      classList.removeAll(removeList);
      System.out.println(count++ + "번 반복!");

      if (size == classList.size()) {
        System.out.println("더이상 객체를 생성할 수 없습니다.");
        break;
      }
    }
  }

  private Object[] findArguments(Parameter[] parameters) throws Exception {
    Object[] arguments = new Object[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      Object obj = findObject(parameters[i].getType());
      if (obj == null) {
        throw new Exception("파라미터에 넣을 객체가 없습니다.");
      }
      arguments[i] = obj;
    }
    return arguments;
  }

  private Object findObject(Class<?> type) throws Exception {
    for (Object obj : objectList) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }

  private void findClasses(File path, String packageName) throws Exception {
    File[] files = path.listFiles(file ->
            file.isDirectory() ||
                    (file.getName().endsWith(".class") && !file.getName().contains("$"))); // 현재 경로에 있는 모든 디렉토리와 파일을 알아낸다.
    for (File file : files) {
      if (file.isDirectory()) {
        findClasses(file, packageName + "." + file.getName());
      } else {
        String className = packageName + "." + file.getName().replaceAll(".class", "");
        Class clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Controller.class)) {
          classList.add(clazz);
        }
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
      objectList.add(appProps);

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
      objectList.add(sqlSessionFactory);

      // bitcamp.myapp 패키지를 뒤져서 @Component와 @Controller 애노테이션이 붙은 클래스를 찾아 객체를 자동 생성한다.
      createObjects("bitcamp.myapp");

      ServletContext ctx = sce.getServletContext();

      ctx.setAttribute("sqlSessionFactory", sqlSessionFactory);

      // 서비스 객체의 트랜잭션을 처리할 프록시 객체 생성기
      TransactionProxyFactory transactionProxyFactory = new TransactionProxyFactory(sqlSessionFactory);

//      MemberService memberService = transactionProxyFactory.createProxy(
//              new DefaultMemberService(memberDao),
//              MemberService.class);
//
//      BoardService boardService = transactionProxyFactory.createProxy(
//              new DefaultBoardService(boardDao, boardFileDao),
//              BoardService.class);

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
