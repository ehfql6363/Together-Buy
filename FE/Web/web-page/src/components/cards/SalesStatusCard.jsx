import React from 'react';

const SalesStatusCard = ({
  orderNumber,
  productName,
  amount,
  address,
  imageUrl,
  category  // 카테고리 prop 추가
}) => {
  const formatPrice = (price) => {
    return price.toLocaleString() + '원';
  };

  return (
    <div className="w-[1022px] h-[208px] relative bg-[#faf7f0] rounded-[30px] shadow-[0px_4px_20px_0px_rgba(0,0,0,0.08)] border border-[#b5912d]">
      {/* 상품 이미지 */}
      <div className="w-32 h-32 left-[48px] top-[40px] absolute">
        <img 
          src={imageUrl}
          alt={productName}
          className="w-full h-full object-cover rounded-xl shadow-[0px_4px_15px_0px_rgba(0,0,0,0.1)]"
        />
      </div>

      {/* 주문 정보 */}
      <div className="pl-[195px] pt-8">
        <div>
          <span className="text-[#4e3a02] text-[32px] font-bold font-['Plus Jakarta Sans'] leading-6">
            {productName}
          </span>
          <span className="ml-4 text-[#b5912d] text-lg">
            {category}
          </span>
        </div>
        
        <div className="mt-4 text-[#4e3a02] text-2xl font-light font-['Plus Jakarta Sans'] leading-6">
          주문 번호 : {orderNumber}
        </div>
        
        <div className="mt-4 text-[#4e3a02] text-2xl font-bold font-['Plus Jakarta Sans'] leading-6">
          가격 : {formatPrice(amount)}
        </div>
        
        <div className="mt-4 text-[#4e3a02] text-2xl font-light font-['Plus Jakarta Sans'] leading-6">
          주소 : {address}
        </div>
      </div>
    </div>
  );
};

const RecentOrders = ({ orders, selectedCategory }) => {
  const filteredOrders = selectedCategory === '전체'
    ? orders
    : orders.filter(order => order.category === selectedCategory);

  if (filteredOrders.length === 0) {
    return (
      <div className="w-full text-center py-8 bg-white rounded-[20px] border border-[#b5912d]">
        <p className="text-gray-500">해당 카테고리의 최근 주문이 없습니다.</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {filteredOrders.map((order) => (
        <SalesStatusCard
          key={order.orderNumber}
          {...order}  // 모든 props를 전달
        />
      ))}
    </div>
  );
};

export { SalesStatusCard, RecentOrders };