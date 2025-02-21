import { Outlet } from 'react-router-dom';
import { Footer } from '@/components/layout/Footer/Footer';
import { Header } from '@/components/layout/Header/Header';

const Layout: React.FC = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <main className="flex flex-col flex-grow items-center justify-center px-4 py-6">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export { Layout };
