package bitcamp.myapp;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication // Gradle에서 bootRun 태스크를 수행할 때 실행시킬 메인 클래스를 지정하는 애노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")

// DAO 구현체 자동 생성을 설정하는 방법 1: 애노테이션으로 설정하기
@MapperScan("bitcamp.myapp.dao")
public class App {

  @Bean // Spring IoC 컨테이너는 이 메서드를 호출한 후 리턴된 객체를 메서드 이름으로 보관한다.
  public DataSource dataSource(
          @Value("${jdbc.driver}") String driverClassName,
          @Value("${jdbc.url}") String url,
          @Value("${jdbc.username}") String username,
          @Value("${jdbc.password}") String password
  ) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext iocContainer) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setTypeAliasesPackage("bitcamp.myapp.vo");

//    Resource[] resources = iocContainer.getResources("classpath*:bitcamp/myapp/mapper/*Dao.xml");
//    System.out.println("Found mapper files: " + Arrays.toString(resources));
//    factoryBean.setMapperLocations(resources);

    factoryBean.setMapperLocations(iocContainer.getResources("classpath:bitcamp/myapp/mapper/*Dao.xml"));

    return factoryBean.getObject();
  }

/*
  // DAO 구현체 자동 생성을 설정하는 방법 2: 자바 코드로 설정하기
  // - 다음 메서드를 통해 DAO 인터페이스의 구현체를 자동으로 생성해주는 일을 하는 객체를 준비한다.
  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer configurer = new MapperScannerConfigurer();
    configurer.setBasePackage("bitcamp.myapp.dao"); // DAO 인터페이스 패키지 설정.
    // 단, SQL 매퍼 파일(*Dao.xml)의 namespace가 인터페이스의 full-qualified name과 일치해야 한다.
    // 그리고 SQL id와 인터페이스의 메서드 이름과 일치해야 한다.
    // 메서드의 파라미터 값이 두 개 이상일 경우,
    // @Params 애노테이션으로 프로퍼티 이름을 명시해야 한다.
    return configurer;
  }
*/

  public static void main(String[] args) {
    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);
  }
}
