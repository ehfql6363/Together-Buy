import React from 'react';
import NavigationBar from '../navigations/NavigationBar';
import SellerNavigationMenu from '../navigations/SellerNavigationMenu';

// default export로 내보내기
export default function SellerLayout({ children }) {
  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <NavigationBar isLoggedIn={true} />
      
      <div className="flex pt-24">
        <div className="w-52 flex-shrink-0">
          <SellerNavigationMenu />
        </div>
        
        <div className="flex-1 p-8 mt-[60px] ml-10 max-w-5xl mx-auto w-full">
          {children}
        </div>
      </div>
    </div>
  );
}