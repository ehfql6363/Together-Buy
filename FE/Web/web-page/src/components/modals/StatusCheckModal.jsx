import React, { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import { useAuth } from '../../contexts/AuthContext';

const StatusCheckModal = ({ isOpen, onClose, productId }) => {
  const { makeRequest } = useAuth();
  const [orderIds, setOrderIds] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(0);
  const [orderDetails, setOrderDetails] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // 주문 ID 목록 가져오기
  useEffect(() => {
    const fetchOrderIds = async () => {
      if (!isOpen || !productId) return;
      
      setLoading(true);
      setError(null);
      
      try {
        const response = await makeRequest('GET', `api/seller/product/${productId}/orders`);
        console.log(response.data.orders)
        // response.data가 배열인지 확인하고, 배열이 아니면 빈 배열로 설정
        const orderIdsArray = Array.isArray(response.data.orders) ? response.data.orders : [];
        setOrderIds(orderIdsArray);
        
        // 첫 번째 주문의 상세 정보 가져오기
        if (orderIdsArray.length > 0) {
          fetchOrderDetails(orderIdsArray[0]);
        }
      } catch (err) {
        setError(err.message);
        console.error('Error fetching order IDs:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchOrderIds();
  }, [isOpen, productId, makeRequest]);

  // 주문 상세 정보 가져오기
  const fetchOrderDetails = async (orderId) => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await makeRequest('GET', `api/seller/order/${orderId}`);
      console.log(response.data.order)
      setOrderDetails(response.data.order);
    } catch (err) {
      setError(err.message);
      console.error('Error fetching order details:', err);
    } finally {
      setLoading(false);
    }
  };

  // 주문 선택 시 상세 정보 가져오기
  const handleOrderSelect = (index, orderId) => {
    setSelectedOrder(index);
    fetchOrderDetails(orderId);
  };

  if (!isOpen) return null;
  if (loading && !orderDetails) return createPortal(
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-[9999]">
      <div className="bg-white p-4 rounded-lg">
        Loading...
      </div>
    </div>,
    document.body
  );
  if (error) return createPortal(
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-[9999]">
      <div className="bg-white p-4 rounded-lg">
        Error: {error}
      </div>
    </div>,
    document.body
  );

  // 총 판매금액 계산
  const totalSales = orderDetails?.orderPrice || 0;
  
  return createPortal(
    <>
      <div 
        className="fixed inset-0 bg-black bg-opacity-50 z-[9999]"
        onClick={onClose}
      />
      
      <div className="fixed inset-0 flex items-center justify-center z-[10000]">
        <div 
          className="bg-[#f9f5eb] rounded-[30px] w-full max-w-3xl relative border border-[#b5912d] shadow-2xl p-8"
          onClick={(e) => e.stopPropagation()}
        >
          {/* 상단 제목 영역 */}
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-bold">주문 상세 정보</h2>
            <button 
              onClick={onClose}
              className="text-black hover:opacity-70 text-2xl"
            >
              ✕
            </button>
          </div>

          <div className="flex">
            {/* 왼쪽 주문내역 리스트 */}
            <div className="w-[200px] bg-[#f8f3e7] min-h-[500px] rounded-l-[20px] overflow-hidden shadow-lg">
              {orderIds.map((orderId, index) => (
                <div
                  key={orderId}
                  onClick={() => handleOrderSelect(index, orderId)}
                  className={`py-4 px-6 cursor-pointer border-b border-[#e0d6bd] text-center ${
                    selectedOrder === index 
                    ? 'bg-[#ffecb4] font-bold' 
                    : ''
                  }`}
                >
                  주문 {index + 1}
                </div>
              ))}
            </div>

            {/* 오른쪽 상세 정보 */}
            <div className="flex-1 p-6">
              <div className="space-y-6">
                {/* 총 판매금액 섹션 */}
                <div className="border border-[#b5912d] rounded-[20px] p-6 bg-white shadow-lg">
                  <h3 className="text-lg font-bold mb-4">
                    총 판매금액 : {totalSales.toLocaleString()}원
                  </h3>
                  <div className="space-y-2">
                    {orderDetails?.participants?.map((participant, index) => (
                      <div key={index} className="flex justify-between">
                        <span>{participant.nickname} :</span>
                        <span>{participant.cost.toLocaleString()}원 ({participant.amount}개)</span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* 정산 금액 섹션 */}
                <div className="shadow-lg p-4 rounded-[15px]">
                  <h3 className="text-lg font-bold mb-2">배송 정보</h3>
                  <div className="space-y-2 bg-white p-4 rounded-[15px] border border-[#b5912d]">
                    <div>배송 상태: {orderDetails?.deliveryStatus}</div>
                    <div>주문 시간: {orderDetails?.orderTime}</div>
                    <div>배송 지역: {orderDetails?.orderAddress}</div>
                    <div>수령인: {orderDetails?.recipient?.nickname}</div>
                    <div>상세주소: {orderDetails?.recipient?.address}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>,
    document.body
  );
};

export default StatusCheckModal;