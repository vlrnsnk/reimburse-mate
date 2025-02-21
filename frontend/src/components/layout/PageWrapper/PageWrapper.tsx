import { PageWrapperProps } from '@/interfaces/PageWrapperProps';

const PageWrapper: React.FC<PageWrapperProps> = ({ children }) => {
  return (
    <div className="flex flex-col items-center justify-center bg-gray-50">
      <section className="bg-white p-8 rounded shadow-md text-center">
        {children}
      </section>
    </div>
  );
};

export { PageWrapper };
