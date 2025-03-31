package bitcamp.myapp.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public JsonResult error(HttpServletRequest request) {
    Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

    String message = exception.getMessage() != null ? exception.getMessage() : "실행 오류 입니다";

    return JsonResult.builder()
            .status(JsonResult.FAILURE)
            .data(message)
            .build();
  }
}
