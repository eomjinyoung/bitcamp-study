import { cookies } from "next/headers";

export async function getUserFromServer() {
  const cookieStore = await cookies(); // ✅ 여기서 호출
  const token = cookieStore.get("jwt_token")?.value;

  if (!token) {
    console.log("토큰이 없습니다.");
    return;
  }

  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_AUTH_REST_API_URL}/auth/user-info`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      // 중요: 쿠키 포함해서 SSR fetch
      //credentials: "include", // 인증 관련 쿠키를 서버 fetch에서도 사용
      cache: "no-store", //매 요청마다 새로 사용자 정보를 받기
    });

    if (!response.ok) return null;

    const result = await response.json();
    return result.data;
  } catch (error) {
    console.error("사용자 정보 요청 오류:", error);
    return null;
  }
}
