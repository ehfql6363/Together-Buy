import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

const api = axios.create({
  baseURL: window._env_?.REACT_APP_API_URL || "https://togethrebuy.com",
  withCredentials: true // CORS 요청에서 쿠키를 주고받기 위해 필요
});

const decodeToken = (token) => {
  if (!token || token.split('.').length !== 3) {
    return null;
  }

  try {
    const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
    const padding = '='.repeat((4 - base64.length % 4) % 4);
    const decodedToken = atob(base64 + padding);
    return JSON.parse(decodedToken);
  } catch (error) {
    console.error('토큰 디코딩 실패:', error);
    return null;
  }
};

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [refreshToken, setRefreshToken] = useState(localStorage.getItem('refreshToken'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // 응답 인터셉터 설정
  useEffect(() => {
    const responseInterceptor = api.interceptors.response.use(
      (response) => {
        // 응답 헤더에서 토큰 확인
        const newToken = response.headers['Authorization'];
        const newRefreshToken = response.headers['Refresh-Token'];

        // 쿠키에서 토큰 확인
        const cookies = document.cookie.split(';').reduce((acc, cookie) => {
          const [key, value] = cookie.trim().split('=');
          acc[key] = value;
          return acc;
        }, {});

        const cookieToken = cookies['Authorization'];
        const cookieRefreshToken = cookies['Refresh-Token'];

        // 새로운 토큰이 있다면 저장
        if (newToken || cookieToken || newRefreshToken || cookieRefreshToken) {
          const finalToken = newToken || cookieToken;
          const finalRefreshToken = newRefreshToken || cookieRefreshToken;
          storeToken(finalToken, finalRefreshToken);
        }

        return response;
      },
      (error) => {
        // 401 에러 발생 시 로그아웃
        if (error.response?.status === 401) {
          throw new Error('인증에 실패했습니다. 다시 로그인해주세요.');
        }
        return Promise.reject(error);
      }
    );

    // 클린업 함수
    return () => {
      api.interceptors.response.eject(responseInterceptor);
    };
  }, []);

  const storeToken = (newToken, newRefreshToken) => {
    if (newToken) {
      localStorage.setItem('token', newToken);
      setToken(newToken);
    }
    if (newRefreshToken) {
      localStorage.setItem('refreshToken', newRefreshToken);
      setRefreshToken(newRefreshToken);
    }
  };

  const storeUserAndToken = (newToken, newRefreshToken, userData) => {
    storeToken(newToken, newRefreshToken);
    setUser({
      ...userData,
      isSeller: decodeToken(newToken)?.roles?.includes('isSeller') || false
    });
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    setToken(null);
    setRefreshToken(null);
    setUser(null);
  };

  const makeRequest = async (method, url, data = null) => {
    try {
      const response = await api({
        method,
        url,
        data,
        headers: token ? { 
          Authorization: `Bearer ${token}`,
        } : {}
      });
      return response;
    } catch (error) {
      throw error; // 인터셉터에서 에러 처리
    }
  };

  return (
    <AuthContext.Provider value={{ 
      token,
      refreshToken,
      user, 
      loading,
      decodeToken,
      storeToken,
      storeUserAndToken,
      logout,
      makeRequest,
      isAuthenticated: !!token 
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);