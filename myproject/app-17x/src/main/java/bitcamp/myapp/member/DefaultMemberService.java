package bitcamp.myapp.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultMemberService implements MemberService {

  private MemberDao memberDao;

  public DefaultMemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public Member get(String email) {
    return memberDao.findByEmail(email);
  }

  @Transactional
  @Override
  public int changePassword(String email, String password) {
    return memberDao.updatePassword(email, password);
  }

  @Transactional
  @Override
  public int changeAllPassword(String password) {
    return memberDao.updateAllPassword(password);
  }
}
