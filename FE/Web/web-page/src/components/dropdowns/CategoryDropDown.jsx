import React, { useState, useEffect } from 'react';
import { ChevronDown, ChevronUp } from 'lucide-react';

const CategoryDropdown = ({ 
  options = [], 
  onChange, 
  value, 
  width = 'w-[200px]', 
  height = 'h-14',
  disabled = false,
  placeholder = '카테고리' 
}) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(placeholder);

  // value prop이 변경될 때 selectedCategory 업데이트
  useEffect(() => {
    if (value) {
      const selectedItem = options.find(option => option.id === value);
      if (selectedItem) {
        setSelectedCategory(selectedItem.name);
      }
    } else {
      setSelectedCategory(placeholder);
    }
  }, [value, options, placeholder]);

  const handleSelect = (category) => {
    setSelectedCategory(category.name);
    setIsOpen(false);
    if (onChange) onChange(category.id);
  };
  
  const innerShadowStyle = {
    boxShadow: 'inset 0px 4px 4px 0px rgba(0,0,0,0.25)'
  };

  return (
    <div className={`relative ${width}`}>
      {/* Dropdown Button */}
      <button
        onClick={() => !disabled && setIsOpen(!isOpen)}
        className={`
          ${width} ${height} px-8 bg-white rounded-full border border-[#4e3a02] 
          flex justify-between items-center w-full
          ${disabled ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer'}
        `}
        style={innerShadowStyle}
        disabled={disabled}
      >
        <span className="text-[#4e3a02] text-lg font-bold leading-normal tracking-wide truncate">
          {selectedCategory}
        </span>
        {isOpen ? (
          <ChevronUp className="w-6 h-6 text-[#4e3a02] flex-shrink-0" />
        ) : (
          <ChevronDown className="w-6 h-6 text-[#4e3a02] flex-shrink-0" />
        )}
      </button>

      {/* Dropdown Menu */}
      {isOpen && !disabled && options.length > 0 && (
        <div className="absolute z-10 left-0 right-0 mt-1">
          <div 
            className={`
              ${width} w-full bg-white border border-[#4e3a02] 
              rounded-[30px] py-4 overflow-hidden
            `}
            style={innerShadowStyle}
          >
            {options.map((category, index) => (
              <button
                key={category.id}
                onClick={() => handleSelect(category)}
                className={`
                  w-full px-8 py-2 text-left text-[#4e3a02] text-base font-normal 
                  leading-normal tracking-wide hover:bg-amber-100 truncate
                  ${index === 0 ? 'rounded-t-[30px]' : ''}
                  ${index === options.length - 1 ? 'rounded-b-[30px]' : ''}
                `}
              >
                {category.name}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default CategoryDropdown;