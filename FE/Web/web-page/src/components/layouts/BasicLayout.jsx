import React from 'react';
import NavigationBar from '../navigations/NavigationBar';

export default function BasicLayout({ children, fullWidth = false }) {
  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <NavigationBar isLoggedIn={false} />
      
      <div className={`flex-1 ${fullWidth ? '' : 'p-8 mt-24 max-w-5xl mx-auto'} w-full`}>
        {children}
      </div>
    </div>
  );
}