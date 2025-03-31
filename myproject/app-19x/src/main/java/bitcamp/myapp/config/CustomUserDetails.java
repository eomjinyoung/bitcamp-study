package bitcamp.myapp.config;

import bitcamp.myapp.member.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class CustomUserDetails implements UserDetails {

  private final Member member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 권한 정보 저장:
    // - DB에서 가져온 로그인 사용자 정보(Member 객체)에 권한 정보가 있다면
    //   그 권한을 목록에 담아서 리턴한다.
    // - 나중에 Spring Security에서 권한에 따라 페이지 접근을 제어할 때 사용된다.
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(() -> "ROLE_USER");

    return authorities;
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
