package bitcamp.myapp.listener;

import bitcamp.transaction.SqlSessionFactoryProxy;
import bitcamp.transaction.SqlSessionProxy;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ResourceServletRequestListener implements ServletRequestListener {
  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    // 클라이언트에게 응답할 때마다 호출된다.
    // 즉 응답 완료 후에 수행할 작업이 있다면 이 메서드에 둔다.
    SqlSessionFactoryProxy sqlSessionFactoryProxy =
            (SqlSessionFactoryProxy) sre.getServletContext().getAttribute("sqlSessionFactory");
    SqlSessionProxy sqlSessionProxy =
            (SqlSessionProxy) sqlSessionFactoryProxy.openSession();
    sqlSessionProxy.realClose(); // SqlSession을 다 사용했으면 자원 해제 시킨다.
    sqlSessionFactoryProxy.clearSession(); // 응답할 때 스레드에서 SqlSession을 제거한다.
    System.out.println("응답할 때 자원해제 했음!");
  }
}
