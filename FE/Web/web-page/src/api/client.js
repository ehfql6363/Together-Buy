import axios from 'axios';

const api = axios.create({
  baseURL: window._env_.REACT_APP_API_URL || 'https://togethrebuy.com',
});

// 요청 인터셉터
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && error.response?.data?.message === 'Token expired') {
      localStorage.removeItem('token');
      window.dispatchEvent(new CustomEvent('tokenExpired'));
    }
    return Promise.reject(error);
  }
);

export default api;