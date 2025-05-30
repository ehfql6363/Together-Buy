import React, { useState, useRef, useEffect } from 'react';
import { Image as ImageIcon } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import Input from '../../components/inputs/Input';
import Button from '../../components/buttons/Button';
import CategoryDropdown from '../../components/dropdowns/CategoryDropDown';
import DiscountUnitToggle from '../../components/common/DiscountUnitToggle';
import ProductPreviewModal from '../../components/modals/ProductPreviewModal';

const ProductRegistrationPage = () => {
  const navigate = useNavigate();
  const productImagesRef = useRef(null);
  const productDetailImageRef = useRef(null);
  const [isSeller, setIsSeller] = useState(false);
  const { makeRequest, decodeToken } = useAuth();
  const [isAuthorized, setIsAuthorized] = useState(true);

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

  const [formData, setFormData] = useState({
    productName: '',
    price: '',
    discountAmount: '',
    discountUnit: '원',
    salePrice: '',
    total: '',
    unitAmount: '',
    unitName: '개',
    pricePerUnit: '',
  });

  const [previews, setPreviews] = useState({
    productImages: null,
    productDetailImage: null
  });

  const [categoryData, setCategoryData] = useState({
    categories: [],           // 메인 카테고리 목록
    subCategories: {}         // { categoryId: [subCategories] } 형태
  });
  
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);

  const [categories, setCategories] = useState({
    categoryId: null,  // null로 변경
    subCategoryId: null  // null로 변경
  });

  // 카테고리 데이터 가져오기
  const fetchCategories = async () => {
    try {
      const response = await makeRequest("GET", "api/categories");
      
      // 메인 카테고리 추출
      const mainCategories = response.data.map(category => ({
        id: category.categoryId,
        name: category.categoryName
      }));
  
      // 서브 카테고리 객체 생성
      const subCategories = response.data.reduce((acc, category) => {
        acc[category.categoryId] = category.subCategories.map(sub => ({
          id: sub.subCategoryId,
          name: sub.subCategoryName
        }));
        return acc;
      }, {});
  
      setCategoryData({
        categories: mainCategories,
        subCategories: subCategories
      });
    } catch (error) {
      console.error('카테고리 정보 로드 실패:', error);
    }
  };
  
  useEffect(() => {
    fetchCategories();
  }, []);

  // Input 이벤트 핸들러들
  const handleInputChange = (name, value) => {
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // 카테고리 변경 핸들러
  const handleCategoryChange = (field) => (value) => {
    if (field === 'categoryId') {
      // 메인 카테고리 선택 시
      setSelectedCategoryId(value);
      // 카테고리 ID 업데이트 및 서브카테고리 초기화
      setCategories(prev => ({
        ...prev,
        categoryId: value,
        subCategoryId: null  // null로 초기화
      }));
    } else {
      // 서브카테고리 선택 시
      setCategories(prev => ({
        ...prev,
        [field]: value
      }));
    }
    
    // formData도 함께 업데이트
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
    
    // formData도 함께 업데이트
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const validateImage = (file) => {
    if (!file) return false;
    
    if (file.size > 5 * 1024 * 1024) {
      alert('이미지 크기는 5MB를 초과할 수 없습니다.');
      return false;
    }

    if (!file.type.startsWith('image/')) {
      alert('이미지 파일만 업로드 가능합니다.');
      return false;
    }

    return true;
  };

  const handleImageUpload = (type) => (event) => {
    const file = event.target.files?.[0];
    
    if (file && validateImage(file)) {
      setFormData(prev => ({
        ...prev,
        [type]: file
      }));
      
      const imageUrl = URL.createObjectURL(file);
      setPreviews(prev => ({
        ...prev,
        [type]: imageUrl
      }));
    }
  };

  const handleImageClick = (type) => {
    if (type === 'productImages') {
      productImagesRef.current?.click();
    } else {
      productDetailImageRef.current?.click();
    }
  };

  const handleCancel = () => {
    if (window.confirm('상품 등록을 취소하시겠습니까?')) {
      navigate('/product-management');
    }
  };

  const validateForm = () => {
    const validations = [
      {
        field: 'productName',
        value: formData.productName,
        message: '상품명을 입력해주세요.',
        validate: value => value && value.trim() !== ''
      },
      {
        field: 'categoryId',
        value: formData.categoryId,
        message: '카테고리를 선택해주세요.',
        validate: value => value !== null
      },
      {
        field: 'subCategoryId',
        value: formData.subCategoryId,
        message: '상세카테고리를를 선택해주세요.',
        validate: value => value !== null
      },
      {
        field: 'price',
        value: formData.price,
        message: '상품 가격을 입력해주세요.',
        validate: value => !isNaN(value) && parseInt(value) > 0
      },
      {
        field: 'total',
        value: formData.total,
        message: '총 수량을 입력해주세요.',
        validate: value => !isNaN(value) && parseInt(value) > 0
      },
      {
        field: 'unitAmount',
        value: formData.unitAmount,
        message: '최소 단위를 입력해주세요.',
        validate: value => !isNaN(value) && parseInt(value) > 0
      },
      {
        field: 'pricePerUnit',
        value: formData.pricePerUnit,
        message: '단위 당 가격이 계산되지 않았습니다.',
        validate: value => {
          const numValue = value.replace(/,/g, '');
          return value !== '' && !isNaN(numValue) && parseInt(numValue) >= 0;
        }
      }
    ];
  
    // 2. 각 필드 검증
    for (const validation of validations) {
      if (!validation.validate(validation.value)) {
        alert(validation.message);
        return false;
      }
    }

    const productImagesFile = productImagesRef.current?.files[0];
    const productDetailImageFile = productDetailImageRef.current?.files[0];
    
    if (!productImagesFile || !productDetailImageFile) {
      alert('상품 이미지와 상세 이미지를 모두 등록해주세요.');
      return false;
    }

    return true;
  };

  useEffect(() => {
    if (formData.salePrice && formData.total) {
      const salePrice = Number(formData.salePrice.replace(/,/g, ''));
      const total = Number(formData.total);
      const unitAmount = Number(formData.unitAmount);
      
      if (total > 0) {
        // 단위 당 가격 계산 (10원 단위 내림)
        const pricePerUnit = Math.floor((salePrice * unitAmount / total) / 10) * 10;
        
        // 천단위 구분자 적용
        const formattedPrice = pricePerUnit.toLocaleString();
  
        setFormData(prev => ({
          ...prev,
          pricePerUnit: formattedPrice
        }));
      }
    } else {
      setFormData(prev => ({
        ...prev,
        pricePerUnit: ''
      }));
    }
  }, [formData.salePrice, formData.total, formData.unitAmount]);

  useEffect(() => {
    if (formData.price && formData.discountAmount) {
      const price = Number(formData.price);
      const discount = Number(formData.discountAmount);
      
      let salePrice;
      if (formData.discountUnit === '%') {
        // 할인율인 경우
        salePrice = price - (price * (discount / 100));
      } else {
        // 할인 금액인 경우
        salePrice = price - discount;
      }
    
      // 10원 단위로 내림 처리
      salePrice = Math.floor(salePrice / 10) * 10;
      
      // 0원 미만으로 내려가지 않도록 처리
      salePrice = Math.max(0, salePrice);
      
      // 천단위 구분자 적용
      const formattedPrice = salePrice.toLocaleString();
  
      setFormData(prev => ({
        ...prev,
        salePrice: formattedPrice.toString()
      }));
    } else {
      // 가격이나 할인 금액이 없는 경우 원래 가격을 그대로 표시
      setFormData(prev => ({
        ...prev,
        salePrice: formData.price || ''
      }));
    }
  }, [formData.price, formData.discountAmount, formData.discountUnit]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (!validateForm()) {
        return;
      }
  
      // 숫자 형식의 데이터에서 콤마 제거
      const cleanedData = {
        ...formData,
        // ...categories,
        price: formData.price.replace(/,/g, ''),
        salePrice: formData.salePrice.replace(/,/g, ''),
        pricePerUnit: formData.pricePerUnit.replace(/,/g, '')
      };
  
      // 이미지를 제외한 데이터를 request 객체로 구성
      const registerData = {
        productName: cleanedData.productName,
        categoryId: Number(categories.categoryId),
        subCategoryId: Number(categories.subCategoryId),
        price: Number(cleanedData.price),
        discountAmount: Number(cleanedData.discountAmount),
        discountUnit: cleanedData.discountUnit,
        salePrice: Number(cleanedData.salePrice),
        total: Number(cleanedData.total),
        unitAmount: Number(cleanedData.unitAmount),
        unitName: cleanedData.unitName,
        pricePerUnit: Number(cleanedData.pricePerUnit)
      };
      console.log('Sending discount unit:', registerData.discountUnit);
      // FormData 생성
      const formDataToSubmit = new FormData();
      
      // registerData 객체를 JSON 문자열로 변환하여 추가
      formDataToSubmit.append("request", new Blob([JSON.stringify(registerData)], { 
        type: "application/json" 
      }));
      
      // 이미지 파일 추가
      if (formData.productImages) {
        formDataToSubmit.append('productImages', formData.productImages);
      }
      
      if (formData.productDetailImage) {
        formDataToSubmit.append('productDetailImage', formData.productDetailImage);
      }
      
      // API 요청 보내기
      const response = await makeRequest("POST", "api/seller/product", formDataToSubmit);
      
      if (response) {
        alert('상품이 등록되었습니다.');
        navigate('/product-management');
      }
    } catch (error) {
      console.error('상품 등록 실패:', error);
      alert('상품 등록에 실패했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <h1 className="text-black text-[40px] font-bold font-['Plus Jakarta Sans'] leading-[56px] mb-6">상품 등록</h1>
          
          <div className="mt-[60px] max-w-5xl">
            <div className="bg-amber-50 p-8 rounded-3xl shadow-md space-y-12 border border-amber-100">
              <div className="space-y-4">
                <div className="flex gap-4 items-end gap-8">
                  <Input 
                    label="상품명"
                    name="productName"
                    placeholder="상품명" 
                    width="w-[500px]"
                    value={formData.productName}
                    onChange={handleInputChange}
                  />
                  <div className="relative z-10">
                    <CategoryDropdown 
                      width="w-[200px]"
                      options={categoryData.categories}
                      value={categories.categoryId}
                      onChange={handleCategoryChange('categoryId')}
                    />
                  </div>
                  <div className="relative z-10">
                    <CategoryDropdown 
                      width="w-[200px]"
                      options={selectedCategoryId ? categoryData.subCategories[selectedCategoryId] : []}
                      value={categories.subCategoryId}
                      onChange={handleCategoryChange('subCategoryId')}
                      disabled={!selectedCategoryId}
                    />
                  </div>
                </div>
              </div>

              <div className="flex items-end gap-8">
                <div className="w-[350px]">
                  <Input 
                    label="상품 가격"
                    name="price"
                    placeholder="상품 가격" 
                    type="text"
                    width="w-full"
                    value={formData.price}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="flex items-end gap-2">
                  <div className="w-[350px]">
                    <Input 
                      label="할인"
                      name="discountAmount"
                      placeholder="할인" 
                      type="text"
                      width="w-full"
                      value={formData.discountAmount}
                      onChange={handleInputChange}
                    />
                  </div>
                  <DiscountUnitToggle 
                    value={formData.discountUnit}
                    onChange={(value) => setFormData(prev => ({ ...prev, discountUnit: value }))}
                  />
                </div>
                <div className="w-[350px]">
                  <Input 
                    label="할인 후 가격"
                    name="salePrice"
                    placeholder="할인 후 가격" 
                    readOnly
                    width="w-full"
                    value={formData.salePrice}
                    readonly
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="grid grid-cols-3 gap-12">
                <div>
                  <Input 
                    label="총 수량"
                    name="total"
                    placeholder="총 수량" 
                    type="text"
                    style={{ width: '100%' }}
                    value={formData.total}
                    onChange={handleInputChange}
                  />
                </div>
                <div>
                  <Input 
                    label="최소 단위"
                    name="unitAmount"
                    placeholder="최소 단위" 
                    type="text"
                    style={{ width: '100%' }}
                    value={formData.unitAmount}
                    onChange={handleInputChange}
                  />
                </div>
                <div>
                  <Input 
                    label="단위 당 가격"
                    name="pricePerUnit"
                    placeholder="단위 당 가격" 
                    type="text"
                    style={{ width: '100%' }}
                    value={formData.pricePerUnit}
                    readOnly
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="space-y-12">
                <div className="flex flex-col items-start space-y-8">
                  <div 
                    onClick={() => handleImageClick('productImages')}
                    className="w-32 h-32 border-2 border-dashed border-gray-300 rounded-lg overflow-hidden cursor-pointer hover:border-gray-400 transition-colors duration-200"
                  >
                    {previews.productImages ? (
                      <img 
                        src={previews.productImages} 
                        alt="Product Preview" 
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <div className="w-full h-full flex items-center justify-center bg-gray-50">
                        <ImageIcon size={48} className="text-gray-300" />
                      </div>
                    )}
                  </div>
                  <input
                    ref={productImagesRef}
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={handleImageUpload('productImages')}
                  />
                  <Button 
                    variant="primary" 
                    textSize="text-xl"
                    onClick={() => handleImageClick('productImages')}
                    className="px-8 py-2 w-[300px] bg-[#ffecb4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] text-[#4e3a02] font-bold font-['Plus Jakarta Sans'] leading-[23px]"
                  >
                    상품 이미지 등록
                  </Button>

                  <div 
                    onClick={() => handleImageClick('productDetailImage')}
                    className="w-32 h-32 border-2 border-dashed border-gray-300 rounded-lg overflow-hidden cursor-pointer hover:border-gray-400 transition-colors duration-200"
                  >
                    {previews.productDetailImage ? (
                      <img 
                        src={previews.productDetailImage} 
                        alt="Product Detail Preview" 
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <div className="w-full h-full flex items-center justify-center bg-gray-50">
                        <ImageIcon size={48} className="text-gray-300" />
                      </div>
                    )}
                  </div>
                  <input
                    ref={productDetailImageRef}
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={handleImageUpload('productDetailImage')}
                  />
                  <Button 
                    variant="primary" 
                    textSize="text-xl"
                    onClick={() => handleImageClick('productDetailImage')}
                    className="px-8 py-2 w-[300px] bg-[#ffecb4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] text-[#4e3a02] font-bold font-['Plus Jakarta Sans'] leading-[23px]"
                  >
                    상품 상세 이미지 등록
                  </Button>
                </div>
              </div>
            </div>

            <div className="flex justify-center gap-[25px] pt-12">
              <Button 
                variant="secondary"
                onClick={handleCancel}
                className="w-[200px] h-[60px] bg-[#c2af7b] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] text-white text-2xl font-bold font-['Plus Jakarta Sans'] leading-[23px]"
              >
                취소
              </Button>
              <Button 
                variant="primary"
                onClick={handleSubmit}
                className="w-[200px] h-[60px] bg-[#ffecb4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] text-[#4e3a02] text-2xl font-bold font-['Plus Jakarta Sans'] leading-[23px]"
              >
                등록하기
              </Button>
              <ProductPreviewModal productDetailImage={previews.productDetailImage} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductRegistrationPage;