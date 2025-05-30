import React from 'react';

const CommonBtn = ({ onClick, children, className = '', variant = 'primary', textSize = 'text-2xl', buttonSize = 'w-[200px] h-[60px]' }) => {
  const getVariantStyles = () => {
    switch (variant) {
      case 'secondary':
        return 'bg-[#c2af7b] text-white';
      default:
        return 'bg-[#ffecb4] text-[#4e3a02]';
    }
  };

  return (
    <div 
      onClick={onClick}
      className={`inline-flex justify-center items-center gap-2.5 cursor-pointer 
        ${buttonSize} px-8 py-3 
        ${getVariantStyles()}
        rounded-[20px]
        shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]
        border border-[#b5912d]
        hover:opacity-90 transition-opacity 
        ${className}`}
    >
      <div className={`${textSize} font-bold font-['Plus Jakarta Sans'] leading-[23px]`}>
        {children}
      </div>
    </div>
  );
};

export default CommonBtn;
