import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';
import { Button } from '@/components/ui/Button/Button';

const NotFoundPage: React.FC = () => {
  return (
    <PageWrapper>
      <div className="flex flex-col items-center justify-between gap-8">
        <h1 className="text-4xl font-bold">404</h1>
        <p className="text-lg">This is not the page you are looking for</p>
        <Button
          to="/"
        >
          Go Home
        </Button>
      </div>
    </PageWrapper>
  );
};

export { NotFoundPage };
