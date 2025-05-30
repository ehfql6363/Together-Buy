import React from 'react';

const CategoryItem = ({ label, displayLabel, isSelected, onClick, isFirst, isLast }) => (
  <button
    onClick={onClick}
    className={`h-14 px-6
      ${isSelected ? 'bg-[#F5F0D9]' : 'bg-[#FAF7F0]'}
      ${isFirst ? 'rounded-l-xl' : ''}
      ${isLast ? 'rounded-r-xl' : ''}
      border-t border-b border-[#4e3a02]
      ${isFirst ? 'border-l' : ''}
      ${isLast ? 'border-r' : ''}
      flex justify-center items-center transition-colors duration-200
      shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)]`}
    style={{ 
      width: displayLabel === '냉장 / 냉동 / 간편식' ? '200px' : 
             displayLabel === '간식 / 베이커리' ? '160px' : 
             displayLabel === '전체' ? '100px' : '140px' 
    }}
  >
    <span className="text-[#4e3a02] text-base font-bold leading-normal tracking-wide whitespace-nowrap">
      {displayLabel}
    </span>
  </button>
);

const CategorySelector = ({ selectedCategory, onCategorySelect }) => {
  // 카테고리 매핑 (실제 키 : 표시 레이블)
  const categoryMapping = {
    '전체': '전체',
    '정육/계란': '정육 / 계란',
    '과일/채소': '과일 / 채소',
    '냉장/냉동/간편식': '냉장 / 냉동 / 간편식',
    '커피/음료': '커피 / 음료',
    '간식/베이커리': '간식 / 베이커리'
  };

  return (
    <div className="w-full mb-8">
      <div className="flex justify-start">
        {Object.entries(categoryMapping).map(([key, label], index) => (
          <CategoryItem
            key={key}
            label={key}
            displayLabel={label}
            isSelected={selectedCategory === key}
            onClick={() => onCategorySelect(key)}
            isFirst={index === 0}
            isLast={index === Object.keys(categoryMapping).length - 1}
          />
        ))}
      </div>
    </div>
  );
};

export default CategorySelector;