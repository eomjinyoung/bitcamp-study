package bitcamp.myapp.service;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

public class MemberService {

  private MemberDao memberDao;

  public MemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailAndPassword(email, password);
  }
}
