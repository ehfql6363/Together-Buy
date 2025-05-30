import React, { useState } from 'react';

const DeliveryStatusBtn = ({ status }) => (
  <div className="h-16 px-9 py-3 bg-[#f0e3c2] rounded-full shadow-[0px_4px_15px_0px_rgba(0,0,0,0.1)] border border-[#b5912d] inline-flex justify-center items-center">
    <div className="text-[#4e3a02] text-xl font-bold font-['Plus Jakarta Sans'] leading-6">
      {status === 'BEFORE' ? '발송 전' : '발송 완료'}
    </div>
  </div>
);

const ActionButton = ({ onClick, disabled, variant }) => {
  const getBgColor = () => {
    if (disabled) return 'bg-[#F5F0D9]';
    if (variant === 'cancel') return 'bg-[#C2AF7B]';
    return 'bg-[#ffecb4]';
  };

  const getTextColor = () => {
    if (disabled) return 'text-[#4E3A02]';
    if (variant === 'cancel') return 'text-[#FFFFFF]';
    return 'text-[#4e3a02]';
  };

  return (
    <div 
      onClick={disabled ? undefined : onClick}
      className={`h-16 px-9 py-3 ${getBgColor()} rounded-full shadow-[0px_4px_15px_0px_rgba(0,0,0,0.1)] border border-[#b5912d] inline-flex justify-center items-center ${!disabled && 'cursor-pointer hover:opacity-90 transition-opacity'}`}
    >
      <div className={`${getTextColor()} text-xl font-bold font-['Plus Jakarta Sans'] leading-6`}>
        {variant === 'cancel' ? '발송 취소' : '발송'}
      </div>
    </div>
  );
};

const DeliveryCard = ({
  orderNumber,
  productName,
  address,
  status: initialStatus = 'BEFORE',
  trackingNumber: initialTrackingNumber = '',
  onTrackingNumberChange,
  onSend,
  onCancel
}) => {
  const [status, setStatus] = useState(initialStatus);
  const [trackingNumber, setTrackingNumber] = useState(initialTrackingNumber);

  const isValidTrackingNumber = trackingNumber.length >= 8;
  const isBeforeDelivery = status === 'BEFORE';

  const handleSend = () => {
    if (isValidTrackingNumber) {
      setStatus('COMPLETE');
      if (onSend) {
        onSend(trackingNumber);
      }
    }
  };

  const handleCancel = () => {
    setStatus('BEFORE');
    setTrackingNumber('');
    if (onCancel) {
      onCancel();
    }
  };

  const handleTrackingNumberChange = (value) => {
    setTrackingNumber(value);
    if (onTrackingNumberChange) {
      onTrackingNumberChange(value);
    }
  };

  return (
    <div className="w-[1022px] h-[208px] relative bg-[#faf7f0] rounded-[30px] shadow-[0px_4px_20px_0px_rgba(0,0,0,0.08)] border border-[#b5912d]">
      <div className="pl-[195px] pt-8">
        <div>
          <span className="text-[#4e3a02] text-[32px] font-bold font-['Plus Jakarta Sans'] leading-6">
            {productName}
          </span>
        </div>
        <div className="mt-4 text-[#4e3a02] text-2xl font-light font-['Plus Jakarta Sans'] leading-6">
          주소 : {address}
        </div>
        <div className="mt-8 flex gap-3 items-center">
          <DeliveryStatusBtn status={status} />
          {isBeforeDelivery ? (
            <>
              <input
                type="text"
                value={trackingNumber}
                onChange={(e) => handleTrackingNumberChange(e.target.value)}
                placeholder="운송장 번호 (8자리 이상)"
                className="w-[492px] h-16 px-9 py-3 bg-white rounded-full shadow-[0px_4px_15px_0px_rgba(0,0,0,0.1)] border border-[#b5912d] text-[#4e3a02] text-xl font-bold font-['Plus Jakarta Sans'] leading-6 focus:outline-none"
              />
              <ActionButton onClick={handleSend} disabled={!isValidTrackingNumber} variant="send" />
            </>
          ) : (
            <ActionButton onClick={handleCancel} variant="cancel" />
          )}
        </div>
      </div>
    </div>
  );
};

const DeliveryManagementCard = ({ deliveries = [] }) => {
  return (
    <div className="w-full p-5 space-y-5">
      {deliveries.map((delivery) => (
        <DeliveryCard
          key={delivery.orderNumber}
          {...delivery}
        />
      ))}
    </div>
  );
};

export default DeliveryManagementCard;