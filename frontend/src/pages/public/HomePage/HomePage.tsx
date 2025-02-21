import { Button } from '@/components/ui/Button/Button';
import Logo from '@/assets/logo.svg?react';
import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';

const HomePage: React.FC = () => {
  return (
    <PageWrapper>
      <header>
          <Logo className="h-20 w-20 mx-auto mb-4 text-blue-600" />
          <h1 className="text-2xl font-bold text-gray-900 mb-2">ReimburseMate</h1>
          <p className="text-gray-700 mb-6">Manage your reimbursements with ease.</p>
        </header>
        <div className="flex justify-between">
          <Button to="/login">Login</Button>
          <Button to="/register">Register</Button>
        </div>
    </PageWrapper>
  );
};

export { HomePage };
