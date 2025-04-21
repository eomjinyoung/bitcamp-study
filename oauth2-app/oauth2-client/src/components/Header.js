"use client";

import { useCallback, useEffect } from "react";
import "./header.css";
import Cookies from "js-cookie";

export default function Header({ user }) {
  useEffect(() => {
    if (user) {
      const no = parseInt(localStorage.getItem("no"));
      if (user.no !== no) {
        console.log("사용자 정보를 localStorage 에 보관 했음!");
        localStorage.setItem("no", user.no);
        localStorage.setItem("name", user.name);
        localStorage.setItem("email", user.email);
      }
    }
  });

  const handleLogin = useCallback((e) => {
    e.preventDefault();
    const authorizeUrl = "http://localhost:9000/oauth2/authorize";
    const clientId = "oidc-client";
    const redirectUri = "http://localhost:3000/authorized";
    const responseType = "code";
    const scope = "openid profile";
    const state = "random123"; // CSRF 확인용 임의 값

    // URLSearchParams로 쿼리 파라미터 생성
    const params = new URLSearchParams({
      response_type: responseType,
      client_id: clientId,
      scope: scope,
      redirect_uri: redirectUri,
      stat: state,
    });

    // Authorization 서버로 리디렉션
    window.location.href = `${authorizeUrl}?${params.toString()}`;
  }, []);

  function handleLogout(e) {
    e.preventDefault();
    console.log("로그아웃 처리");

    localStorage.removeItem("no");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
    Cookies.remove("jwt_token");

    window.location.href = `http://localhost:3000/`;
  }

  return (
    <header className='page-header'>
      <h1>
        <a href={`http://localhost:3000/`}>프로젝트 관리 시스템</a>
      </h1>
      <nav>
        <ul className='nav-links'>
          <li>
            <a href={`http://localhost:3000/members`}>회원</a>
          </li>
          <li>
            <a href={`http://localhost:3000/boards`}>게시글</a>
          </li>
        </ul>
      </nav>
      {user ? (
        <div className='login'>
          <span className='user-name'>{user.name}</span>
          <a href='#' onClick={handleLogout}>
            로그아웃
          </a>
        </div>
      ) : (
        <div className='login'>
          <a href='#' onClick={handleLogin}>
            로그인
          </a>
        </div>
      )}
    </header>
  );
}
