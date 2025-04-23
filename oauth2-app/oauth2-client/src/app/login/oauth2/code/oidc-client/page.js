"use client";
import { useEffect } from "react";

export default function OidcClient() {

  useEffect(() => {
    async function getAccessToken() {
      const response = await fetch("http://localhost:9000/oauth2/token", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Authorization": "Basic " + btoa("oidc-client:secret"),
        },
        body: new URLSearchParams({
          grant_type: "authorization_code",
          code: new URLSearchParams(window.location.search).get("code"),
          scope: "openid profile",
          redirect_uri: "http://localhost:3000/authorized",
        }),
      });
      if (response.ok) {
        const data = await response.json();
        console.log("Access Token Response:", data);
        console.log("Access Token:", data.access_token);
        return data.access_token;
      } else {
        console.error("Failed to fetch access token");
      }
    }
    getAccessToken();
  }, []);


  return <h1>사용자 인증 확인!</h1>;
}
