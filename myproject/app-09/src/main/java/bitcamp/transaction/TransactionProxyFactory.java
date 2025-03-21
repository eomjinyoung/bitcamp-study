package bitcamp.transaction;

import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionProxyFactory {

  private SqlSessionFactory sqlSessionFactory;

  public TransactionProxyFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public <T> T createProxy(Object target, Class<T>... interfaces) {
    return (T) Proxy.newProxyInstance (
            target.getClass().getClassLoader(), // 클래스 로더
            interfaces, // 리턴되는 프록시 객체가 구현해야 할 메서드
            new TransactionInvocationHandler(target, sqlSessionFactory) // 프록시의 메서드가 호출될 때 작업할 객체
    );
  }
}
