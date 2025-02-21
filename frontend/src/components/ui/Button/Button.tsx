import { ButtonProps } from '@/interfaces/ButtonProps';
import { Link } from 'react-router-dom';

const Button: React.FC<ButtonProps> = ({ to, children, className }) => {
  return (
    <Link
      to={to}
      className={`px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 active:bg-green-600 transition-colors duration-300 ease-in-out ${className}`}
    >
      {children}
    </Link>
  );
};

export { Button };
