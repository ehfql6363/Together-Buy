import React, { useState } from 'react';

const Input = ({
  label = "Label",
  name = "",
  placeholder = "placeholder",
  value,
  onChange,
  onFocus = () => {},
  onBlur = () => {},
  variant = "default",
  status = "default",
  containerStyle = "",
  labelStyle = "",
  type = "text",
  width = "w-full",  // width 속성 추가
  height = "h-14"    // height 속성 추가
}) => {
  const [inputStatus, setInputStatus] = useState(status);

  const getStatusClasses = () => {
    switch (inputStatus) {
      case 'default':
        return {
          container: 'border border-[#b5912d] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
      case 'focused':
      case 'filled':
        return {
          container: 'border-2 border-[#9747ff] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
      default:
        return {
          container: 'border border-[#b5912d] rounded-[20px]',
          label: 'text-[#4e3a02] text-2xl font-bold',
          input: 'text-[#4E3A02] text-xl font-bold placeholder:text-[#c1af7b]'
        };
    }
  };

  const statusClasses = getStatusClasses();

  return (
    <div className={`w-full ${containerStyle}`}>
      {label && (
        <label className={`block ${statusClasses.label} mb-2 ${labelStyle}`}>
          {label}
        </label>
      )}
      <input
        type={type}
        name={name}  // ✅ name 속성 추가
        placeholder={placeholder}
        value={value}
        onChange={(e) => {
          onChange(name, e.target.value);  // ✅ name을 직접 전달
          setInputStatus(e.target.value ? 'filled' : 'default');
        }}
        onFocus={() => {
          onFocus(name);  // ✅ name을 직접 전달
          setInputStatus('focused');
        }}
        onBlur={() => {
          onBlur(name);  // ✅ name을 직접 전달
          setInputStatus(value ? 'filled' : 'default');
        }}
        className={`
          ${width} ${height} px-8 py-2 bg-white rounded-xl
          text-xl font-bold focus:outline-none
          ${statusClasses.container} ${statusClasses.input}
        `}
        style={{
          boxShadow: 'inset 0px 4px 4px 0px rgba(0,0,0,0.25)'
        }}
      />
    </div>
  );
};

export default Input;
