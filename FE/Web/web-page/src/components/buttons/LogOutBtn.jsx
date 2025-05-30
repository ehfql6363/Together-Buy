import React from 'react';

const LoginButton = ({ onClick }) => {
  return (
    <div 
      onClick={onClick}
      className="h-[45px] px-9 py-2.5 bg-[#F0E3C2] rounded-full shadow-md border border-[#B5912E] inline-flex justify-center items-center gap-2.5 cursor-pointer hover:bg-[#E8D5A7] transition-colors duration-200"
    >
      <div className="text-[#4E3A02] text-xl font-bold font-['Plus Jakarta Sans'] leading-6">
        로그아웃
      </div>
    </div>
  );
};

export default LoginButton;