package bitcamp.myapp.member;

import lombok.Data;

import java.sql.Date;

@Data
public class Member {
  private int no;
  private String name;
  private String email;
  private String password;
  private String tel;
  private Date createDate;
  private String photo;
}
