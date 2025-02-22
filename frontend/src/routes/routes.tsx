import { createBrowserRouter, Navigate } from 'react-router-dom';
import { HomePage } from '@/pages/public/HomePage/HomePage';
import { LoginPage } from '@/pages/public/LoginPage/LoginPage';
import { NotFoundPage } from '@/pages/public/NotFoundPage/NotFoundPage';
import { RegisterPage } from '@/pages/public/RegisterPage/RegisterPage';
import { Layout } from '@/components/layout/Layout/Layout';
import { EmployeeLayout } from '@/components/layout/EmployeeLayout/EmployeeLayout';
import { ReimbursementsPage } from '@/pages/employee/ReimbursementsPage/ReimbursementsPage';
import { LogoutPage } from '@/pages/shared/LogoutPage/LogoutPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { path: '/', element: <HomePage /> },
      { path: '/login', element: <LoginPage /> },
      { path: '/register', element: <RegisterPage /> },
      { path: '/logout', element: <LogoutPage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
  {
    path: '/employee',
    element: <EmployeeLayout />,
    children: [
      { path: '', element: <Navigate to="/employee/reimbursements" /> },
      { path: 'reimbursements', element: <ReimbursementsPage /> },
    ],
  },
]);

export { router };
