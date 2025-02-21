import { Button } from '@/components/ui/Button/Button';
import Logo from '@/assets/logo.svg?react';

const HomePage: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center bg-gray-50">
      <section className="bg-white p-8 rounded shadow-md text-center">
        <header>
          <Logo className="h-20 w-20 mx-auto mb-4 text-blue-600" />
          <h1 className="text-2xl font-bold text-gray-900 mb-2">ReimburseMate</h1>
          <p className="text-gray-700 mb-6">Manage your reimbursements with ease.</p>
        </header>
        <div className="flex justify-between">
          <Button to="/login">Login</Button>
          <Button to="/register">Register</Button>
        </div>
      </section>
    </div>
  );
};

export { HomePage };
