export default function Auth() {
  return (
    <main id='page-content'>
      <h2>로그인</h2>
      <p id='error-message' class='error invisible'>
        로그인 실패!
      </p>
      <form id='login-form' method='post'>
        <div>
          <label for='email'>이메일:</label>
          <input type='email' id='email' name='email' required />
        </div>
        <div>
          <label for='password'>암호:</label>
          <input type='password' id='password' name='password' required />
        </div>
        <div>
          <input type='checkbox' id='saveEmail' name='saveEmail' />
          <label for='saveEmail'>이메일 저장</label>
        </div>
        <div>
          <input type='submit' value='로그인' />
        </div>
      </form>
    </main>
  );
}
