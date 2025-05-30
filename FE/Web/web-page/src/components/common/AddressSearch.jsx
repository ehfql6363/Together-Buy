import React, { useState } from 'react';

const AddressSearch = ({
  onAddressSelect,
  status = "default",
  onFocus,
  onBlur,
  containerStyle = "",
  width = "w-full",
  height = "h-14"
}) => {
  const [address, setAddress] = useState('');
  const [inputStatus, setInputStatus] = useState(status);

  const getStatusClasses = () => {
    switch (inputStatus) {
      case 'default':
        return {
          container: 'border border-[#b5912d] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
      case 'focused':
      case 'filled':
        return {
          container: 'border-2 border-[#9747ff] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
      default:
        return {
          container: 'border border-[#b5912d] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
    }
  };

  const statusClasses = getStatusClasses();

  const openAddressSearch = (e) => {
    e.preventDefault();  // 이벤트 전파 방지
    
    if (!window.daum) {
      const script = document.createElement('script');
      script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
      document.head.appendChild(script);
    }

    new window.daum.Postcode({
      oncomplete: function(data) {
        const fullAddress = data.address;
        const extraAddress = data.buildingName ? ` (${data.buildingName})` : '';
        
        setAddress(fullAddress + extraAddress);
        onAddressSelect(fullAddress + extraAddress);
        setInputStatus('filled');
      }
    }).open();
  };

  return (
    <div className={`w-full ${containerStyle}`}>
      <label className={`block ${statusClasses.label} mb-2`}>
        주소
      </label>
      <div className="flex gap-4">
        <input
          type="text"
          value={address}
          readOnly
          className={`
            ${width} ${height} px-8 py-2 bg-white rounded-xl
            text-xl font-bold focus:outline-none
            ${statusClasses.container} ${statusClasses.input}
          `}
          style={{
            boxShadow: 'inset 0px 4px 4px 0px rgba(0,0,0,0.25)'
          }}
          onFocus={() => {
            onFocus('address');
            setInputStatus('focused');
          }}
          onBlur={() => {
            onBlur('address');
            setInputStatus(address ? 'filled' : 'default');
          }}
          placeholder="주소 검색을 클릭하세요"
        />
        <button
          type="button"
          onClick={openAddressSearch}
          className="min-w-[120px] h-14 bg-[#FFECB4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#B5912D] text-[#4E3A02] text-lg font-bold hover:bg-[#f0d69a] transition-colors duration-200"
        >
          주소 검색
        </button>
      </div>
    </div>
  );
};

export default AddressSearch;