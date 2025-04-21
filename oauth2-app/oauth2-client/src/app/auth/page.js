"use client";

import { useEffect, useState, useCallback } from "react";
import "../globals.css";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";

export default function Auth() {
  const [token, setToken] = useState();
  const [errorMessage, setErrorMessage] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [saveEmail, setSaveEmail] = useState(false);

  useEffect(() => {
    if (!token) return;
    console.log("메인 화면으로 이동!");
    window.location.href = `${process.env.NEXT_PUBLIC_AUTH_UI_URL}/`;
  }, [token]);

  useEffect(() => {
    const email = Cookies.get("email");
    if (email) {
      setEmail(email);
      setSaveEmail(true);
    }
  }, []);

  const handleSubmit = useCallback(
    async (e) => {
      console.log("로그인 요청!");
      e.preventDefault();

      if (saveEmail) {
        Cookies.set("email", email, { expires: 7 });
      } else {
        Cookies.remove("email");
      }

      try {
        const response = await fetch(`${process.env.NEXT_PUBLIC_AUTH_REST_API_URL}/auth/login`, {
          method: "POST",
          body: new URLSearchParams({
            email: email,
            password: password,
          }),
        });
        if (!response.ok) {
          throw new Error("로그인 요청 실패!");
        }
        const result = await response.json();

        if (result.status == "success") {
          console.log("로그인 성공!");
          console.log("JWT 토큰을 쿠키에 저장");
          Cookies.set("jwt_token", result.data, {
            path: "/",

            //domain: "localhost",
            //domain: "110.165.18.171",

            // Non-HTTPS:
            sameSite: "Lax",
            secure: false,

            // HTTPS:
            // sameSite: "None", // HTTPS 환경일 때
            // secure: true, // HTTPS 환경일 때
          });
          setToken(result.data);
        } else {
          setErrorMessage("사용자 인증 실패!");
        }
      } catch (error) {
        console.log(error);
      }
    },
    [email, password, saveEmail]
  );

  return (
    <>
      <h2>로그인</h2>
      {errorMessage && <p className='error'>사용자 인증 실패!</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor='email'>이메일:</label>
          <input type='email' value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div>
          <label htmlFor='password'>암호:</label>
          <input
            type='password'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <input
            type='checkbox'
            checked={saveEmail}
            onChange={(e) => setSaveEmail(e.target.checked)}
          />
          <label htmlFor='saveEmail'>이메일 저장</label>
        </div>
        <div>
          <button>로그인</button>
        </div>
      </form>
    </>
  );
}
