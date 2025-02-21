import { Link } from 'react-router-dom';
import Logo from '@/assets/logo.svg?react';

const Header: React.FC = () => {
  return (
    <header className="bg-gray-50 shadow-sm">
      <div className="container mx-auto px-4 py-4 flex flex-col sm:flex-row justify-between items-center text-center md:text-left">
        <Link to="/" className="flex flex-col sm:flex-row items-center">
          <Logo className="h-10 w-10 text-blue-600 hidden sm:block" />
          <span className="mt-2 sm:mt-0 sm:ml-2 text-xl font-semibold text-gray-900">
            ReimburseMate
          </span>
        </Link>
        <nav className="mt-4 sm:mt-0 flex flex-col sm:flex-row space-y-2 sm:space-y-0 sm:space-x-4">
          <Link to="/login" className="text-gray-800 hover:text-gray-900 active:text-blue-600 transition-colors duration-300 ease-in-out" aria-label="Login Page">
            Login
          </Link>
          <Link to="/register" className="text-gray-800 hover:text-gray-900 active:text-blue-600 transition-colors duration-300 ease-in-out" aria-label="Register Page">
            Register
          </Link>
        </nav>
      </div>
    </header>
  );
};

export { Header };
