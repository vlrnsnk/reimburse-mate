import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';
import { Button } from '@/components/ui/Button/Button';

const LogoutPage: React.FC = () => {
  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-8">Logout</h1>
      <p className="text-gray-700 mb-12">You have been logged out.</p>
      <Button
        to="/"
      >
        Go Home
      </Button>
    </PageWrapper>
  );
};

export { LogoutPage };
