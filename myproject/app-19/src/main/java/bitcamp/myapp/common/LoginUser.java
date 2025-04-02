package bitcamp.myapp.common;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser { // request handler에서 로그인 정보를 파라미터로 받고 싶을 때 붙인다.
}
