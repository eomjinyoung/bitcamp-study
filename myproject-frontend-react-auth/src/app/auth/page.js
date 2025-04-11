"use client";

import { useRef } from "react";

export default function Auth() {
  const txtEmail = useRef("");
  const txtPassword = useRef("");
  const chkSaveEmail = useRef(false);

  function submit(e) {
    e.preventDefault();
    fetch("http://localhost:8010/auth/login", {
      method: "POST",
      body: new URLSearchParams({
        email: txtEmail.current.value,
        password: txtPassword.current.value,
      }),
    })
      .then((response) => response.json())
      .then((result) => {
        console.log(result.data);
      })
      .catch((error) => {
        alert("로그인 오류!");
      });
  }

  return (
    <>
      <h2>로그인</h2>
      <p className='error invisible'>로그인 실패!</p>
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
