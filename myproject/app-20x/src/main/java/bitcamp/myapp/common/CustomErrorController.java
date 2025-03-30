package bitcamp.myapp.common;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
  @RequestMapping("/error")
  public JsonResult handleError(HttpServletRequest request) {
    Map<String, Object> response = new HashMap<>();
    Object status = request.getAttribute("javax.servlet.error.status_code");

    response.put("status", status);
    response.put("message", "잘못된 요청입니다.");
    return JsonResult.builder().status(JsonResult.FAILURE).data(response).build();
  }
}
