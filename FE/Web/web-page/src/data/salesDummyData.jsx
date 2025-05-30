import beef from '../assets/beef.jpg';
import tomato from '../assets/tomato.jpg';
import rolecake from '../assets/rolecake.jpg';
import startbucks from '../assets/startbucks.jpg';
import kimchimandoo from '../assets/kimchimandoo.jpg';


// 월별 매출 데이터
export const monthlySalesData = [
    { category: '1월', sales: 8245000 },
    { category: '2월', sales: 7950000 },
    { category: '3월', sales: 8480000 },
    { category: '4월', sales: 8980000 },
    { category: '5월', sales: 9320000 },
    { category: '6월', sales: 8890000 },
    { category: '7월', sales: 9540000 },
    { category: '8월', sales: 10120000 },
    { category: '9월', sales: 9780000 },
    { category: '10월', sales: 9450000 },
    { category: '11월', sales: 10340000 },
    { category: '12월', sales: 11250000 }
  ];
  
  // 카테고리별 매출 데이터
  export const categorySalesData = {
    '전체': monthlySalesData,
    '정육/계란': [
      { category: '1월', sales: 2245000 },
      { category: '2월', sales: 2120000 },
      { category: '3월', sales: 2380000 },
      { category: '4월', sales: 2280000 },
      { category: '5월', sales: 2520000 },
      { category: '6월', sales: 2390000 },
      { category: '7월', sales: 2440000 },
      { category: '8월', sales: 2620000 },
      { category: '9월', sales: 2580000 },
      { category: '10월', sales: 2450000 },
      { category: '11월', sales: 2840000 },
      { category: '12월', sales: 3150000 }
    ],
    '과일/채소': [
      { category: '1월', sales: 1980000 },
      { category: '2월', sales: 1850000 },
      { category: '3월', sales: 2080000 },
      { category: '4월', sales: 2320000 },
      { category: '5월', sales: 2520000 },
      { category: '6월', sales: 2290000 },
      { category: '7월', sales: 2440000 },
      { category: '8월', sales: 2620000 },
      { category: '9월', sales: 2380000 },
      { category: '10월', sales: 2150000 },
      { category: '11월', sales: 2340000 },
      { category: '12월', sales: 2550000 }
    ],
    '냉장/냉동/간편식': [
      { category: '1월', sales: 1820000 },
      { category: '2월', sales: 1780000 },
      { category: '3월', sales: 1920000 },
      { category: '4월', sales: 2180000 },
      { category: '5월', sales: 1980000 },
      { category: '6월', sales: 1910000 },
      { category: '7월', sales: 2260000 },
      { category: '8월', sales: 2480000 },
      { category: '9월', sales: 2320000 },
      { category: '10월', sales: 2250000 },
      { category: '11월', sales: 2460000 },
      { category: '12월', sales: 2650000 }
    ],
    '커피/음료': [
      { category: '1월', sales: 1120000 },
      { category: '2월', sales: 1080000 },
      { category: '3월', sales: 1180000 },
      { category: '4월', sales: 1280000 },
      { category: '5월', sales: 1420000 },
      { category: '6월', sales: 1510000 },
      { category: '7월', sales: 1560000 },
      { category: '8월', sales: 1580000 },
      { category: '9월', sales: 1420000 },
      { category: '10월', sales: 1350000 },
      { category: '11월', sales: 1460000 },
      { category: '12월', sales: 1550000 }
    ],
    '간식/베이커리': [
      { category: '1월', sales: 1080000 },
      { category: '2월', sales: 1120000 },
      { category: '3월', sales: 920000 },
      { category: '4월', sales: 920000 },
      { category: '5월', sales: 880000 },
      { category: '6월', sales: 790000 },
      { category: '7월', sales: 840000 },
      { category: '8월', sales: 820000 },
      { category: '9월', sales: 1080000 },
      { category: '10월', sales: 1250000 },
      { category: '11월', sales: 1240000 },
      { category: '12월', sales: 1350000 }
    ]
  };
  
  // 최근 주문 데이터
  export const recentOrders = [
    {
      orderNumber: "ORDER-2024-001",
      productName: "한우 1++ 등심 500g",
      category: "정육/계란",
      amount: 58000,
      address: "서울시 강남구 역삼동 123-45",
      imageUrl: beef
    },
    {
      orderNumber: "ORDER-2024-002",
      productName: "무농약 대추방울토마토 1kg",
      category: "과일/채소",
      amount: 12800,
      address: "경기도 성남시 분당구 정자동 567-89",
      imageUrl: tomato
    },
    {
      orderNumber: "ORDER-2024-003",
      productName: "곰표 밀크롤 케이크",
      category: "간식/베이커리",
      amount: 4800,
      address: "서울시 마포구 연남동 234-56",
      imageUrl: rolecake
    },
    {
      orderNumber: "ORDER-2024-004",
      productName: "스타벅스 하우스 블렌드 200g",
      category: "커피/음료",
      amount: 15000,
      address: "인천시 연수구 송도동 789-12",
      imageUrl: startbucks
    },
    {
      orderNumber: "ORDER-2024-005",
      productName: "올반 김치만두 1kg",
      category: "냉장/냉동/간편식",
      amount: 8900,
      address: "부산시 해운대구 우동 345-67",
      imageUrl: kimchimandoo
    }
  ];
  
  // 카테고리 목록
  export const categories = ['전체', '정육/계란', '과일/채소', '냉장/냉동/간편식', '커피/음료', '간식/베이커리'];
  
  // 카테고리별 대표 상품
  export const categoryProducts = {
    '정육/계란': ['한우 등심', '목심 슬라이스', '무항생제 계란', '닭가슴살', '돼지 삼겹살'],
    '과일/채소': ['햇사과', '대추방울토마토', '감자', '양파', '바나나'],
    '냉장/냉동/간편식': ['올반 김치만두', '비비고 왕교자', '곰표 도시락', '즉석 떡볶이', '냉동 피자'],
    '커피/음료': ['스타벅스 원두', '매일 우유', '콤부차', '과일주스', '탄산수'],
    '간식/베이커리': ['곰표 밀크롤', '크로와상', '허니버터칩', '초코파이', '마카롱']
  };