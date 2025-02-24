import { PageWrapper } from "@/components/layout/PageWrapper/PageWrapper";
import { Button } from "@/components/ui/Button/Button";
import { Input } from "@/components/ui/Input/Input";
import { UserLoginRequest } from "@/interfaces/user";
import { loginUser } from "@/services/authService";
import { useState } from "react";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const navigate = useNavigate();

  const isLoginButtonActive = username !== "" && password !== "";

  const handleLogin: () => void = async () => {
    const payload: UserLoginRequest = {
      username,
      password,
    };

    try {
      const response = await loginUser(payload);

      const loadingToast = toast.loading(
        "You are being redirected to your dashboard..."
      );
      toast.success("Login successful!");

      setTimeout(() => {
        toast.dismiss(loadingToast);

        if (response.role === "MANAGER") {
          navigate("/manager/reimbursements");
        } else {
          navigate("/employee/reimbursements");
        }
      }, 1000);

      const { id, username, firstName, lastName, role } = response;

      localStorage.setItem("userId", id.toString());
      localStorage.setItem("username", username);
      localStorage.setItem("firstName", firstName);
      localStorage.setItem("lastName", lastName);
      localStorage.setItem("role", role);

      console.log(response);
    } catch (error: any) {
      console.error(error.message);
      toast.error("Login failed. Please try again.");
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (isLoginButtonActive) {
      handleLogin();
    }
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Login</h1>
      <form onSubmit={handleSubmit}>
        <Input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          type="text"
          placeholder="Username"
          required
        />
        <Input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          placeholder="Password"
          required
        />
        <Button
          type="submit"
          isActive={isLoginButtonActive}
          className="bg-green-600 hover:bg-green-700"
        >
          Login
        </Button>
      </form>
      <p className="text-gray-700 my-6">
        Don't have an account?{" "}
        <Link
          to="/register"
          className="text-blue-600 hover:text-blue-800 hover:underline active:no-underline transition-colors duration-300 ease-in-out"
        >
          Register
        </Link>
      </p>
      <Button to="/">Go Home</Button>
    </PageWrapper>
  );
};

export { LoginPage };
