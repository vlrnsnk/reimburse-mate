import { ButtonProps } from '@/interfaces/ButtonProps';
import { useNavigate } from 'react-router-dom';

const Button: React.FC<ButtonProps> = ({
  children,
  handleClick,
  to,
  className,
  isActive = true,
}) => {
  const buttonClasses = `px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 active:bg-green-600 transition-colors duration-300 ease-in-out cursor-pointer disabled:cursor-not-allowed disabled:bg-gray-400 disabled:text-gray-700 ${className}`;

  const navigate = useNavigate();

  return (
    <button
        onClick={handleClick ? handleClick : to ? () => navigate(to) : undefined}
        className={`${buttonClasses}`}
        disabled={!isActive}
      >
      {children}
    </button>
  );
};

export { Button };
