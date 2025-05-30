import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import Input from '../../components/inputs/Input';
import BankDropdown from '../../components/dropdowns/BankDropDown';
import GenderSelector from '../../components/selectors/GenderSelector'; 
import _ from 'lodash';

const InfoBox = ({ label, value }) => {
  return (
    <div className="w-full">
      <label className="block text-[#4e3a02] text-xl font-bold mb-4">
        {label}
      </label>
      <div 
        className="w-full h-14 px-8 bg-white rounded-xl
          text-[#4E3A02] text-xl font-bold
          border border-[#b5912d] rounded-[20px]
          flex items-center"
        style={{
          boxShadow: 'inset 0px 4px 4px 0px rgba(0,0,0,0.25)'
        }}
      >
        {value || ''}
      </div>
    </div>
  );
};

const MyPage = () => {
  const navigate = useNavigate();
  const { makeRequest, token } = useAuth();
  const [isEditing, setIsEditing] = useState(false);
  const [userData, setUserData] = useState({});
  const [loading, setLoading] = useState(true);
  const [gender, setGender] = useState(null);
  const [decodedToken, setDecodedToken] = useState(null);
  const [profileImageFromToken, setProfileImageFromToken] = useState(null);

  useEffect(() => {
    const fetchUserData = async () => {
      decodeToken();

      try {
        const response = await makeRequest('get', 'api/member/mypage');
        setUserData(response.data);
        setGender(response.data.gender === 0 ? 'MALE' : response.data.gender === 1 ? 'FEMALE' : null);
        setLoading(false);
      } catch (error) {
        console.error('Failed to fetch user data:', error);
        setLoading(false);
      }
    };

    const decodeToken = () => {
      try {
        if (!token || token.split('.').length !== 3) {
          console.error('유효하지 않은 토큰 형식');
          return;
        }
    
        // base64url 디코딩을 위한 패딩 처리
        const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
        const padding = '='.repeat((4 - base64.length % 4) % 4);
        const decodedToken = atob(base64 + padding);
        const tokenPayload = JSON.parse(decodedToken);
        setDecodedToken(tokenPayload);
        
        // 토큰에서 프로필 이미지 추출
        if (tokenPayload.profileImage) {
          setProfileImageFromToken(tokenPayload.profileImage);
        }
        
      } catch (error) {
        console.error('토큰 디코딩 실패:', error);
      }
    };
    fetchUserData();
  }, [makeRequest, token]);

  // Format phone number as user types
  const formatPhoneNumber = (value) => {
    const number = value.replace(/[^\d]/g, '');
    
    if (number.length <= 4) return number;
    if (number.length <= 7) {
      const areaCode = number.slice(0, 3);
      const firstPart = number.slice(3);
      return `${areaCode}-${firstPart}`;
    }
    if (number.length <= 11) {
      const areaCode = number.slice(0, 3);
      const firstPart = number.slice(3, 7);
      const secondPart = number.slice(7);
      return `${areaCode}-${firstPart}-${secondPart}`;
    }
    return `${number.slice(0, 3)}-${number.slice(3, 7)}-${number.slice(7, 11)}`;
  };

  const formatBirthDate = (value) => {
    const date = value.replace(/[^\d]/g, '');
    
    if (date.length <= 4) return date;
    if (date.length <= 6) {
      return `${date.slice(0, 4)}-${date.slice(4)}`;
    }
    if (date.length <= 8) {
      return `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6)}`;
    }
    return `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6, 8)}`;
  };

  const handleInputChange = (name, value) => {
    let formattedValue = value;
    
    if (name === 'tel' || name === 'seller.businessTel') {
      formattedValue = formatPhoneNumber(value);
    }
    
    if (name === 'birth') {
      formattedValue = formatBirthDate(value);
    }

    if (name.includes('seller.')) {
      setUserData(prev => ({
        ...prev,
        seller: {
          ...prev.seller,
          [name.replace('seller.', '')]: formattedValue
        }
      }));
    } else {
      setUserData(prev => ({
        ...prev,
        [name]: formattedValue
      }));
    }
  };

  const handleEdit = async () => {
    if (isEditing) {
      try {
        const sellerKeys = businessInputs.map(input => input.key.replace('seller.', ''));
        
        const sellerData = sellerKeys.reduce((acc, key) => {
          const value = _.get(userData, `seller.${key}`);
          return value ? { ...acc, [key]: value } : acc;
        }, {});
  
        const userInfo = _.omit(userData, 'seller');
        const genderValue = gender === 'MALE' ? 0 : gender === 'FEMALE' ? 1 : null;
  
        const requestData = {
          memberId: userData.memberId,
          ...userInfo,
          gender: genderValue,
          seller: Object.keys(sellerData).length > 0 ? sellerData : null
        };
        
        const response = await makeRequest('put', 'api/member/mypage', requestData, {
          headers: {
            'Content-Type': 'application/json'
          }
        });

        setUserData(prevData => ({
          ...prevData,
          ...response.data,
          gender: genderValue
        }));
  
        alert('정보 수정이 완료되었습니다.');
      } catch (error) {
        console.error('Failed to update user data:', error);
        alert('정보 수정 중 오류가 발생했습니다.');
      }
    }
    setIsEditing(!isEditing);
  };

  const handleInputFocus = (name) => {
    // Focus 이벤트 처리
  };

  const handleInputBlur = (name) => {
    // Blur 이벤트 처리
  };

  const personalInputs = [
    { key: 'nickname', label: '닉네임' },
    { key: 'tel', label: '연락처' },
    { key: 'birth', label: '생년월일' },
    { key: 'gender', label: '성별' },
    { key: 'address', label: '주소' },
    { key: 'bankName', label: '은행' },
    { key: 'bankNumber', label: '계좌번호' }
  ];

  const businessInputs = [
    { key: 'seller.businessName', label: '상호' },
    { key: 'seller.bossName', label: '대표자' },
    { key: 'seller.businessTel', label: '사업장 연락처' },
    { key: 'seller.businessEmail', label: 'E-Mail' },
    { key: 'seller.businessAddress', label: '사업장 소재' },
    { key: 'seller.businessNumber', label: '사업자 번호' }
  ];

  const getDisplayValue = (key, value) => {
    if (key === 'gender') {
      if (value === 0) return '남자';
      if (value === 1) return '여자';
      return '';
    }
    return value;
  };

  const renderInput = (key, label) => {
    const rawValue = key.includes('seller.') ? _.get(userData, key, '') : userData[key];
    const displayValue = getDisplayValue(key, rawValue);
    
    if (isEditing) {
      if (key === 'bankName') {
        return (
          <BankDropdown 
            key={key}
            label={label}
            value={displayValue}
            onChange={(value) => handleInputChange(key, value)}
            containerStyle="mb-4 space-y-2"
            labelStyle="text-xl"
          />
        );
      }
      
      if (key === 'gender') {
        return (
          <div key={key} className="w-full mb-4">
            <label className="block text-[#4e3a02] text-xl font-bold mb-4">
              {label}
            </label>
            <GenderSelector 
              gender={gender} 
              setGender={setGender} 
            />
          </div>
        );
      }

      if (key === 'tel' || key === 'seller.businessTel' || key === 'birth') {
        return (
          <Input 
            key={key}
            label={label}
            name={key}
            value={displayValue}
            onChange={(name, value) => handleInputChange(name, value.replace(/[^0-9]/g, ''))}
            onFocus={handleInputFocus}
            onBlur={handleInputBlur}
            containerStyle="mb-4 space-y-2"
            labelStyle="text-xl"
            maxLength={key === 'birth' ? 10 : 13}
          />
        );
      }
      
      return (
        <Input 
          key={key}
          label={label}
          name={key}
          value={displayValue}
          onChange={handleInputChange}
          onFocus={handleInputFocus}
          onBlur={handleInputBlur}
          containerStyle="mb-4 space-y-2"
          labelStyle="text-xl"
        />
      );
    }
    
    return (
      <InfoBox 
        key={key}
        label={label}
        value={displayValue}
      />
    );
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <div className="flex items-center mb-12">
            <h1 className="text-[40px] font-bold">{userData.nickname}님 프로필</h1>
            
            {/* 프로필 이미지 */}
            <div className="w-[200px] h-[200px] ml-16">
              <img 
                src={profileImageFromToken || "../assets/profile_img.png"}
                alt="profile"
                className="rounded-full w-full h-full object-cover shadow-lg border-2 border-[#b5912d]"
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = "../assets/profile_img.png";
                }}
              />
            </div>
          </div>

          <div className="max-w-[700px] space-y-6">
            {personalInputs.map(({ key, label }) => renderInput(key, label))}
            
            {!userData?.seller && (
              <button 
                className="w-[180px] h-12 bg-[#FFECB4] text-[#4E3A02] text-xl rounded-full font-bold shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] hover:bg-[#f0e3c2] transition-colors"
                onClick={() => navigate('/seller-registration')}
              >
                판매자 등록하기
              </button>
            )}

            {userData?.seller && (
              <>
                {businessInputs.map(({ key, label }) => renderInput(key, label))}
              </>
            )}

            <button 
              className="w-[120px] h-12 bg-[#FFECB4] text-[#4E3A02] text-xl rounded-full font-bold shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] float-right hover:bg-[#f0e3c2] transition-colors"
              onClick={handleEdit}
            >
              {isEditing ? '수정완료' : '수정하기'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPage;