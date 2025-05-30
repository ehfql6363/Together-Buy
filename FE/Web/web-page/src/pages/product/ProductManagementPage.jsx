import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ProductManagementCard from '../../components/cards/ProductManagementCard';
import StatusCheckModal from '../../components/modals/StatusCheckModal';
import { useAuth } from '../../contexts/AuthContext';

const ProductManagementPage = () => {
  const navigate = useNavigate();
  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const [selectedProductId, setSelectedProductId] = useState(null);
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isSeller, setIsSeller] = useState(false);
  const { makeRequest, decodeToken } = useAuth();
  const [isAuthorized, setIsAuthorized] = useState(true);

  useEffect(() => {
    fetchProducts();
  }, []);

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

  const fetchProducts = async () => {
    try {
      setIsLoading(true);
      const data = await makeRequest('GET', 'api/seller/products');
      const productsWithSoldOut = data.data.map(product => ({
        productId: product.productId,
        productName: product.productName,
        productMainImage: product.productMainImage,
        price: product.price,
        salePrice: product.salePrice,
        category: product.category,
        subCategory: product.subCategory,
        soldOut: false
      }));
      setProducts(productsWithSoldOut);
    } catch (err) {
      window.alert(err.message);
      console.error('상품 정보 조회 에러:', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleEdit = (productId) => {
    navigate(`/product-fix/${productId}`);
  };

  const handleStatus = (productId) => {
    setSelectedProductId(productId);
    setIsStatusModalOpen(true);
  };

  const handleSoldOut = (productId) => {
    setProducts(prevProducts => 
      prevProducts.map(product => 
        product.productId === productId
          ? { ...product, soldOut: !product.soldOut }
          : product
      )
    );
  };

  const handleDelete = async (productId) => {
    if (!window.confirm('정말로 이 상품을 삭제하시겠습니까?')) {
      return;
    }
  
    try {
      await makeRequest('DELETE', `api/seller/product/${productId}`);
      
      // 삭제 성공 시 상품 목록에서 제거
      setProducts(prevProducts => 
        prevProducts.filter(product => product.productId !== productId)
      );
      
      // 성공 메시지 표시
      alert('상품이 성공적으로 삭제되었습니다.');
      
    } catch (error) {
      let errorMessage = '상품 삭제 중 오류가 발생했습니다.';
      
      if (error.message === '토큰이 만료되었습니다. 다시 로그인해주세요.') {
        errorMessage = error.message;
      } else if (error.response?.data?.message) {
        errorMessage = error.response.data.message;
      }
      
      alert(errorMessage);
    }
  };

  if (isLoading) {
    return (
      <div className="min-h-screen bg-[#faf7f0] flex items-center justify-center">
        <div className="text-lg">로딩 중...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#faf7f0]">
      <div className="flex pt-2">
        <div className="flex-1 p-8 mt-[60px] ml-10">
          <h1 className="text-[40px] font-bold mb-12">상품 관리</h1>
          
          {products.length === 0 ? (
            <div className="text-center text-gray-500 mt-8">
              등록된 상품이 없습니다.
            </div>
          ) : (
            <div className="space-y-6">
              {products.map((product) => (
                <ProductManagementCard
                  key={product.productId}
                  productName={product.productName}
                  category={`${product.category}-${product.subCategory}`}
                  price={product.price?.toLocaleString() || '0'}
                  salePrice={product.salePrice?.toLocaleString() || '0'}
                  imageUrl={product.productMainImage || "../assets/product_img.png"}
                  soldOut={product.soldOut}
                  onEdit={() => handleEdit(product.productId)}
                  onStatus={() => handleStatus(product.productId)}
                  onSoldOut={() => handleSoldOut(product.productId)}
                  onDelete={() => handleDelete(product.productId)}
                />
              ))}
            </div>
          )}
        </div>
      </div>

      <StatusCheckModal
        isOpen={isStatusModalOpen}
        onClose={() => {
          setIsStatusModalOpen(false);
          setSelectedProductId(null);
        }}
        productId={selectedProductId}
      />
    </div>
  );
};

export default ProductManagementPage;