"use client";

import { useEffect, useState } from "react";

export const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState();

  useEffect(() => {
    const no = localStorage.getItem("no");
    if (!no) return;

    console.log("localStorage에서 사용자 정보 로딩");
    setUserInfo({
      no: localStorage.getItem("no"),
      name: localStorage.getItem("name"),
      email: localStorage.getItem("email"),
    });
  }, []);

  return [userInfo, setUserInfo];
};
