import { createBrowserRouter, Navigate } from 'react-router-dom';
import { HomePage } from '@/pages/public/HomePage/HomePage';
import { LoginPage } from '@/pages/public/LoginPage/LoginPage';
import { NotFoundPage } from '@/pages/public/NotFoundPage/NotFoundPage';
import { RegisterPage } from '@/pages/public/RegisterPage/RegisterPage';
import { Layout } from '@/components/layout/Layout/Layout';
import { EmployeeLayout } from '@/components/layout/EmployeeLayout/EmployeeLayout';
import { EmployeeDashboardPage } from '@/pages/employee/EmployeeDashboard/EmployeeDashboard';
import { ReimbursementsPage } from '@/pages/employee/ReimbursementsPage/ReimbursementsPage';
import { PendingReimbursementsPage } from '@/pages/employee/PendingReimbursementsPage/PendingReimbursementsPage';
import { CreateReimbursementPage } from '@/pages/employee/CreateReimbursementPage/CreateReimbursementPage';

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
  {
    path: '/employee',
    element: <EmployeeLayout />,
    children: [
      { path: '', element: <Navigate to="/employee/dashboard" /> },
      { path: 'dashboard', element: <EmployeeDashboardPage /> },
      { path: 'reimbursements', element: <ReimbursementsPage /> },
      { path: 'reimbursements/pending', element: <PendingReimbursementsPage /> },
      { path: 'reimbursements/create', element: <CreateReimbursementPage /> }
    ],
  },
]);

export { router };
