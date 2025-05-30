import React from 'react';

const BankDropdown = ({ 
  label, 
  value, 
  onChange,
  containerStyle,
  labelStyle
}) => {
  const banks = [
    '',  // Empty string for default selection
    '신한은행',
    '국민은행',
    '우리은행',
    '하나은행',
    '농협은행',
    '기업은행',
    '카카오뱅크',
    '토스뱅크'
  ];

  return (
    <div className={`w-full ${containerStyle}`}>
      <label className={`block text-[#4e3a02] font-bold ${labelStyle}`}>
        {label}
      </label>
      <div className="relative w-[250px]">
        <select
          value={value}
          onChange={(e) => onChange(e.target.value)}
          className="w-full h-14 px-8 bg-white rounded-xl
            text-[#4E3A02] text-xl font-bold
            border border-[#b5912d] rounded-[20px]
            focus:outline-none focus:border-[#9747ff] focus:border-2"
          style={{
            boxShadow: 'inset 0px 4px 4px 0px rgba(0,0,0,0.25)',
            appearance: 'none',
            backgroundImage: `url("data:image/svg+xml,%3Csvg width='24' height='24' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M6 9L12 15L18 9' stroke='%234E3A02' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'/%3E%3C/svg%3E")`,
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'right 1rem center'
          }}
        >
          <option value="" disabled>은행</option>
          {banks.slice(1).map((bank) => (
            <option key={bank} value={bank}>
              {bank}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default BankDropdown;