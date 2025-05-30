import React, { useState } from 'react';

const DiscountUnitToggle = ({ onChange }) => {
  const [selectedUnit, setSelectedUnit] = useState('원');

  const handleUnitChange = () => {
    const newUnit = selectedUnit === '원' ? '%' : '원';
    setSelectedUnit(newUnit);
    if (onChange) {
      onChange(newUnit);
    }
  };

  return (
    <div className="relative">
      <span className="absolute -top-6 left-1/2 -translate-x-1/2 whitespace-nowrap text-sm text-gray-500 font-['Gaegu']">클릭!!</span>
      <button
        onClick={handleUnitChange}
        className="w-[60px] h-[60px] bg-[#ffecb4] rounded-[20px] border border-[#4e3a02] flex justify-center items-center hover:opacity-90"
      >
        <span className="text-[#4e3a02] text-xl font-bold font-['Plus Jakarta Sans'] leading-[23px]">
          {selectedUnit}
        </span>
      </button>
    </div>
  );
};

export default DiscountUnitToggle;