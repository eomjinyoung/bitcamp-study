package bitcamp.myapp.service;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

public interface MemberService {
  Member get(String email, String password);
}
