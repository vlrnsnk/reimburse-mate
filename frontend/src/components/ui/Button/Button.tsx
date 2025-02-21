import { ButtonProps } from '@/interfaces/ButtonProps';
import { Link } from 'react-router-dom';

const Button: React.FC<ButtonProps> = ({
  children,
  onClick,
  to,
  className,
}) => {
  const buttonClasses = `px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 active:bg-green-600 transition-colors duration-300 ease-in-out ${className}`;

  if (to) {
    return (
      <Link
        to={to}
        className={buttonClasses}
      >
        {children}
      </Link>
    );
  }

  return (
    <button
      onClick={onClick}
      className={`${buttonClasses} cursor-pointer`}
    >
      {children}
    </button>
  );
};

// const className = "px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 active:bg-blue-800 focus:outline-none focus:ring-2 focus:ring-blue-600";


export { Button };
