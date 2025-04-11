import "./header.css";

export default function Header() {
  return (
    <header className='page-header'>
      <h1>
        <a href='http://localhost:3010/'>프로젝트 관리 시스템</a>
      </h1>
      <nav>
        <ul className='nav-links'>
          <li>
            <a href='http://localhost:3010/member'>회원</a>
          </li>
          <li>
            <a href='http://localhost:3020/board'>게시글</a>
          </li>
        </ul>
      </nav>
      <div className='login logged-in invisible'>
        <span id='user-name'>홍길동</span>
        <a href='#'>로그아웃</a>
      </div>
      <div className='login logged-out'>
        <a href='http://localhost:3010/auth'>로그인</a>
      </div>
    </header>
  );
}
