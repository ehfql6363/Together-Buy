import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ShoppingCart, AlarmClock, BadgePercent, Facebook, Instagram, Youtube } from 'lucide-react';
import characterImg from '../../assets/character.png';
import { useAuth } from '../../contexts/AuthContext';

const IndexPage = () => {
  const navigate = useNavigate();
  const [currentSection, setCurrentSection] = useState(0);
  const [isScrolling, setIsScrolling] = useState(false);
  const { isAuthenticated, decodeToken } = useAuth();
  const [isSeller, setIsSeller] = useState(false);
  
  const sections = ['main', 'features', 'footer'];

  useEffect(() => {
    const checkSellerStatus = () => {
      const token = localStorage.getItem('token');
      if (token) {
        const decoded = decodeToken(token);
        setIsSeller(decoded.isSeller);
      }
    };

    checkSellerStatus();
  }, [decodeToken, isSeller]);

  useEffect(() => {
    const handleScroll = (event) => {
      event.preventDefault();
      if (isScrolling) return;
      
      setIsScrolling(true);
      const direction = event.deltaY > 0 ? 1 : -1;
      
      setCurrentSection((prev) => {
        const next = prev + direction;
        if (next >= 0 && next < sections.length) {
          document.getElementById(sections[next]).scrollIntoView({ behavior: 'smooth' });
          return next;
        }
        return prev;
      });

      setTimeout(() => setIsScrolling(false), 1000);
    };

    window.addEventListener('wheel', handleScroll, { passive: false });
    return () => window.removeEventListener('wheel', handleScroll);
  }, [isScrolling]);

  const renderButtons = () => {
    if (!isAuthenticated) {
      // 비로그인 상태: '시작하기' 버튼
      return (
        <button 
          className="bg-[#4E3A02] text-white px-8 py-4 rounded-xl font-bold 
            shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]
            hover:bg-[#3A2C01] hover:-translate-y-1
            transition-all duration-300 ease-in-out"
          onClick={() => navigate('/login')}
        >
          시작하기
        </button>
      );
    } else if (!isSeller) {
      // 로그인 상태이지만 판매자가 아닌 경우: '마이페이지'와 '판매자 등록하기' 버튼
      return (
        <div className="flex flex-col space-y-4">
          <button 
            className="bg-[#4E3A02] text-white px-8 py-4 rounded-xl font-bold 
              shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]
              hover:bg-[#3A2C01] hover:-translate-y-1
              transition-all duration-300 ease-in-out"
            onClick={() => navigate('/my-page')}
          >
            마이페이지
          </button>
          <button 
            className="bg-white text-[#4E3A02] px-8 py-4 rounded-xl font-bold 
              border-2 border-[#4E3A02]
              shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]
              hover:bg-[#FAF7F0] hover:-translate-y-1
              transition-all duration-300 ease-in-out"
            onClick={() => navigate('/seller-registration')}
          >
            판매자로 등록하기
          </button>
        </div>
      );
    } else {
      // 로그인 상태이며 판매자인 경우: '상품 관리' 버튼
      return (
        <button 
          className="bg-[#4E3A02] text-white px-8 py-4 rounded-xl font-bold 
            shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]
            hover:bg-[#3A2C01] hover:-translate-y-1
            transition-all duration-300 ease-in-out"
          onClick={() => navigate('/product-management')}
        >
          상품 관리
        </button>
      );
    }
  };

  return (
    <div className="h-screen w-screen overflow-y-auto overflow-x-hidden">
      {/* Main Section */}
      <section id="main" className="w-full h-screen bg-[#FAF7F0] snap-start">
        <div className="max-w-7xl mx-auto px-4 md:px-8 h-full flex items-center">
          <div className="flex flex-col md:flex-row items-center justify-between w-full">
            <div className="md:w-1/2 mb-12 md:mb-0">
              <h2 className="text-5xl md:text-7xl font-bold text-[#4E3A02] mb-6">
                <span className="block mb-6">대한민국</span>
                <span>1등 공동구매</span>
              </h2>
              <p className="text-xl text-[#4E3A02]/80 mb-8">
                오늘도 당신에게 좋은 가격을 배달중입니다.
              </p>
              <div className="flex flex-col space-y-4">
                {renderButtons()}
              </div>
            </div>
            <div className="md:w-1/2 flex justify-center">
              <img 
                src={characterImg}
                alt="같이 사요 캐릭터"
                className="w-full max-w-[500px]"
              />
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="w-full h-screen bg-white snap-start">
        <div className="max-w-7xl mx-auto px-4 md:px-8 h-full flex items-center">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 w-full">
            <div className="bg-[#FAF7F0]/10 rounded-2xl p-8 text-center">
              <div className="w-20 h-20 bg-[#F0E3C2] rounded-full mx-auto mb-6 flex items-center justify-center">
                <ShoppingCart className="text-[#4E3A02]" size={32} />
              </div>
              <h3 className="text-2xl font-bold text-[#4E3A02] mb-4">함께 구매하기</h3>
              <p className="text-gray-600">이웃들과 함께 구매하여 더 저렴한 가격에 구매하세요</p>
            </div>
            <div className="bg-[#F0E3C2]/10 rounded-2xl p-8 text-center">
              <div className="w-20 h-20 bg-[#F0E3C2] rounded-full mx-auto mb-6 flex items-center justify-center">
                <AlarmClock className="text-[#4E3A02]" size={32} />
              </div>
              <h3 className="text-2xl font-bold text-[#4E3A02] mb-4">타임특가</h3>
              <p className="text-gray-600">매일 업데이트되는 특가 상품을 놓치지 마세요</p>
            </div>
            <div className="bg-[#F0E3C2]/10 rounded-2xl p-8 text-center">
              <div className="w-20 h-20 bg-[#F0E3C2] rounded-full mx-auto mb-6 flex items-center justify-center">
                <BadgePercent className="text-[#4E3A02]" size={32} />
              </div>
              <h3 className="text-2xl font-bold text-[#4E3A02] mb-4">최대 70% 할인</h3>
              <p className="text-gray-600">공동구매로 최대 70% 할인된 가격에 구매하세요</p>
            </div>
          </div>
        </div>
      </section>

      {/* Footer Section */}
      <section id="footer" className="w-full h-screen bg-[#4E3A02] text-white snap-start">
        <div className="max-w-7xl mx-auto px-4 md:px-8 h-full flex flex-col justify-center">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-12 mb-12">
            <div>
              <h3 className="font-bold text-xl mb-4">SSAFY 형제들</h3>
              <ul className="space-y-2">
                <li><a href="#" className="text-white/80 hover:text-white">공동구매 입점관리</a></li>
                <li><a href="#" className="text-white/80 hover:text-white">공동구매 네트워크</a></li>
              </ul>
              <div className="flex gap-4 mt-6">
                <Facebook className="text-white/80 hover:text-white cursor-pointer" size={20} />
                <Instagram className="text-white/80 hover:text-white cursor-pointer" size={20} />
                <Youtube className="text-white/80 hover:text-white cursor-pointer" size={20} />
              </div>
            </div>
            <div>
              <h3 className="font-bold text-xl mb-4">오늘도 당신에게</h3>
              <p className="text-white/80 mb-2">좋은 가격을 배달중</p>
              <p className="text-lg font-bold">같이 사요</p>
            </div>
          </div>
          <div className="pt-8 border-t border-white/20">
            <p className="text-sm text-white/60">(주)SSAFY 형제들</p>
            <p className="text-sm text-white/60 mt-1">서울시 강남구 연규짱대로 1 동민빌딩 14층</p>
            <p className="text-sm text-white/60 mt-1">
              사업자번호: 000-80-00000 | 통신판매업: 서울 강남-0000
            </p>
            <div className="mt-4">
              <p className="text-sm text-white/60">
                전자금융분쟁처리 TEL: 1600-0000 | FAX: 02-000-1234
              </p>
              <p className="text-sm text-white/60">
                MAIL: mins@ssafy.com
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default IndexPage;