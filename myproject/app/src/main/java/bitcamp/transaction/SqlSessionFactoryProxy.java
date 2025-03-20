package bitcamp.transaction;

import org.apache.ibatis.session.*;

import java.sql.Connection;

public class SqlSessionFactoryProxy implements SqlSessionFactory {

  private SqlSessionFactory originalFactory;

  // 이 객체에 담는 값은 스레드에 보관된다.
  private ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<>();

  public SqlSessionFactoryProxy(SqlSessionFactory originalFactory) {
    this.originalFactory = originalFactory;
  }

  public void clearSession() {
    sqlSessionThreadLocal.remove(); // 스레드에 보관된 값을 제거한다.
  }

  @Override
  public SqlSession openSession() {
    SqlSession sqlSession = sqlSessionThreadLocal.get();
    if (sqlSession == null) {
      sqlSession = new SqlSessionProxy(originalFactory.openSession());
      sqlSessionThreadLocal.set(sqlSession); // 새로 만든 SqlSession 객체를 스레드에 보관해둔다.
    }
    return sqlSession;
  }

  @Override
  public SqlSession openSession(boolean autoCommit) {
    return originalFactory.openSession(autoCommit);
  }

  @Override
  public SqlSession openSession(Connection connection) {
    return originalFactory.openSession(connection);
  }

  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return originalFactory.openSession(level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType) {
    return originalFactory.openSession(execType);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return originalFactory.openSession(execType, autoCommit);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return originalFactory.openSession(execType, level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return originalFactory.openSession(execType, connection);
  }

  @Override
  public Configuration getConfiguration() {
    return originalFactory.getConfiguration();
  }
}
