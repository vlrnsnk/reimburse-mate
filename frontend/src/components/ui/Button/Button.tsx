import { ButtonProps } from '@/interfaces/ButtonProps';
import { useLocation, useNavigate } from 'react-router-dom';

const Button: React.FC<ButtonProps> = ({
  children,
  handleClick,
  isActive = true,
  to,
  className,
}) => {
  const buttonClasses = `px-4 py-2 bg-blue-600 text-gray-100 rounded-md hover:border-blue-700 hover:bg-blue-700 active:bg-green-600 active:border-green-600 border-blue-600 disabled:cursor-not-allowed disabled:bg-gray-400 disabled:text-gray-700 transition-colors duration-300 ease-in-out cursor-pointer ${className}`;

  const navigate = useNavigate();
  const location = useLocation();

  const isActiveRoute = to ? location.pathname === to : false;
  const activeClass = 'bg-gray-50 text-gray-800 border  hover:text-gray-100';

  return (
    <button
        onClick={handleClick ? handleClick : to ? () => navigate(to) : undefined}
        className={`${buttonClasses} ${isActiveRoute ? activeClass : ''}`}
        disabled={!isActive}
      >
      {children}
    </button>
  );
};

export { Button };
