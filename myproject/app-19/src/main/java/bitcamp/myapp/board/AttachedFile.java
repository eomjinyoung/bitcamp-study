package bitcamp.myapp.board;

import lombok.Data;

@Data
public class AttachedFile {
  private int no;
  private String filename;
  private String originFilename;
  private int boardNo;
}
