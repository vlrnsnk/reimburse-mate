import { createBrowserRouter } from 'react-router-dom';
import { HomePage } from '@/pages/public/HomePage/HomePage';
import { LoginPage } from '@/pages/public/LoginPage/LoginPage';
import { NotFoundPage } from '@/pages/public/NotFoundPage/NotFoundPage';
import { RegisterPage } from '@/pages/public/RegisterPage/RegisterPage';
import { Layout } from '@/components/Layout/Layout';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { path: '/', element: <HomePage /> },
      { path: '/login', element: <LoginPage /> },
      { path: '/register', element: <RegisterPage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
]);

export { router };
