import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { Footer } from "@/components/layout/Footer/Footer";
import Logo from "@/assets/logo.svg?react";
import { UserRole } from "@/interfaces/UserRole";
import { Button } from "@/components/ui/Button/Button";
import toast from "react-hot-toast";
import { logoutUser } from "@/services/authService";
import { useEffect } from 'react';

interface UserLayoutProps {
  role: UserRole;
}

const UserLayout: React.FC<UserLayoutProps> = (role: UserLayoutProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const currentPath = location.pathname;

  useEffect(() => {
    if (localStorage.getItem("userId") === null) {
      console.log("User not logged in");
      toast.error("You must be logged in to access this page.");
      navigate("/login");
    }
  }, []);

  const handleLogoutClick = async (e: React.MouseEvent) => {
    e.preventDefault();

    try {
      const response = await logoutUser();

      const loadingToast = toast.loading(
        "You are being redirected to the home page..."
      );
      toast.success("Logout successful!");

      localStorage.removeItem("userId");
      localStorage.removeItem("username");
      localStorage.removeItem("firstName");
      localStorage.removeItem("lastName");
      localStorage.removeItem("role");

      setTimeout(() => {
        navigate("/");
        toast.dismiss(loadingToast);
      }, 1000);

      console.log(response);
    } catch (error) {
      toast.error("Logout failed. Please try again.");
      console.error("Logout failed:", error);
    }
  };

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
            <span className="mt-2 sm:mt-0 sm:ml-2 text-md text-gray-900">
              Hello,{" "}
              <span className="italic text-blue-600 font-semibold hover:underline hover:text-blue-800 active:text-blue-400">
                <Link to={localStorage.getItem("role") as unknown as string === "MANAGER" ? `/manager/profile` : `/employee/profile`}>
                {localStorage.getItem("username")}</Link>
              </span>
            </span>
            <Link
              to="/logout"
              onClick={handleLogoutClick}
              className="text-gray-800 hover:text-gray-900 active:text-blue-600 transition-colors duration-300 ease-in-out"
              aria-label="Logout Page"
            >
              Logout
            </Link>
          </nav>
        </div>
      </header>
      <main className="flex flex-col flex-grow items-center px-4 py-6 overflow-x-auto">
        {role.role === "MANAGER" && !currentPath.endsWith('profile') && (
          <div className="p-4 flex flex-col gap-4 sm:flex-row mb-4">
            <Button to="/manager/reimbursements/my">My Reimbursements</Button>
            <Button to="/manager/reimbursements">Manage Reimbursements</Button>
            <Button to="/manager/users">Manage Users</Button>
          </div>
        )}
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export { UserLayout };
