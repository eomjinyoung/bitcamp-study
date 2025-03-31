package bitcamp.myapp.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public JsonResult error(HttpServletRequest request) {

    HashMap<String, Object> errorData = new HashMap<>();
    errorData.put("status_code", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
    errorData.put("url", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

    Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
    errorData.put("message", exception.getMessage());
    errorData.put("type", exception.getCause().getClass().getName());

    return JsonResult.builder()
            .status(JsonResult.FAILURE)
            .data(errorData)
            .build();
  }
}
