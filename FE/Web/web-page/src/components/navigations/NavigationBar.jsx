import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import axios from 'axios';
import Logo from '../common/Logo';
import LogInBtn from '../buttons/LogInBtn';
import LogOutBtn from '../buttons/LogOutBtn';
import MyPageBtn from '../buttons/MyPageBtn';
import SignUpBtn from '../buttons/SignUpBtn';

const NavigationBar = () => {
  const { isAuthenticated, logout, refreshToken } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      if (!refreshToken) {
        console.error('리프레시 토큰이 없습니다.');
        logout();
        navigate('/login');
        return;
      }
      
      // 백엔드에 리프레시 토큰을 헤더에 포함하여 로그아웃 요청 보내기
      await axios.post(
        `${window._env_.REACT_APP_API_URL}/api/member/logout`,
        {},
        {
          headers: {
            'Refresh-Token': `Bearer ${refreshToken}`
          }
        }
      );
      
      // 로컬 로그아웃 처리
      logout();
      navigate('/login');
    } catch (error) {
      console.error('로그아웃 중 오류가 발생했습니다:', error);
      // 에러가 발생하더라도 로컬 로그아웃은 진행
      logout();
      navigate('/login');
    }
  };

  return (
    <div className="fixed top-0 left-0 right-0 z-50">
      <div className="w-full min-w-[320px] h-[100px] bg-[#F4EFD8] shadow-md border border-[#FAF7F0] relative">
        {/* 로고 영역 */}
        <div 
          className="absolute left-4 sm:left-8 top-2.5 w-16 sm:w-20 h-16 sm:h-20 cursor-pointer" 
          onClick={() => navigate('/')}
        >
          <Logo className="w-full h-full" />
        </div>
        
        {/* 버튼 영역 */}
        <div className="absolute right-4 sm:right-8 top-7 flex gap-2 sm:gap-4">
          {isAuthenticated ? (
            <>
              <MyPageBtn onClick={() => navigate('/my-page')} />
              <LogOutBtn onClick={handleLogout} />
            </>
          ) : (
            <>
              <LogInBtn onClick={() => navigate('/login')} />
              <SignUpBtn onClick={() => navigate('/login')} />
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default NavigationBar;