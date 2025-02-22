import { createBrowserRouter, Navigate } from 'react-router-dom';
import { HomePage } from '@/pages/public/HomePage/HomePage';
import { LoginPage } from '@/pages/public/LoginPage/LoginPage';
import { NotFoundPage } from '@/pages/public/NotFoundPage/NotFoundPage';
import { RegisterPage } from '@/pages/public/RegisterPage/RegisterPage';
import { Layout } from '@/components/layout/Layout/Layout';
import { UserLayout } from '@/components/layout/UserLayout/UserLayout';
import { EmployeeReimbursements } from '@/pages/employee/EmployeeReimbursements/EmployeeReimbursements';
import { LogoutPage } from '@/pages/shared/LogoutPage/LogoutPage';
import { UserRole } from '@/interfaces/UserRole';
import { ManagerReimbursements } from '@/pages/manager/ManagerReimbursements/ManagerReimbursements';
import { ManagerUsers } from '@/pages/manager/ManagerUsers/ManagerUsers';

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
    element: <UserLayout role={UserRole.EMPLOYEE} />,
    children: [
      { path: '', element: <Navigate to="/employee/reimbursements" /> },
      { path: 'reimbursements', element: <EmployeeReimbursements /> },
    ],
  },
  {
    path: 'manager',
    element: <UserLayout role={UserRole.MANAGER} />,
    children: [
      { path: '', element: <Navigate to="/manager/reimbursements" /> },
      { path: 'reimbursements', element: <ManagerReimbursements /> },
      { path: 'users', element: <ManagerUsers /> },
    ],
  },
]);

export { router };
