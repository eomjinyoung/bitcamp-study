package bitcamp.myapp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    // 서블릿을 실행하기 전 해야 할 일
    request.setCharacterEncoding("UTF-8");

    // 다음 필터 실행
    chain.doFilter(request, response);

    // 서블릿을 실행한 후 해야 할 일

  }
}
