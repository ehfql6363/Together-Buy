import React, { useState } from 'react';
import { X } from "lucide-react";

const ProductPreviewModal = ({ productDetailImage }) => {
  const [isOpen, setIsOpen] = useState(false);

  if (!productDetailImage) {
    return null;
  }

  return (
    <>
      <button 
        onClick={() => setIsOpen(true)}
        className="w-[200px] h-[60px] bg-[#ffecb4] rounded-[20px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] border border-[#b5912d] text-[#4e3a02] text-2xl font-bold font-['Plus Jakarta Sans'] leading-[23px]"
      >
        미리보기
      </button>

      {isOpen && (
        <div 
          className="fixed inset-0 bg-black bg-opacity-50 flex items-start justify-center z-50"
          onClick={() => setIsOpen(false)}
        >
          <div 
            className="relative bg-white max-w-5xl w-full overflow-y-auto mt-8 max-h-[90vh]"
            onClick={e => e.stopPropagation()}
          >
            <button
              onClick={() => setIsOpen(false)}
              className="absolute top-2 right-2 p-1 rounded-full bg-white hover:bg-gray-100 shadow-lg z-10"
            >
              <X className="h-5 w-5" />
            </button>
            {productDetailImage && (
                <img 
                src={productDetailImage} 
                alt="Product Detail" 
                className="w-full h-auto"
              />
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default ProductPreviewModal;