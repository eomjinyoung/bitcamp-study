"use client";

import { useUserInfo } from "hooks/useUserInfo";
import { useAuth } from "components/AuthProvider";
import "./header.css";

export default function Header() {
  const [userInfo] = useUserInfo();
  const { resetToken } = useAuth();

  function handleLogout(e) {
    e.preventDefault();
    console.log("로그아웃 처리");
    resetToken();
  }

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
      {userInfo ? (
      <div className='login'>
        <span className='user-name'>{userInfo.name}</span>
        <a href='#' onClick={handleLogout}>로그아웃</a>
      </div>
      ) : (
      <div className='login'>
        <a href='http://localhost:3010/auth'>로그인</a>
      </div>
      )}
    </header>
  );
}
