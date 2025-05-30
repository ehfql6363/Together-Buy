import React, { useState, useEffect } from 'react';
import Logo from '../../components/common/Logo';
import kakaoLoginImage from '../../assets/kakao.png';
import googleLoginImage from '../../assets/google.png';
import naverLoginImage from '../../assets/naver.png';

// const REDIRECT_URI = 'http://localhost:3000/auth/callback'; // 테스트용 로컬 주소
const REDIRECT_URI = "https://togethrebuy.com/auth/callback"; // 실제 서비스 주소

const LoginPage = () => {
  const [clientIds, setClientIds] = useState({
    google: '',
    naver: '',
    kakao: ''
  });
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadClientIds = async () => {
      try {
        setClientIds({
          google: window._env_.REACT_APP_GOOGLE_CLIENT_ID,
          naver: window._env_.REACT_APP_NAVER_CLIENT_ID,
          // kakao: window._env_.REACT_APP_KAKAO_CLIENT_ID || '' // 카카오 ID가 없는 경우 빈 문자열
        });
        setIsLoading(false);
      } catch (err) {
        setError(err.message);
        setIsLoading(false);
      }
    };

    loadClientIds();
  }, []);


  const handleGoogleLogin = () => {
    const googleAuthUrl = new URL(`${window._env_.REACT_APP_API_URL}/oauth2/authorization/google`);
    googleAuthUrl.searchParams.append('client_id', clientIds.google);
    googleAuthUrl.searchParams.append('redirect_uri', REDIRECT_URI);
    googleAuthUrl.searchParams.append('response_type', 'code');
    googleAuthUrl.searchParams.append('scope', 'openid profile email');
    googleAuthUrl.searchParams.append('state', 'google_login');
    googleAuthUrl.searchParams.append('prompt', 'select_account');

    window.location.href = googleAuthUrl.toString();
  };

  const handleNaverLogin = () => {
    const naverAuthUrl = new URL(`${window._env_.REACT_APP_API_URL}/oauth2/authorization/naver`);
    naverAuthUrl.searchParams.append('client_id', clientIds.naver);
    naverAuthUrl.searchParams.append('redirect_uri', REDIRECT_URI);
    naverAuthUrl.searchParams.append('response_type', 'code');
    naverAuthUrl.searchParams.append('state', 'naver_login');

    window.location.href = naverAuthUrl.toString();
  };

  // const handleKakaoLogin = () => {
  //   const kakaoAuthUrl = new URL('https://kauth.kakao.com/oauth/authorize');
  //   kakaoAuthUrl.searchParams.append('client_id', clientIds.kakao);
  //   kakaoAuthUrl.searchParams.append('redirect_uri', REDIRECT_URI);
  //   kakaoAuthUrl.searchParams.append('response_type', 'code');
  //   kakaoAuthUrl.searchParams.append('state', 'kakao_login');

  //   window.location.href = kakaoAuthUrl.toString();
  // };

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>에러 발생: {error}</div>;

  return (
    <div className="relative w-full min-h-screen bg-[#FAF7F0]">

      {/* Main Content */}
      <main className="w-full min-h-screen pb-32">
        <div className="flex flex-col items-center pt-2">
          {/* Logo Section */}
          <div className="mt-32 mb-16">
            <div className="w-60 h-60">
              <Logo className="w-full h-full" />
            </div>
          </div>

          {/* Login Buttons Section */}
          <div className="w-[600px] space-y-8">
            {/* Google Login Button */}
            <button 
              onClick={handleGoogleLogin}
              className="w-full h-[90px] bg-white rounded-lg border-2 border-gray-300 
              flex items-center px-8 
              transition-all duration-300 
              shadow-md
              hover:-translate-y-2 hover:shadow-lg
              active:scale-[0.98]"
            >
              <img 
                src={googleLoginImage} 
                alt="Google" 
                className="w-16 h-16"
              />
              <span className="flex-1 text-center text-[#1E1E1E] text-3xl font-medium font-['Roboto']">
                구글 계정으로 로그인
              </span>
            </button>

            {/* Naver Login Button */}
            <button 
              onClick={handleNaverLogin}
              className="w-full h-[89.4px] bg-[#03C75A] rounded-lg border-2 border-[#03a357] 
              flex items-center px-8 
              transition-all duration-300 
              shadow-md
              hover:-translate-y-2 hover:shadow-lg
              active:scale-[0.98]"
            >
              <img 
                src={naverLoginImage}
                alt="Naver" 
                className="w-16 h-16"
              />
              <span className="flex-1 text-center text-white text-3xl font-medium font-['Roboto']">
                네이버 로그인
              </span>
            </button>

            {/* Kakao Login Button
            <button 
              onClick={handleKakaoLogin}
              className="w-full h-[89.4px] bg-[#FEE500] rounded-lg border-2 border-[#F2D300] 
              flex items-center px-8 
              transition-all duration-300 
              shadow-md
              hover:-translate-y-2 hover:shadow-lg
              active:scale-[0.98]"
            >
              <img 
                src={kakaoLoginImage}
                alt="카카오 로그인" 
                className="w-full h-full object-cover rounded-lg"
              />
            </button> */}
          </div>
        </div>
      </main>
    </div>
  );
};

export default LoginPage;