import React from 'react';

const GenderSelector = ({ gender, setGender }) => {
  const handleSelect = (selectedGender) => {
    // Toggle gender selection
    const newGender = gender === selectedGender ? null : selectedGender;
    setGender(newGender);
    
  };

  const shadowStyle = {
    boxShadow: '0px 4px 4px 0px rgba(0,0,0,0.25)'
  };

  return (
    <div className="inline-flex">
      <button
        type="button"
        onClick={() => handleSelect('MALE')}
        className={`w-20 h-14 
          ${gender === 'MALE' ? 'bg-amber-100' : 'bg-white'}
          rounded-l-xl border border-[#4e3a02] 
          flex justify-center items-center transition-colors duration-200`}
        style={shadowStyle}
      >
        <span className="text-[#4e3a02] text-base font-bold leading-normal tracking-wide">
          남자
        </span>
      </button>
      <button
        type="button"
        onClick={() => handleSelect('FEMALE')}
        className={`w-20 h-14
          ${gender === 'FEMALE' ? 'bg-amber-100' : 'bg-white'}
          rounded-r-xl border-r border-t border-b border-[#4e3a02]
          flex justify-center items-center transition-colors duration-200`}
        style={shadowStyle}
      >
        <span className="text-[#4e3a02] text-base font-bold leading-normal tracking-wide">
          여자
        </span>
      </button>
    </div>
  );
};

export default GenderSelector;