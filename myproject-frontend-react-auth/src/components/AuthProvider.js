"use client";

import { createContext, useState, useContext, useCallback } from 'react';
import Cookies from 'js-cookie';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState();

  const setToken = useCallback((token) => {
    setAuth(token);
    Cookies.set("jwt_token", token, {
      path: "/",
      domain: "localhost",
      sameSite: "None",
      secure: true,
    });
  }, []);

  const resetToken = useCallback(() => {
    setAuth(null);
    Cookies.remove("jwt_token");
  }, []);

  return (
    <AuthContext.Provider value={{ auth, setToken, resetToken }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};