"use client";

import { useRef, useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import { useAuth } from "context/AuthProvider";

export default function Auth() {
  const { setAuth } = useAuth();
  const [loginError, setLoginError] = useState(null);
  const router = useRouter();
  const txtEmail = useRef();
  const txtPassword = useRef();
  const chkSaveEmail = useRef();
  const emailCookie = Cookies.get("email");

  async function getUserInfo(jwtToken) {
    try {
      const response = await fetch(`http://localhost:8010/auth/user-info`, {
        headers: {
          Authorization: "Bearer " + jwtToken,
        },
      });
      if (!response.ok) {
        throw new Error("응답 오류!");
      }
      const result = await response.json();

      if (result.status === "success") {
        setAuth(result.data);
      }
    } catch (err) {
      alert("Auth()/getUserInfo(): 사용자 정보 로딩 오류!");
    }
  }

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
        // HTTP 요청 오류!
        alert("응답 오류!");
      }
      const result = await response.json();

      if (result.status == "success") {
        Cookies.set("jwt_token", result.data, {
          path: "/",
          domain: "localhost",
          sameSite: "None",
          secure: true,
        });

        getUserInfo(result.data);

        if (chkSaveEmail.current.checked) {
          Cookies.set("email", txtEmail.current.value, { expires: 7 });
        } else {
          Cookies.remove("email");
        }
        router.push("/");
      } else {
        setLoginError("로그인 실패!");
      }
    } catch (error) {
      // 서버와의 통신 오류 발생!
      alert(`로그인 중 오류 발생: ${error.message}`);
    }
  }

  return (
    <>
      <h2>로그인</h2>
      {loginError && <p className='error'>로그인 실패!</p>}
      <form onSubmit={submit}>
        <div>
          <label htmlFor='email'>이메일:</label>
          <input
            ref={txtEmail}
            defaultValue={emailCookie}
            type='email'
            autoComplete='email'
            required
          />
        </div>
        <div>
          <label htmlFor='password'>암호:</label>
          <input ref={txtPassword} type='password' autoComplete='current-password' required />
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
