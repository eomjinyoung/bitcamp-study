package bitcamp.myapp.listener;

import bitcamp.myapp.config.AppConfig;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebInitListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // ServletContext: 웹 애플리케이션 전반에 관한 정보를 다루는 객체
    ServletContext ctx = sce.getServletContext();

    // 1) DispatcherServlet이 사용할 IoC 컨테이너(Bean 컨테이너) 준비
    AnnotationConfigWebApplicationContext beanContainer = new AnnotationConfigWebApplicationContext();
    beanContainer.register(AppConfig.class);
    beanContainer.refresh();

    // 2) DispatcherServlet을 준비
    DispatcherServlet servlet = new DispatcherServlet(beanContainer);
    ServletRegistration.Dynamic registration = ctx.addServlet("app", servlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/app/*");
  }
}
