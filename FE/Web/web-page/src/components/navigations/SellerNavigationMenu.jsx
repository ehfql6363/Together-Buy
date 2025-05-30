import React from 'react';
import { Menu } from 'lucide-react';
import { useNavigate, useLocation } from 'react-router-dom';

const MenuNav = () => {
  const navigate = useNavigate();
  const location = useLocation();

  // Extract the current path to set active menu item
  const activeMenuItem = (() => {
    const path = location.pathname;
    // product-fix/숫자 패턴을 확인
    if (path.match(/^\/product-fix\/\d+$/)) {
      return 1;
    }
    switch (location.pathname) {
      case '/product-management': return 1;
      case '/product-registration': return 2;
      case '/delivery-management': return 3;
      case '/sales-status': return 4;
      case '/my-page': return 5;
      default: return null;
    }
  })();

  const menuItems = [
    { 
      id: 1, 
      name: '상품 관리', 
      path: '/product-management' 
    },
    { 
      id: 2, 
      name: '상품 등록', 
      path: '/product-registration' 
    },
    { 
      id: 3, 
      name: '배송 관리', 
      path: '/delivery-management' 
    },
    { 
      id: 4, 
      name: '매출 현황', 
      path: '/sales-status' 
    },
    { 
      id: 5, 
      name: '마이 페이지', 
      path: '/my-page' 
    }
  ];

  const handleMenuClick = (path) => {
    navigate(path);
  };

  const MenuItem = ({ item }) => {
    const isActive = activeMenuItem === item.id;
    return (
      <div 
        className={`h-14 w-full cursor-pointer transition-colors duration-200 
          ${isActive ? 'bg-[#faf7f0]' : 'hover:bg-[#faf7f0]/50'} z-index`}
        onClick={() => handleMenuClick(item.path)}
      >
        <div className="h-full px-3 py-2 flex items-center">
          <div className="flex-1">
            <div className="text-[#1d1b20] text-base font-normal leading-normal tracking-wide">
              {item.name}
            </div>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="fixed left-0 top-0 p-5 mt-[100px]">
      <div 
        className="w-48 h-auto bg-[#f0e3c2] rounded-3xl shadow-md border border-[#522404] 
          overflow-hidden transition-all duration-300 ease-in-out flex flex-col"
      >
        <div className="p-5">
          <Menu 
            className="w-6 h-6 text-[#1d1b20]" 
          />
        </div>
        
        <div className="flex flex-col flex-grow">
          {menuItems.map(item => (
            <MenuItem key={item.id} item={item} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default MenuNav;