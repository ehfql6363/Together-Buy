import React from 'react';

const CommonButton = ({ onClick, text = "button" }) => {
  return (
    <div 
      onClick={onClick}
      className="w-36 h-12 px-8 py-3 bg-amber-200 rounded-xl shadow-md border border-amber-600 inline-flex justify-center items-center gap-2.5 cursor-pointer hover:bg-amber-300 transition-colors duration-200"
    >
      <div className="text-amber-900 text-xl font-bold font-['Plus Jakarta Sans'] leading-6">
        {text}
      </div>
    </div>
  );
};

export default CommonButton;