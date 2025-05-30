import React, { useEffect, useState, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Image as ImageIcon } from 'lucide-react';
import { useAuth } from '../../contexts/AuthContext';
import Input from '../../components/inputs/Input';
import Button from '../../components/buttons/Button';
import CategoryDropdown from '../../components/dropdowns/CategoryDropDown';
import DiscountUnitToggle from '../../components/common/DiscountUnitToggle';
import ProductPreviewModal from '../../components/modals/ProductPreviewModal';

const ProductFixPage = () => {
  const { productId } = useParams();
  const productImagesRef = useRef(null);
  const productDetailImageRef = useRef(null);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
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
  
  const [formData, setFormData] = useState({
    productName: '',
    categoryId: null,
    subCategoryId: null,
    price: '',
    discountAmount: '',
    discountUnit: '원',
    salePrice: '',
    total: '',
    unitAmount: '',
    unitName: '개',
    pricePerUnit: '',
    productImages: [],
    productDetailImage: ''
  });

  const [previews, setPreviews] = useState({
    productImages: null,
    productDetailImage: null
  });

  const [categoryData, setCategoryData] = useState({
    categories: [],
    subCategories: {}
  });

  const [selectedCategoryId, setSelectedCategoryId] = useState(null);

   // 카테고리 데이터 가져오기
   const fetchCategories = async () => {
    try {
      const response = await makeRequest("GET", "api/categories");
      
      const mainCategories = response.data.map(category => ({
        id: category.categoryId,
        name: category.categoryName
      }));
  
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

  // 상품 데이터 가져오기
  const fetchProductData = async () => {
    try {
      setIsLoading(true);
      const response = await makeRequest('GET', `api/seller/product/${productId}`);
      const data = response.data;
  
      console.log('서버에서 받은 카테고리명:', data.category);
  
      // 카테고리 매칭 - 슬래시(/) 포함하여 정확히 비교
      const matchingCategory = categoryData.categories.find(
        category => category.name === data.category
      );
      console.log('현재 사용 가능한 카테고리들:', categoryData.categories.map(c => c.name));
      console.log('매칭된 카테고리:', matchingCategory);
  
      const categoryId = matchingCategory ? matchingCategory.id : null;
  
      // 서브카테고리도 동일한 방식으로 정확히 매칭
      const subCategories = categoryId ? categoryData.subCategories[categoryId] : [];
      const matchingSubCategory = subCategories.find(
        subCategory => subCategory.name === data.subCategory
      );

      const subCategoryId = matchingCategory ? matchingSubCategory.id : null;

      setFormData({
        productName: data.productName || '',
        categoryId: categoryId,
        subCategoryId: subCategoryId,
        price: data.price ? data.price.toLocaleString() : '',
        discountAmount: data.discountAmount || '',
        discountUnit: data.discountUnit || '원',
        salePrice: data.salePrice ? data.salePrice.toLocaleString() : '',
        total: data.total || '',
        unitAmount: data.unitAmount || '',
        unitName: data.unitName || '개',
        pricePerUnit: data.pricePerUnit ? data.pricePerUnit.toLocaleString() : '',
        productImages: data.productImages || [],
        productDetailImage: data.productDetailImage || ''
      });
  
      setSelectedCategoryId(categoryId);
  
      // 미리보기 이미지 설정
      setPreviews({
        productImages: data.productImages?.[0] || "../assets/product_img.png",
        productDetailImage: data.productDetailImage || "../assets/product_img.png"
      });
    } catch (error) {
      console.error('상품 정보 조회 실패:', error);
      window.alert('상품 정보를 불러오는데 실패했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  useEffect(() => {
    if (categoryData.categories.length > 0) {
      fetchProductData();
    }
  }, [productId, categoryData.categories]);

  // 할인가 계산
  useEffect(() => {
    if (formData.price && formData.discountAmount) {
      const price = Number(formData.price.replace(/,/g, ''));
      const discount = Number(formData.discountAmount);
      
      let salePrice;
      if (formData.discountUnit === '%') {
        salePrice = price - (price * (discount / 100));
      } else {
        salePrice = price - discount;
      }
    
      salePrice = Math.floor(salePrice / 10) * 10;
      salePrice = Math.max(0, salePrice);
      
      setFormData(prev => ({
        ...prev,
        salePrice: salePrice.toLocaleString()
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        salePrice: formData.price || ''
      }));
    }
  }, [formData.price, formData.discountAmount, formData.discountUnit]);

  // 단위당 가격 계산
  useEffect(() => {
    if (formData.salePrice && formData.total) {
      const salePrice = Number(formData.salePrice.replace(/,/g, ''));
      const total = Number(formData.total);
      const unitAmount = Number(formData.unitAmount);
      
      if (total > 0) {
        const pricePerUnit = Math.floor((salePrice * unitAmount / total) / 10) * 10;
        setFormData(prev => ({
          ...prev,
          pricePerUnit: pricePerUnit.toLocaleString()
        }));
      }
    } else {
      setFormData(prev => ({
        ...prev,
        pricePerUnit: ''
      }));
    }
  }, [formData.salePrice, formData.total]);

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

  const handleInputChange = (name, value) => {
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleCategoryChange = (field) => (value) => {
    if (field === 'categoryId') {
      setSelectedCategoryId(value);
      setFormData(prev => ({
        ...prev,
        categoryId: value,
        subCategoryId: null
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [field]: value
      }));
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
        validate: value => !isNaN(value.replace(/,/g, '')) && parseInt(value.replace(/,/g, '')) > 0
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
      }
    ];
  
    for (const validation of validations) {
      if (!validation.validate(validation.value)) {
        alert(validation.message);
        return false;
      }
    }

    return true;
  };

  const handleCancel = () => {
    if (window.confirm('상품 수정을 취소하시겠습니까?')) {
      navigate('/product-management');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (!validateForm()) {
        return;
      }
  
      const numberFields = ['price', 'salePrice', 'pricePerUnit'];
      const cleanedData = {};
      
      // 숫자 필드의 콤마 제거 및 숫자 변환
      Object.keys(formData).forEach(key => {
        if (numberFields.includes(key)) {
          cleanedData[key] = Number(formData[key].replace(/,/g, ''));
        } else if (key !== 'productImages' && key !== 'productDetailImage') {
          // 이미지 필드는 제외하고 복사
          cleanedData[key] = formData[key];
        }
      });
  
      const formDataToSubmit = new FormData();
      
      // JSON 데이터 추가
      formDataToSubmit.append("request", new Blob([JSON.stringify(cleanedData)], { 
        type: "application/json" 
      }));
      
      // 이미지 파일 추가
      if (formData.productImages instanceof File) {
        formDataToSubmit.append('productImages', formData.productImages);
      } else {
        formDataToSubmit.append('productImages', null);
      }
      
      if (formData.productDetailImage instanceof File) {
        formDataToSubmit.append('productDetailImage', formData.productDetailImage);
      } else {
        formDataToSubmit.append('productDetailImage', null);
      }
      
      await makeRequest("PUT", `api/seller/product/${productId}`, formDataToSubmit, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      
      alert('상품이 수정되었습니다.');
      navigate('/product-management');
    } catch (error) {
      console.error('상품 수정 실패:', error);
      alert('상품 수정에 실패했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <h1 className="text-black text-[40px] font-bold font-['Plus Jakarta Sans'] leading-[56px] mb-6">
            상품 수정
          </h1>
          
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
                      value={formData.categoryId}
                      onChange={handleCategoryChange('categoryId')}
                    />
                  </div>
                  <div className="relative z-10">
                    <CategoryDropdown 
                      width="w-[200px]"
                      options={selectedCategoryId ? categoryData.subCategories[selectedCategoryId] : []}
                      value={formData.subCategoryId}
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

              <div className="space-y-12 mt-16">
                <div className="flex flex-col items-start space-y-8">
                  {/* Product Image Upload */}
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
                    상품 이미지 수정
                  </Button>

                  {/* Product Detail Image Upload */}
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
                    상품 상세 이미지 수정
                  </Button>
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
                  수정하기
                </Button>
                <ProductPreviewModal productDetailImage={previews.productDetailImage} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductFixPage