package bitcamp.myapp.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonResult {
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";

  private String status;
  private Object data;
}
