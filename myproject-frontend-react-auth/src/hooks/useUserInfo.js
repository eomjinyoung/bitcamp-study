"use client";

import { useEffect, useState } from 'react';
import { useAuth } from 'components/AuthProvider';

export const useUserInfo = () => {
  console.log('useUserInfo() 호출됨');
  const { auth } = useAuth();
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log('useUserInfo()/useEffect() 호출됨');
    if (auth) {
      console.log('사용자 정보 요청');
      const fetchUserInfo = async () => {
        try {
          const response = await fetch('http://localhost:8010/auth/user-info', {
            headers: {
              Authorization: `Bearer ${auth}`,
            },
          });

          if (!response.ok) {
            throw new Error('사용자 정보 요청 실패!');
          }

          const result = await response.json();
          if (result.status !== 'success') {
            throw new Error('사용자 정보 로딩 실패!');
          }

          setUserInfo(result.data);
        
        } catch (error) {
          setError('요청 오류:' + error.message);
        }
      };

      fetchUserInfo();
    }
  }, [auth]);

  return [userInfo, error];
};