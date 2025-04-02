package bitcamp.myapp.common;

import bitcamp.myapp.member.Member;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 페이지 컨트롤러의 request handler를 호출할 때
// DispatcherServlet(프론트 컨트롤러)는 request handler가 요구하는 파라미터 값을 주입해 준다.
// 이때 @LoginUser 애노테이션의 붙은 파라미터는 이 클래스가 처리한다.
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final Log log = LogFactory.getLog(LoginUserArgumentResolver.class);

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    // @LoginUser 애노테이션이 붙은 파라미터인지 검사
    // Member 객체를 받을 수 있는 타입의 파라미터인지 검사
    return parameter.hasParameterAnnotation(LoginUser.class) &&
            parameter.getParameterType().isAssignableFrom(Member.class);
  }

  @Override
  public Object resolveArgument(
          MethodParameter parameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory) throws Exception {
    // supportsParameter()의 리턴 값이 true 인 경우, 즉 이 클래스가 처리할 수 있는 파라미터인 경우
    // 이 메서드에서 그 값을 준비해서 DispatcherServlet에게 리턴해 준다.
    Member member = JwtAuth.extractUserInfo();
    log.debug("===========>" + member);

    return member;
  }

}
