package bitcamp.myapp.servlet;

import java.lang.reflect.Method;

// 클라이언트 요청을 처리할 메서드 정보
public class RequestHandler {
  public Object pageController;
  public Method handler;

  public RequestHandler(Object pageController, Method handler) {
    this.pageController = pageController;
    this.handler = handler;
  }
}
