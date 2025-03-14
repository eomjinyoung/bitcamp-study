package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import java.util.List;

public interface BoardService {
  List<Board> list();
  void add(Board board);
  Board get(int no);
  void update(Board board);
  void delete(int no);

  AttachedFile getAttachedFile(int fileNo);
}
