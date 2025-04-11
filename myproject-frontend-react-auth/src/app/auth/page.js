"use client";

import { useRef, useState } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import "../globals.css";
import { useAuth } from "components/AuthProvider";

export default function Auth() {
  const [errorMessage, setErrorMessage] = useState("");
  const { setAuth } = useAuth();
  const router = useRouter();
  const txtEmail = useRef();
  const txtPassword = useRef();
  const chkSaveEmail = useRef();

  async function submit(e) {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8010/auth/login", {
        method: "POST",
        body: new URLSearchParams({
          email: txtEmail.current.value,
          password: txtPassword.current.value,
        }),
      });
      if (!response.ok) {
        throw new Error("로그인 요청 실패!");
      }
      const result = await response.json();

      if (result.status == "success") {
        Cookies.set("jwt_token", result.data, {
          path: "/",
          domain: "localhost",
          sameSite: "None",
          secure: true,
        });
        setAuth(result.data); // JWT 토큰을 AuthProvider에 저장
        router.push("/");
        
      } else {
        setErrorMessage("사용자 인증 실패!");
      }
    } catch (error) {
      // 서버와의 통신 오류 발생!
      alert(`로그인 중 오류 발생: ${error.message}`);
    }
  }

  return (
    <>
      <h2>로그인</h2>
      {errorMessage && (
      <p className='error'>
        사용자 인증 실패!
      </p>
      )}
      <form onSubmit={submit}>
        <div>
          <label htmlFor='email'>이메일:</label>
          <input ref={txtEmail} type='email' required />
        </div>
        <div>
          <label htmlFor='password'>암호:</label>
          <input ref={txtPassword} type='password' required />
        </div>
        <div>
          <input ref={chkSaveEmail} type='checkbox' />
          <label htmlFor='saveEmail'>이메일 저장</label>
        </div>
        <div>
          <button>로그인</button>
        </div>
      </form>
    </>
  );
}
