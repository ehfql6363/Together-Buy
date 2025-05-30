import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Input from '../../components/inputs/Input';
import PhoneInput from '../../components/inputs/PhoneInput';
import AddressSearch from '../../components/common/AddressSearch';
import { useAuth } from '../../contexts/AuthContext';

const EMAIL_DOMAINS = [
  'gmail.com',
  'naver.com', 
  'daum.net', 
  'kakao.com', 
  'nate.com',
  '직접 입력'
];

const SellerRegistrationPage2 = () => {
  const navigate = useNavigate();
  const { makeRequest } = useAuth();
  const [formData, setFormData] = useState({
    businessName: '',
    bossName: '',
    businessTel: '',
    emailId: '',
    emailDomain: '',
    customEmailDomain: '',
    businessAddress: '',
    businessNumber: ''
  });

  const handleChange = (name, value) => {
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const validateForm = () => {
    const requiredFields = [
      { name: 'businessName', label: '상호' },
      { name: 'bossName', label: '대표자' },
      { name: 'businessTel', label: '연락처' },
      { name: 'businessAddress', label: '사업장 소재' },
      { name: 'businessNumber', label: '사업자 번호' }
    ];
  
    for (let field of requiredFields) {
      if (!formData[field.name]) {
        window.alert(`${field.label}을(를) 입력해주세요.`);
        return false;
      }
    }
  
    // 연락처 유효성 검사 (지역번호-000-0000 형식)
    const phoneRegex = /^(02|0[3-7][0-9]|01[0-9]|080|070)-\d{3,4}-\d{4}$/;
    if (!phoneRegex.test(formData.businessTel)) {
      window.alert('연락처는 지역번호-000-0000 형식으로 입력해주세요.\n(예: 02-123-4567, 031-1234-5678)');
      return false;
    }
  
    // 이메일 아이디 유효성 검사
    const emailIdRegex = /^[a-zA-Z0-9._-]{3,30}$/;
    if (!formData.emailId) {
      window.alert('이메일 아이디를 입력해주세요.');
      return false;
    }
    if (!emailIdRegex.test(formData.emailId)) {
      window.alert('3-30자의 영문, 숫자, 특수문자(_.-) 사용 가능합니다.');
      return false;
    }
  
    let actualDomain = formData.emailDomain === '직접 입력' 
      ? formData.customEmailDomain 
      : formData.emailDomain;
  
    if (!actualDomain) {
      window.alert('이메일 도메인을 선택 또는 입력해주세요.');
      return false;
    }
  
    const domainRegex = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!domainRegex.test(actualDomain)) {
      window.alert('올바른 도메인 형식이 아닙니다.');
      return false;
    }
  
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    const actualDomain = formData.emailDomain === '직접 입력' 
      ? formData.customEmailDomain 
      : formData.emailDomain;
      
      try {
        
        const form = new FormData();
        
        // API로 전송할 데이터 준비
        const submitData = {
          businessName: formData.businessName,
          bossName: formData.bossName,
          businessTel: formData.businessTel,
          businessEmail: `${formData.emailId}@${actualDomain}`,
          businessAddress: formData.businessAddress,
          businessNumber: Number(formData.businessNumber)
        };

        form.append("request", new Blob([JSON.stringify(submitData)], { type: "application/json" }));
        
        // API 요청 (Content-Type을 application/json으로 설정)
        const response = await makeRequest("POST", "api/seller", form, {
          headers: { "Content-Type": "multipart/form-data" },
       });

        console.log(response)

        if (!response.status === 200) {
          throw new Error('API 요청이 실패했습니다.');
        }
        
        // 응답 헤더에서 토큰 추출 및 저장
        const newToken = response.headers.get('Authorization');
        if (newToken) {
          localStorage.setItem('token', newToken);
        }
        
        window.alert('판매자 정보가 성공적으로 등록되었습니다.');
        navigate('/');
        
      } catch (error) {
        console.error('API 에러:', error);
        window.alert('판매자 정보 등록 중 오류가 발생했습니다.');
      }
    };

    const otherFormFields = [
      { label: '상호', name: 'businessName' },
      { label: '대표자', name: 'bossName' },
      { 
        label: '연락처', 
        name: 'businessTel',
        isPhone: true
      },
      { label: '사업장 소재', name: 'businessAddress', isAddress: true },
      { label: '사업자 번호', name: 'businessNumber' }
    ];

  return (
    <div className="w-full min-h-screen bg-[#faf7f0]">
      <main className="w-full pt-2 px-8 pb-24 mt-[60px]">
        <div className="max-w-7xl mx-auto">
          <h1 className="text-[#4e3a01] text-[40px] font-bold font-['Plus Jakarta Sans'] mb-12">판매자 정보 입력</h1>
        
          <form onSubmit={handleSubmit} className="space-y-8 mb-24">
            {otherFormFields.map((field) => (
              <div key={field.name} className="h-24">
                {field.isPhone ? (
                  <PhoneInput
                    value={formData[field.name]}
                    onChange={(name, value) => handleChange(name, value)}
                    label={field.label}
                    name={field.name}
                  />
                ) : field.isAddress ? (
                  <AddressSearch
                    onAddressSelect={(address) => handleChange(field.name, address)}
                    onFocus={() => {}}
                    onBlur={() => {}}
                  />
                ) : (
                  <Input
                    type="text"
                    name={field.name}
                    value={formData[field.name]}
                    onChange={handleChange}
                    placeholder={field.placeholder || field.label}
                    label={field.label}
                  />
                )}
              </div>
            ))}

            <div className="h-24">
              <div className="flex space-x-4 items-end">
                <div className="flex-grow">
                  <Input
                    type="text"
                    name="emailId"
                    label='E - Mail'
                    value={formData.emailId}
                    onChange={handleChange}
                    placeholder="이메일 아이디"
                  />
                </div>
                <div className="w-8 text-4xl fond-bold pb-3">@</div>
                <div className="flex-grow text-[#4e3a01] text-lg font-bold font-['Plus Jakarta Sans']">
                  {formData.emailDomain === '직접 입력' ? (
                    <Input
                      type="text"
                      name="customEmailDomain"
                      value={formData.customEmailDomain}
                      onChange={handleChange}
                      placeholder="도메인 입력"
                    />
                  ) : (
                    <div className="relative w-full">
                      <select
                        value={formData.emailDomain}
                        onChange={(e) => handleChange('emailDomain', e.target.value)}
                        className="w-full h-14 px-3 border border-[#b5912d] rounded-[10px] appearance-none bg-white focus:outline-none focus:ring-2 focus:ring-[#b5912d] text-[#4e3a02] shadow-[inset_0_2px_4px_rgba(0,0,0,0.1)]"
                      >
                        <option value="">도메인 선택</option>
                        {EMAIL_DOMAINS.map((domain) => (
                          <option key={domain} value={domain}>
                            {domain}
                          </option>
                        ))}
                      </select>
                      <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-3 text-[#4e3a02]">
                        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                          <path fillRule="evenodd" d="M10 3a1 1 0 01.707.293l3 3a1 1 0 01-1.414 1.414L10 5.414 7.707 7.707a1 1 0 01-1.414-1.414l3-3A1 1 0 0110 3zm-3.707 9.293a1 1 0 011.414 0L10 14.586l2.293-2.293a1 1 0 011.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z" clipRule="evenodd" />
                        </svg>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            </div>

            <div className="flex justify-center mt-8 mb-16">
              <button 
                type="submit"
                className="w-48 h-14 px-8 py-2 bg-[#ffecb4] rounded-[20px] shadow-md border border-[#b5912d] text-[#4e3a02] text-xl font-bold"
              >
                등록하기
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default SellerRegistrationPage2;