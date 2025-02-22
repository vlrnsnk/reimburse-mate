import { RouterProvider } from 'react-router-dom';
import { router } from '@/routes/routes';
import { Toaster } from 'react-hot-toast';

const App: React.FC = () => {
  return (
    <>
      <Toaster position="top-right" />
      <RouterProvider router={router} />
    </>
  );
};

export { App };
