import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import React from 'react';
import { Loader2 } from 'lucide-react';
import { useCookies } from 'react-cookie';

const OAuthCallback = () => {
  const { storeToken } = useAuth();
  const navigate = useNavigate();
  const [cookies, , removeCookie] = useCookies(['Authorization', 'Refresh-Token']);

  useEffect(() => {
    const handleAuthRedirect = async () => {
      try {
        const accessToken = cookies.Authorization;
        const refreshToken = cookies['Refresh-Token'];
        
        if (!accessToken || !refreshToken) {
          console.log('쿠키에 토큰이 없음');
          navigate('/login');
          return;
        }
        
        // Bearer 제거 및 공백 제거
        const cleanAccessToken = accessToken.replace('Bearer ', '').trim();
        const cleanRefreshToken = refreshToken.replace('Bearer ', '').trim();
        
        await storeToken(cleanAccessToken, cleanRefreshToken);
        
        // 토큰 디코딩 전에 유효성 검사
        if (!cleanAccessToken || cleanAccessToken.split('.').length !== 3) {
          console.error('유효하지 않은 토큰 형식');
          navigate('/login');
          return;
        }

        try {
          // base64url 디코딩을 위한 패딩 처리
          const base64 = cleanAccessToken.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
          const padding = '='.repeat((4 - base64.length % 4) % 4);
          const decodedToken = atob(base64 + padding);
          const tokenPayload = JSON.parse(decodedToken);

          if (!tokenPayload.profileImage) {
            console.log('닉네임 정보 없음, 회원가입 페이지로 이동');
            navigate('/signup');
          } else {
            console.log('인증 완료, 메인 페이지로 이동');
            navigate('/');
          }
        } catch (decodeError) {
          console.error('토큰 디코딩 실패:', decodeError);
          navigate('/login');
          return;
        }
  
      } catch (error) {
        console.error('인증 처리 중 오류 발생:', error);
        navigate('/login');
      }
    };
  
    handleAuthRedirect();
  }, [cookies.Authorization, cookies.RefreshToken, storeToken, navigate, removeCookie]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#FAF7F0]">
      <div className="bg-white p-8 rounded-2xl shadow-lg flex flex-col items-center">
        <Loader2 className="w-12 h-12 text-blue-500 animate-spin mb-4" />
        <h2 className="text-2xl font-semibold text-gray-700 mb-2">로그인 처리 중</h2>
        <p className="text-gray-500">잠시만 기다려주세요...</p>
      </div>
    </div>
  );
};

export default OAuthCallback;