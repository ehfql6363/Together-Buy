import React from 'react';
import Input from './Input';

const formatPhoneNumber = (value) => {
  const numbers = value.replace(/[^\d]/g, '');
  
  if (numbers.length <= 2) return numbers;
  
  if (numbers.startsWith('02')) {
    if (numbers.length <= 5) return `02-${numbers.slice(2)}`;
    return `02-${numbers.slice(2, 5)}-${numbers.slice(5, 9)}`;
  }
  
  if (numbers.length <= 3) return numbers;
  if (numbers.length <= 7) return `${numbers.slice(0, 3)}-${numbers.slice(3)}`;
  return `${numbers.slice(0, 3)}-${numbers.slice(3, 7)}-${numbers.slice(7, 11)}`;
};

const PhoneInput = ({ value = '', onChange, label, name }) => {
  return (
    <Input
      type="tel"
      name={name}
      value={value}
      onChange={(name, value) => {
        const formattedNumber = formatPhoneNumber(value);
        onChange(name, formattedNumber);
      }}
      placeholder="예: 02-123-4567"
      maxLength={13}
      label={label}
    />
  );
};

export default PhoneInput;