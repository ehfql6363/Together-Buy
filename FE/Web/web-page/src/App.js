import React from 'react';
import ScrollToTop from './components/common/ScrollToTop';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { CookiesProvider } from 'react-cookie';
import OAuthCallback from './components/common/OAuthCallback';
import IndexPage from './pages/common/IndexPage';
import LogInPage from './pages/common/LogInPage';
import MyPage from './pages/common/MyPage';
import SignUpPage from './pages/common/SignUpPage';
import SellerRegistrationPage2 from './pages/seller/SellerRegistrationPage2';
import ProductManagementPage from './pages/product/ProductManagementPage';
import ProductRegistrationPage from './pages/product/ProductRegistrationPage';
import DeliveryManagementPage from './pages/product/DeliveryManagementPage';
import SalesStatusPage from './pages/product/SalesStatusPage';
import ProductFixPage from './pages/product/ProductFixPage';
import SellerRegistrationPage1 from './pages/seller/sellerRegistrationPage1'
import SellerLayout from './components/layouts/SellerLayout';
import BasicLayout from './components/layouts/BasicLayout';

const App = () => {
  return (
    <CookiesProvider>
      <AuthProvider>
        <Router>
          <ScrollToTop />
          <Routes>
            <Route path="/" element={<BasicLayout fullWidth><IndexPage /></BasicLayout>} />
            <Route path="/login" element={<BasicLayout><LogInPage /></BasicLayout>} />
            <Route path="/my-page" element={<SellerLayout><MyPage /></SellerLayout>} />
            <Route path="/signup" element={<BasicLayout><SignUpPage /></BasicLayout>} />
            <Route path="/seller-registration" element={<BasicLayout><SellerRegistrationPage1 /></BasicLayout>} />
            <Route path="/seller-registration2" element={<BasicLayout><SellerRegistrationPage2 /></BasicLayout>} />
            <Route path="/product-fix/:productId" element={<SellerLayout><ProductFixPage /></SellerLayout>} />
            <Route path="/product-management" element={<SellerLayout><ProductManagementPage /></SellerLayout>} />
            <Route path="/product-registration" element={<SellerLayout><ProductRegistrationPage /></SellerLayout>} />
            <Route path="/delivery-management" element={<SellerLayout><DeliveryManagementPage /></SellerLayout>} />
            <Route path="/sales-status" element={<SellerLayout><SalesStatusPage /></SellerLayout>} />
            <Route path="/auth/callback" element={<SellerLayout><OAuthCallback /></SellerLayout>} />
          </Routes>
        </Router>
      </AuthProvider>
    </CookiesProvider>
    
  );
};

export default App;