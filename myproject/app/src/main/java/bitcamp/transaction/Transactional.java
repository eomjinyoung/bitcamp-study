package bitcamp.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 이 애노테이션은 클래스가 로딩될 때 함께 로딩된다.
@Target({ElementType.METHOD})
public @interface Transactional {
}
