import React, { useState, useRef, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import { UserCircle2 } from 'lucide-react';
import Input from '../../components/inputs/Input';
import GenderSelector from '../../components/selectors/GenderSelector';
import BankDropdown from '../../components/dropdowns/BankDropDown';
import AddressSearch from '../../components/common/AddressSearch';

const SignUpPage = () => {
  const navigate = useNavigate();
  const fileInputRef = useRef(null);
  const { makeRequest, storeToken } = useAuth();
  
  // 폼 데이터 상태
  const [formData, setFormData] = useState({
    name: '',
    nickname: '',
    tel: '',
    birth: '',
    bankName: '',
    bankNumber: '',
    address: '',
    gender: '',
    image: null
  });

  // 에러 상태
  const [errors, setErrors] = useState({
    name: '',
    nickname: '',
    tel: '',
    birth: '',
    bankName: '',
    bankNumber: '',
    address: '',
    gender: '',
    image: ''
  });

  // 입력 상태
  const [inputStatus, setInputStatus] = useState({
    name: 'default',
    nickname: 'default',
    tel: 'default',
    birth: 'default',
    bankNumber: 'default',
    address: 'default',
  });

  // 프로필 이미지 미리보기
  const [previewUrl, setPreviewUrl] = useState(null);

  // 필드 검증 함수
  const validateField = useCallback((name, value) => {
    const stringValue = typeof value === 'string' ? value : '';

    switch (name) {
      case 'name':
        return stringValue.trim().length >= 2 
          ? '' 
          : '이름은 2자 이상이어야 합니다.';
      
      case 'nickname':
        return stringValue.trim().length >= 2 
          ? '' 
          : '닉네임은 2자 이상이어야 합니다.';
      
      case 'tel':
        const phoneRegex = /^010-\d{4}-\d{4}$/;
        return phoneRegex.test(stringValue) 
          ? '' 
          : '핸드폰 번호 형식이 올바르지 않습니다. (010-0000-0000)';
      
      case 'birth':
        const birthdateRegex = /^\d{4}-\d{2}-\d{2}$/;
        if (!birthdateRegex.test(stringValue)) {
          return '생년월일 형식이 올바르지 않습니다. (YYYY-MM-DD)';
        }
        
        const inputDate = new Date(stringValue);
        const currentDate = new Date();
        const minDate = new Date(currentDate.getFullYear() - 100, currentDate.getMonth(), currentDate.getDate());
        const maxDate = new Date(currentDate.getFullYear() - 14, currentDate.getMonth(), currentDate.getDate());
        
        return (inputDate >= minDate && inputDate <= maxDate) 
          ? '' 
          : '유효한 생년월일을 입력해주세요.';
      
      case 'bankName':
        return stringValue ? '' : '은행을 선택해주세요.';
      
      case 'bankNumber':
        const accountRegex = /^\d{10,14}$/;
        return accountRegex.test(stringValue) 
          ? '' 
          : '계좌번호는 10-14자리 숫자여야 합니다.';
      
      case 'address':
        return stringValue.trim() ? '' : '주소를 입력해주세요.';
      
      case 'gender':
        return (value === 'MALE' || value === 'FEMALE') 
          ? '' 
          : '성별을 선택해주세요.';
      
      case 'image':
        return value instanceof File ? '' : '프로필 이미지를 등록해주세요.';
      
      default:
        return '';
    }
  }, []);
  
    const formatPhoneNumber = (value) => {
      // Remove all non-digits
      const number = value.replace(/[^\d]/g, '');
      
      // Format based on length
      if (number.length <= 3) return number;
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
      // If longer than 11 digits, truncate
      return `${number.slice(0, 3)}-${number.slice(3, 7)}-${number.slice(7, 11)}`;
    };

    const formatBirthDate = (value) => {
      // Remove all non-digits
      const date = value.replace(/[^\d]/g, '');
      
      // Format based on length
      if (date.length <= 4) return date;
      if (date.length <= 6) {
        return `${date.slice(0, 4)}-${date.slice(4)}`;
      }
      if (date.length <= 8) {
        return `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6)}`;
      }
      // If longer than 8 digits, truncate
      return `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6, 8)}`;
    };

    // 입력 변경 핸들러
  const handleChange = useCallback((name, value) => {
    let formattedValue = value;
  
    // Format phone numbers
    if (name === 'tel') {
      formattedValue = formatPhoneNumber(value.replace(/[^0-9]/g, ''));
    }
    
    // Format birth date
    if (name === 'birth') {
      formattedValue = formatBirthDate(value.replace(/[^0-9]/g, ''));
    }
  
    setFormData(prev => ({
      ...prev,
      [name]: formattedValue
    }));
  
    // 성별 선택 시 특별한 처리
    const errorMessage = name === 'gender' 
      ? (value === 'MALE' || value === 'FEMALE' 
        ? '' 
        : '성별을 선택해주세요.')
      : validateField(name, formattedValue);
  
    setErrors(prev => ({
      ...prev,
      [name]: errorMessage
    }));

    // 성별일 경우 강제로 'filled' 상태로 설정
    setInputStatus(prev => ({
      ...prev,
      [name]: name === 'gender' ? 'filled' : (value ? 'filled' : 'default')
    }));
  }, [validateField]);

  // 이미지 변경 핸들러
  const handleImageChange = (event) => {
    const file = event.target.files?.[0];
    if (file) {
      if (file.size > 5 * 1024 * 1024) {
        alert('이미지 크기는 5MB를 초과할 수 없습니다.');
        return;
      }

      if (!file.type.startsWith('image/')) {
        alert('이미지 파일만 업로드 가능합니다.');
        return;
      }

      handleChange('image', file);
      
      const url = URL.createObjectURL(file);
      setPreviewUrl(url);
    }
  };

  // 폼 전체 검증
  const validateForm = useCallback(() => {
    const requiredFields = [
      'name', 
      'nickname', 
      'tel', 
      'birth', 
      'bankName', 
      'bankNumber', 
      'address', 
      'gender', 
      'image'
    ];
  
    for (const key of requiredFields) {
      const value = formData[key];
      const errorMessage = validateField(key, value);
      
      if (errorMessage) {
        alert(errorMessage);
        return false;
      }
    }
  
    return true;
  }, [formData, validateField]);
  
  const handleSubmit = useCallback(async () => {
    if (validateForm()) {
      try {
        const form = new FormData();
        
        // 성별 값을 0/1로 변환
        const genderValue = formData.gender === 'MALE' ? 0 : 1;

        // API로 전송할 데이터 객체 생성
        const signupData = {
          name: formData.name,
          nickname: formData.nickname,
          tel: formData.tel,
          birth: formData.birth,
          gender: genderValue,
          address: formData.address,
          bankName: formData.bankName,
          bankNumber: formData.bankNumber,
          // image: base64Image
        };

        // FormData 객체 랩핑핑
        form.append("request", new Blob([JSON.stringify(signupData)], { type: "application/json" }));

        // 이미지 파일이 있을 경우 FormData에 추가
        if(formData.image) {
          form.append("file", formData.image);
        }
  
        // AuthContext의 makeRequest를 사용하여 API 요청
        await makeRequest("POST", "api/member/signup", form, {
            headers: { "Content-Type": "multipart/form-data" },
        });

        const cookies = document.cookie.split(';').reduce((acc, cookie) => {
          const [name, value] = cookie.trim().split('=');
          acc[name] = value;
          return acc;
        }, {});

        const accessToken = cookies['Authorization'];
        const refreshToken = cookies['Refresh-Token'];

        if (accessToken && refreshToken) {
          storeToken(accessToken, refreshToken);
        }

        alert('회원가입이 완료되었습니다.');
        navigate('/');
      } catch (error) {
        console.error('회원가입 에러:', error);
        alert(error.response?.data?.message || error.message || '회원가입 중 오류가 발생했습니다.');
      }
    }
  }, [validateForm, navigate, formData, makeRequest]);

  const handleInputFocus = (name) => {
    setInputStatus(prev => ({ ...prev, [name]: 'focused' }));
  };
  
  const handleInputBlur = (name) => {
    const errorMessage = validateField(name, formData[name]);
    setErrors(prev => ({
      ...prev,
      [name]: errorMessage
    }));

    setInputStatus(prev => ({ 
      ...prev, 
      [name]: formData[name] ? 'filled' : 'default' 
    }));
  };

  const handleImageClick = () => {
    fileInputRef.current?.click();
  };

  return (
    <div className="min-h-screen bg-[#FAF7F0]">
      <main className="container mx-auto px-4 sm:px-6 lg:px-8 max-w-[1440px]">
        <div className="pt-[60px] pb-8">
          <h1 className="text-[#4E3A02] text-4xl font-bold font-['Plus Jakarta Sans'] mb-12">
            회원 정보 입력
          </h1>

          <div className="flex flex-col lg:flex-row lg:gap-16">
            {/* Left Column - Form Fields */}
            <div className="flex-1 space-y-8">
              <Input
                label="이름"
                name="name"
                value={formData.name}
                onChange={handleChange}
                onFocus={handleInputFocus}
                onBlur={handleInputBlur}
                status={inputStatus.name}
                placeholder="이름"
                containerStyle="w-full"
              />

              <Input
                label="닉네임"
                name="nickname"
                value={formData.nickname}
                onChange={handleChange}
                onFocus={handleInputFocus}
                onBlur={handleInputBlur}
                status={inputStatus.nickname}
                placeholder="닉네임"
                containerStyle="w-full"
              />

              <Input
                label="핸드폰 번호"
                name="tel"
                value={formData.tel}
                onChange={(name, value) => handleChange(name, value.replace(/[^0-9]/g, ''))}
                onFocus={handleInputFocus}
                onBlur={handleInputBlur}
                status={inputStatus.tel}
                placeholder="핸드폰 번호(010-0000-0000)"
                containerStyle="w-full"
                maxLength={13}
              />

              <div className="flex flex-col sm:flex-row gap-4 sm:items-end">
                <Input
                  label="생년월일"
                  name="birth"
                  value={formData.birth}
                  onChange={(name, value) => handleChange(name, value.replace(/[^0-9]/g, ''))}
                  onFocus={handleInputFocus}
                  onBlur={handleInputBlur}
                  status={inputStatus.birth}
                  placeholder="생년월일(2000-01-01)"
                  containerStyle="flex-1"
                  maxLength={10}
                />
                <GenderSelector
                  gender={formData.gender}
                  setGender={(gender) => {
                    handleChange('gender', gender);
                    handleInputBlur('gender');
                  }}
                />
              </div>

              <div className="flex flex-col sm:flex-row gap-4 sm:items-end">
                <div className="flex flex-col gap-2">
                  <label className="text-[#4E3A01] text-2xl font-bold font-['Plus Jakarta Sans']">
                    은행
                  </label>
                  <BankDropdown
                    value={formData.bankName}
                    onChange={(value) => {
                      handleChange('bankName', value);
                      handleInputBlur('bankName');
                    }}
                  />
                </div>
                <Input
                  label="계좌번호"
                  name="bankNumber"
                  value={formData.bankNumber}
                  onChange={handleChange}
                  onFocus={handleInputFocus}
                  onBlur={handleInputBlur}
                  status={inputStatus.bankNumber}
                  placeholder="계좌번호"
                  containerStyle="flex-1"
                />
              </div>

              <AddressSearch
                onAddressSelect={(value) => {
                  handleChange('address', value);
                  handleInputBlur('address');
                }}
                status={inputStatus.address}
                onFocus={() => handleInputFocus('address')}
                onBlur={() => handleInputBlur('address')}
              />
            </div>

            {/* Right Column - Profile Image */}
            <div className="lg:w-80 mt-8 lg:mt-0 flex flex-col items-center">
              <div 
                onClick={handleImageClick}
                className="w-60 h-60 bg-white rounded-full shadow-md overflow-hidden cursor-pointer relative hover:opacity-90 transition-opacity duration-200"
              >
                {previewUrl ? (
                  <img 
                    src={previewUrl} 
                    alt="Profile preview" 
                    className="w-full h-full object-cover"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center bg-gray-50">
                    <UserCircle2 size={120} className="text-gray-300" />
                  </div>
                )}
              </div>
              <input
                ref={fileInputRef}
                type="file"
                accept="image/*"
                onChange={handleImageChange}
                className="hidden"
              />
              <button 
                onClick={handleImageClick}
                className="mt-4 w-[200px] h-[60px] bg-[#FFECB4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#B5912D] text-[#4E3A02] text-xl font-bold hover:bg-[#f0d69a] transition-colors duration-200"
              >
                이미지 등록
              </button>
            </div>
          </div>

          {/* Submit Button */}
          <div className="flex justify-center mt-16">
            <button 
              onClick={handleSubmit}
              className="w-[200px] h-[60px] bg-[#FFECB4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#B5912D] text-[#4E3A02] text-xl font-bold hover:bg-[#f0d69a] transition-colors duration-200"
            >
              가입하기
            </button>
          </div>
        </div>
      </main>
    </div>
  );
};

export default SignUpPage;