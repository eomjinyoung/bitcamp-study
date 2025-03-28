package bitcamp.myapp.board;

import bitcamp.myapp.member.Member;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Board {
  private int no;
  private String title;
  private String content;
  private int viewCount;
  private Member writer;
  private Date createDate;
  private List<AttachedFile> attachedFiles;
}
