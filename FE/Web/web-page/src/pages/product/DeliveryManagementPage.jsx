import React, { useState, useEffect, useRef } from 'react';
import { useAuth } from '../../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import DeliveryManagementCard from '../../components/cards/DeliveryManagementCard';

const DeliveryManagementPage = () => {
  const [deliveries, setDeliveries] = useState([]);
  const [filteredDeliveries, setFilteredDeliveries] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeFilter, setActiveFilter] = useState('all');
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
          window.history.go(-1);
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
  
  useEffect(() => {
    if (isSeller) {
      fetchDeliveries();
    }
  }, [isSeller, activeFilter]);

  useEffect(() => {
    filterDeliveries();
  }, [deliveries, activeFilter]);

  
  const fetchDeliveries = async () => {
    try {
      console.log('delivery log: ')
      const response = await makeRequest('GET', 'api/seller/orders/delivery/all');
      console.log(response)

      let deliveryData = [];
      if (Array.isArray(response)) {
        console.log("1");
        deliveryData = response;
      } else if (response.data && Array.isArray(response.data)) {
        console.log("2");
        deliveryData = response.data;
      } else if (response.data && typeof response.data === 'object') {
        console.log("3");
        deliveryData = Object.values(response.data);
      } else {
        console.log("4");
        throw new Error('Unexpected data format');
      }
      console.log("ok");
      console.log(deliveryData);
      setDeliveries(deliveryData[0]);
    } catch (err) {
      console.error('Fetch deliveries error:', err);
      setError(err.message || '데이터를 불러오는데 실패했습니다');
    } finally {
      setIsLoading(false);
    }
  };

  const filterDeliveries = () => {
    let filtered = deliveries;
    switch (activeFilter) {
      case 'before-send':
        filtered = deliveries.filter(delivery => delivery.status === 'BEFORE');
        break;
      case 'completed':
        filtered = deliveries.filter(delivery => 
          delivery.status === 'COMPLETE' || delivery.status === 'DELIVERY'
        );
        break;
      default: // 'all'
        filtered = deliveries;
    }
    console.log(filtered)
    setFilteredDeliveries(filtered);
  };
  console.log(filteredDeliveries)
  const handlewayBillNumberChange = (orderId, value) => {
    const updatedDeliveries = deliveries.map(delivery =>
      delivery.orderId === orderId
        ? { ...delivery, wayBillNumber: value }
        : delivery
    );
    setDeliveries(updatedDeliveries);
  };
  
  const handleSend = async (orderId, wayBillNumber) => {
    try {
      const response = await makeRequest('POST', `api/seller/order/${orderId}/delivery/${wayBillNumber}`);
      
      if (response.status === 200) {
        const updatedDeliveries = deliveries.map(delivery =>
          delivery.orderId === orderId
            ? { ...delivery, status: 'DELIVERY' }
            : delivery
        );
        setDeliveries(updatedDeliveries);
      }
    } catch (err) {
      console.error('발송 처리 실패:', err);
    }
  };

  const handleCancel = (orderId) => {
    const updatedDeliveries = deliveries.map(delivery =>
      delivery.orderId === orderId
        ? { ...delivery, status: 'BEFORE', wayBillNumber: '' }
        : delivery
    );
    setDeliveries(updatedDeliveries);
  };

  const FilterButton = ({ filter, label }) => (
    <button
      className={`
        h-16 px-9 py-3 rounded-full shadow-[0px_4px_15px_0px_rgba(0,0,0,0.1)] 
        border border-[#b5912d] inline-flex justify-center items-center
        transition-opacity hover:opacity-90
        ${activeFilter === filter 
          ? 'bg-[#C2AF7B] text-white' 
          : 'bg-[#f0e3c2] text-[#4e3a02]'}
        text-xl font-bold font-['Plus Jakarta Sans'] leading-6
      `}
      onClick={() => setActiveFilter(filter)}
    >
      {label}
    </button>
  );

  if (isLoading) {
    return (
      <div className="min-h-screen bg-[#faf7f0] flex justify-center items-center">
        <p>로딩 중...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-[#faf7f0] flex justify-center items-center">
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <h1 className="text-[40px] font-bold mb-12">배송 관리</h1>
          
          {/* Filter Buttons */}
          <div className="flex space-x-4 mb-8">
            <FilterButton filter="all" label="전체" />
            <FilterButton filter="before-send" label="발송 전" />
            <FilterButton filter="completed" label="발송 완료" />
          </div>
          
          {/* Delivery List */}
          <div className="space-y-6">
            {filteredDeliveries.length > 0 ? (
              filteredDeliveries.map((delivery) => (
                <DeliveryManagementCard
                  key={delivery.orderId}
                  deliveries={[{
                    orderNumber: delivery.orderId,
                    productName: delivery.productName,
                    productMainImage: delivery.productMainImage,
                    address: delivery.address,
                    status: delivery.status,
                    wayBillNumber: delivery.wayBillNumber,
                    onwayBillNumberChange: (value) => 
                    handlewayBillNumberChange(delivery.orderId, value),
                    onSend: (wayBillNumber) => handleSend(delivery.orderId, wayBillNumber),
                    onCancel: () => handleCancel(delivery.orderId)
                  }]}
                />
              ))
            ) : (
              <div className="text-center text-gray-500 py-10">
                해당 상태의 주문이 없습니다.
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};


export default DeliveryManagementPage;