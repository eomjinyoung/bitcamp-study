import Image from "next/image";

export default function Home() {
  return (
    <main>
      <ul>
        <li>
          <a href='/auth'>로그인</a>
        </li>
        <li>
          <a href='/member'>회원</a>
        </li>
        <li>
          <a href='/board'>게시판</a>
        </li>
      </ul>
    </main>
  );
}
