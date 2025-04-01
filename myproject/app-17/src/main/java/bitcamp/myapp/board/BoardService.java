package bitcamp.myapp.board;

import java.util.List;

public interface BoardService {
  List<Board> list();
  void add(Board board);
  Board get(int no);
  void update(Board board);
  void delete(int no);
  void increaseViewCount(int no);

  AttachedFile getAttachedFile(int fileNo);
  void deleteAttachedFile(int fileNo);
}
