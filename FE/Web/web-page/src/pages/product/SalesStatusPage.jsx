import React, { useState, useEffect } from 'react';
import CategorySelector from '../../components/selectors/CategorySelector';  
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { RecentOrders } from '../../components/cards/SalesStatusCard';
import { categorySalesData, recentOrders } from '../../data/salesDummyData';

const formatSalesValue = (value) => {
  return new Intl.NumberFormat('ko-KR', {
    style: 'currency',
    currency: 'KRW'
  }).format(value);
};

const SalesChart = ({ data }) => (
  <div className="w-full bg-white p-4 rounded-[20px] border border-[#b5912d]" style={{ height: '400px' }}>
    <ResponsiveContainer width="100%" height="100%">
      <LineChart data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="category" />
        <YAxis 
          tickFormatter={(value) => `${Math.floor(value / 10000)}만`}
        />
        <Tooltip 
          formatter={(value) => formatSalesValue(value)}
          labelFormatter={(label) => `${label} 매출`}
        />
        <Line 
          type="monotone" 
          dataKey="sales" 
          stroke="#FFB6C1" 
          strokeWidth={2} 
        />
      </LineChart>
    </ResponsiveContainer>
  </div>
);

const SalesStatusPage = () => {
  const [selectedCategory, setSelectedCategory] = useState('전체');
  const [isSeller, setIsSeller] = useState(false);
  const { makeRequest, decodeToken } = useAuth();
  const [isAuthorized, setIsAuthorized] = useState(true);
  const navigate = useNavigate();
  
  useEffect(() => {
    // 토큰 확인 및 디코딩
    const checkSellerStatus = () => {
      const token = localStorage.getItem('token');
      if (token) {
        const decoded = decodeToken(token);
        if (decoded.isSeller) {
          setIsSeller(true);
          setIsAuthorized(true);
        } else {
          // 판매자가 아니라면 이전 페이지로 돌아가기
          alert('판매자만 접근할 수 있습니다.');
          navigate('/my-page')
        }
      } else {
        // 토큰이 없다면 이전 페이지로 돌아가기
        setIsAuthorized(false);
        alert('로그인이 필요합니다.');
        navigate('/login');;
      }
    };

    checkSellerStatus();
  }, [decodeToken]);

  if(!isSeller){
    return null;
  };

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
  };

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <h1 className="text-[40px] font-bold mb-12">매출 현황</h1>
          
          <CategorySelector 
            selectedCategory={selectedCategory}
            onCategorySelect={handleCategorySelect}
          />
          
          <div className="mb-12">
            <SalesChart data={categorySalesData[selectedCategory]} />
          </div>
          
          <div className="mb-6 flex justify-between items-center">
            <h2 className="text-2xl font-bold">최근 주문 현황</h2>
            <span className="text-[#b5912d] text-lg">
              {selectedCategory === '전체' ? '전체 주문' : `${selectedCategory} 주문`}
            </span>
          </div>
          
          <div className="w-full">
            <RecentOrders 
              orders={recentOrders}
              selectedCategory={selectedCategory}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SalesStatusPage;