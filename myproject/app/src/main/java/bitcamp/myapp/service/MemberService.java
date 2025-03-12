package bitcamp.myapp.service;

import bitcamp.myapp.vo.Member;

public class MemberService {
  public Member get(String email, String password) {
    Member user = new Member();
    user.setNo(1);
    user.setName("user1");
    user.setEmail("user1@test.com");
    return user;
  }
}
