import React from 'react';
import logo from '../../assets/logo.png';  // 강아지 로고 이미지

const Logo = ({ className = '' }) => {
  return (
    <div className={`relative flex flex-col items-start ${className}`}>
      
      {/* 로고 이미지 */}
      <img
        src={logo}
        alt="Company Logo"
        className="w-60 h-60 object-contain relative z-10"
      />

    </div>
  );
};

export default Logo ;