import React, { useState } from 'react';

// 버튼 컴포넌트들
const EditProductBtn = ({ onClick, disabled }) => (
  <div 
    onClick={disabled ? undefined : onClick}
    className={`h-[60px] px-[37px] py-[11px] ${disabled ? 'bg-[#E0E0E0]' : 'bg-[#f0e3c2]'} rounded-[100px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] inline-flex justify-center items-center gap-2.5 ${!disabled && 'cursor-pointer hover:bg-[#f0e3c2]/80 transition-colors duration-200'}`}
  >
    <div className={`${disabled ? 'text-[#888888]' : 'text-[#4e3a02]'} text-xl font-bold font-['Plus Jakarta Sans'] leading-[23px]`}>
      상품 정보 수정
    </div>
  </div>
);

const StatusCheckBtn = ({ onClick, disabled }) => (
  <div 
    onClick={(e) => {
      if (!disabled) {
        console.log('현황 조회 버튼 클릭됨');
        onClick && onClick(e);
      }
    }}
    className={`h-[60px] px-[37px] py-[11px] ${disabled ? 'bg-[#E0E0E0]' : 'bg-[#f0e3c2]'} rounded-[100px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] inline-flex justify-center items-center gap-2.5 ${!disabled && 'cursor-pointer hover:bg-[#f0e3c2]/80 transition-colors duration-200'}`}
  >
    <div className={`${disabled ? 'text-[#888888]' : 'text-[#4e3a02]'} text-xl font-bold font-['Plus Jakarta Sans'] leading-[23px]`}>
      현황 조회
    </div>
  </div>
);

const SoldOutBtn = ({ onClick, isSoldOut }) => (
  <div 
    onClick={onClick}
    className={`h-[60px] px-[37px] py-[11px] ${isSoldOut ? 'bg-[#c1af7b]' : 'bg-[#f0e3c2]'} rounded-[100px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] inline-flex justify-center items-center gap-2.5 cursor-pointer hover:opacity-90 transition-opacity`}
  >
    <div className={`${isSoldOut ? 'text-[#faf7f0]' : 'text-[#4e3a02]'} text-xl font-bold font-['Plus Jakarta Sans'] leading-[23px]`}>
      {isSoldOut ? '품절 해제' : '품절'}
    </div>
  </div>
);

const DeleteBtn = ({ onClick, disabled }) => (
  <div 
    onClick={disabled ? undefined : onClick}
    className={`h-[60px] px-[37px] py-[11px] ${disabled ? 'bg-[#E0E0E0]' : 'bg-[#c1af7b]'} rounded-[100px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] inline-flex justify-center items-center gap-2.5 ${!disabled && 'cursor-pointer hover:opacity-90 transition-opacity'}`}
  >
    <div className={`${disabled ? 'text-[#888888]' : 'text-[#faf7f0]'} text-xl font-bold font-['Plus Jakarta Sans'] leading-[23px]`}>
      삭제
    </div>
  </div>
);

// 메인 카드 컴포넌트
const ProductManagementCard = ({
  productName,
  category,
  price,
  salePrice,
  imageUrl,
  onEdit,
  onStatus,
  onSoldOut,
  onDelete
}) => {
  const [isSoldOut, setIsSoldOut] = useState(false);

  const handleSoldOut = () => {
    const newSoldOutStatus = !isSoldOut;
    setIsSoldOut(newSoldOutStatus);
    
    if (onSoldOut) {
      onSoldOut(newSoldOutStatus);
    }
  };

  const getCardBackgroundColor = () => {
    if (isSoldOut) return 'bg-[#F5F5F5]';
    return 'bg-[#faf7f0]';
  };

  const getCardBorderColor = () => {
    if (isSoldOut) return 'border-[#CCCCCC]';
    return 'border-[#b5912d]';
  };

  return (
    <div className={`w-[1022px] h-[208px] relative ${getCardBackgroundColor()} rounded-[30px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] ${getCardBorderColor()} overflow-hidden`}>
      <div className="w-[96px] h-[96px] left-[36px] top-[56px] absolute overflow-hidden">
        <img 
          src={imageUrl || "/api/placeholder/96/96"} 
          alt={productName}
          className={`w-full h-full object-cover rounded-[50%] ${isSoldOut ? 'opacity-50' : ''}`}
        />
      </div>

      <div className="left-[195px] top-[32px] absolute">
        <div>
          <span className={`text-[32px] font-bold font-['Plus Jakarta Sans'] leading-[23px] ${isSoldOut ? 'text-[#888888]' : 'text-[#4e3a02]'}`}>
            {productName}
          </span>
          <span className={`text-xl font-extralight font-['Plus Jakarta Sans'] leading-[23px] ml-2 ${isSoldOut ? 'text-[#888888]/70' : 'text-[#4e3a02]/70'}`}>
            ({category})
          </span>
        </div>
        <div className={`mt-[14px] text-2xl font-light font-['Plus Jakarta Sans'] leading-[23px] ${isSoldOut ? 'text-[#888888]' : 'text-[#4e3a02]'}`}>
          {salePrice ? (
            <>
              <span className="line-through text-gray-500">{price.toLocaleString()}원</span>
              <span className="ml-2 text-red-600">{salePrice.toLocaleString()}원</span>
            </>
          ) : (
            <span>{price.toLocaleString()}원</span>
          )}
        </div>
      </div>

      <div className="left-[195px] top-[118px] absolute flex gap-[13px]">
        <EditProductBtn 
          onClick={onEdit} 
          disabled={isSoldOut} 
        />
        <StatusCheckBtn 
          onClick={onStatus} 
          disabled={isSoldOut} 
        />
        <SoldOutBtn 
          onClick={handleSoldOut} 
          isSoldOut={isSoldOut} 
        />
        <DeleteBtn 
          onClick={onDelete} 
          disabled={isSoldOut} 
        />
      </div>
    </div>
  );
};

export default ProductManagementCard;