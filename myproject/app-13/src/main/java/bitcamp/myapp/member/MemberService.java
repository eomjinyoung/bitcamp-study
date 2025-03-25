package bitcamp.myapp.member;

public interface MemberService {
  Member get(String email, String password);
}
