import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Input from '../../components/inputs/Input';
import Button from '../../components/buttons/Button';
import { useAuth } from '../../contexts/AuthContext';

const SellerRegistrationPage1 = () => {
  const navigate = useNavigate();
  const { makeRequest, decodeToken } = useAuth();
  const [verificationCode, setVerificationCode] = useState('');
  const [isVerified, setIsVerified] = useState(false);

  const getEmailFromToken = () => {
    const token = localStorage.getItem('token');
    if (!token) return '';
    
    const decoded = decodeToken(token);
    return decoded?.email.trim() || '';
  };
  
  // 이메일 상태 초기화할 때 직접 디코딩하여 설정
  const [email, setEmail] = useState(getEmailFromToken());

  const handleSendVerificationCode = async () => {
    try {
      const response = await makeRequest('POST', 'api/seller/emails/verify');
      console.log('인증번호 발송 성공:', response);
      alert('인증번호가 발송되었습니다.');
    } catch (error) {
      console.error('인증번호 발송 실패:', error);
      alert('인증번호 발송에 실패했습니다. 다시 시도해주세요.');
    }
  };

  const handleVerify = async () => {
    try {
      const response = await makeRequest(
        'POST', 
        'api/seller/emails/verify/confirm',
        {
          verificationCode: verificationCode
        }
      ); 
      if (response.data.isVerified){
        console.log('인증 성공:', response);
        alert('이메일 인증이 완료되었습니다.');
        setIsVerified(true); // 인증 성공 시 상태 업데이트
      } else {
        alert('인증에 실패했습니다. 다시 시도해주세요.');
        setIsVerified(false); // 인증 실패 시 상태 업데이트
      }
    } catch (error) {
      console.error('인증 실패:', error);
      alert('인증에 실패했습니다. 다시 시도해주세요.');
      setIsVerified(false); // 인증 실패 시 상태 업데이트
    }
  };

  const handleConfirmAndContinue = () => {
    if (isVerified) {
      navigate('/seller-registration2', { 
        state: { 
          email: email 
        } 
      });
    } else {
      alert('이메일 인증이 필요합니다.');
    }
  };

  return (
    <div className="w-full min-h-screen bg-[#faf7f0]"> 
      <main className="w-full pt-2 px-8 pb-24 mt-[60px]">
        <div className="max-w-6xl mx-auto px-4 mt-6">
            <h1 className="text-[#4e3a01] text-[40px] font-bold font-['Plus Jakarta Sans'] mb-16">이메일 인증</h1>
            
            <div className="space-y-4">
              <div className="flex space-x-20 items-end">
                  <Input 
                    type="email"
                    name="email" 
                    label="이메일"
                    placeholder="example@email.com" 
                    value={email}
                    onChange={(name, value) => {/* readonly */}}
                    className="w-[400px] p-2 border rounded"
                    disabled={true}
                  />
                  <Button 
                    onClick={handleSendVerificationCode}
                    className="bg-[#ffecb4] text-#4e3a02 hover:bg-[#E8D5A7] px-8 w-[300px] text-base"
                  >
                    인증번호 발송
                  </Button>
              </div>
              
              <div className="flex space-x-20 items-end">
                  <Input 
                    type="text"
                    name="verificationCode" 
                    label="인증번호"
                    placeholder="인증번호 6자리" 
                    value={verificationCode}
                    onChange={(name, value) => setVerificationCode(value)}
                    className="w-[400px] p-2 border rounded"
                    maxLength={6}
                  />
                  <Button 
                    onClick={handleVerify}
                    className="bg-[#ffecb4] text-#4e3a02 hover:bg-[#E8D5A7] px-8 w-[300px] text-base"
                  >
                    인증
                  </Button>
              </div>
            </div>
            <div className="flex justify-center">
              <Button 
                onClick={handleConfirmAndContinue}
                className="w-[300px] mt-16 bg-[#ffecb4] text-#4e3a02 hover:bg-[#E8D5A7]"
              >
                확인
              </Button>
            </div>
        </div>
      </main>
    </div>
  );
};

export default SellerRegistrationPage1;