package bitcamp.myapp.board;

import bitcamp.myapp.member.Member;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.util.List;

@Alias("board") // alias는 SQL 매퍼 파일에서 대소문자 구분없이 사용 가능
public class Board {
  private int no;
  private String title;
  private String content;
  private int viewCount;
  private Member writer;
  private Date createDate;
  private List<AttachedFile> attachedFiles;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public Member getWriter() {
    return writer;
  }

  public void setWriter(Member writer) {
    this.writer = writer;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public List<AttachedFile> getAttachedFiles() {
    return attachedFiles;
  }

  public void setAttachedFiles(List<AttachedFile> attachedFiles) {
    this.attachedFiles = attachedFiles;
  }
}
