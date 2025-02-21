import { Link, Outlet } from "react-router-dom";
import { Footer } from "@/components/layout/Footer/Footer";
import Logo from "@/assets/logo.svg?react";
import { Button } from "@/components/ui/Button/Button";

const EmployeeLayout: React.FC = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <header className="bg-gray-50 shadow-sm">
        <div className="container mx-auto px-4 py-4 flex flex-col sm:flex-row justify-between items-center text-center md:text-left">
          <Link to="/" className="flex flex-col sm:flex-row items-center">
            <Logo className="h-10 w-10 text-blue-600 hidden sm:block" />
            <span className="mt-2 sm:mt-0 sm:ml-2 text-xl font-semibold text-gray-900">
              ReimburseMate
            </span>
          </Link>
          <nav className="mt-4 sm:mt-0 flex flex-col sm:flex-row space-y-2 sm:space-y-0 sm:space-x-4">
            <Link
              to="/login"
              className="text-gray-800 hover:text-gray-900 active:text-blue-600 transition-colors duration-300 ease-in-out"
              aria-label="Logout Page"
            >
              Logout
            </Link>
          </nav>
        </div>
      </header>
      <main className="flex flex-col flex-grow items-center px-4 py-6">
        <div className="p-4 flex space-x-4 mb-12">
          <Button
            to="/employee/dashboard"
          >
            Dashboard
          </Button>
          <Button
            to="/employee/reimbursements"
          >
            View Reimbursements
          </Button>
          <Button
            to="/employee/reimbursements/pending"
          >
            Pending Reimbursements
          </Button>
          <Button
            to="/employee/reimbursements/create"
          >
            Create Reimbursement
          </Button>
        </div>
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export { EmployeeLayout };
