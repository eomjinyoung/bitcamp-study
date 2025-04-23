"use client";
import { useEffect } from "react";

export default function Authorized() {

  useEffect(() => {
    window.location.href = "http://localhost:3000/";
  }, []);


  return <h1>인증 완료!</h1>;
}
