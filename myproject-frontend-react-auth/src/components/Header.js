"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import "./header.css";
import { useAuth } from "context/AuthProvider";

export default function Header() {
  console.log("Header() 호출됨!");
  const router = useRouter();
  const { auth, setAuth } = useAuth();

  function handleLogout(e) {
    console.log("로그아웃 됨!");
    e.preventDefault();
    Cookies.remove("jwt_token");
    setAuth({});
    router.push("/");
  }

  return (
    <header className='page-header'>
      <h1>
        <Link href='/'>프로젝트 관리 시스템</Link>
      </h1>
      <nav>
        <ul className='nav-links'>
          <li>
            <Link href='/member'>회원</Link>
          </li>
          <li>
            <a href='http://localhost:3020/board' rel='noopener noreferrer'>
              게시글
            </a>
          </li>
        </ul>
      </nav>
      {auth.name ? (
        <div className='login'>
          <span className='user-name'>{auth.name}</span>
          <a onClick={handleLogout}>로그아웃</a>
        </div>
      ) : (
        <div className='login'>
          <Link href='/auth'>로그인</Link>
        </div>
      )}
    </header>
  );
}
