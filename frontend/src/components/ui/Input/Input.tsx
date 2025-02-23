import { InputProps } from '@/interfaces/InputProps';

const Input: React.FC<InputProps> = ({
  value,
  onChange,
  type,
  placeholder,
  required = false,
  additionalClasses,
}) => {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      required={required}
      className={`w-full mb-4 p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:invalid:border-red-600 focus:invalid:ring-red-600 ${additionalClasses}`}
    />
  );
};

export { Input };
