import { PageWrapper } from "@/components/layout/PageWrapper/PageWrapper";
import { Button } from "@/components/ui/Button/Button";
import { Input } from "@/components/ui/Input/Input";
import { UserCreateRequest } from "@/interfaces/user";
import { UserRole } from "@/interfaces/UserRole";
import { registerUser } from "@/services/authService";
import { useState } from "react";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";

const RegisterPage: React.FC = () => {
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const navigate = useNavigate();

  const isRegisterButtonActive =
    firstName !== "" && lastName !== "" && username !== "" && password !== "";

  const handleRegister: () => void = async () => {
    const payload: UserCreateRequest = {
      firstName,
      lastName,
      username,
      password,
      role: UserRole.EMPLOYEE,
    };

    try {
      const response = await registerUser(payload);

      const loadingToast = toast.loading(
        "You are being redirected to the login page..."
      );
      toast.success("Registration successful!");

      setTimeout(() => {
        toast.dismiss(loadingToast);
        navigate("/login");
      }, 1000);

      console.log(response);
    } catch (error: any) {
      if (error.message) {
        toast.error(error.message);
      } else {
        toast.error("Registration failed. Please try again.");
      }

      console.error(error.message);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (isRegisterButtonActive) {
      handleRegister();
    }
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Register</h1>
      <p className="text-sm text-gray-600 mb-4">
        <span className="text-red-500">*</span> Fields marked with{" "}
        <span className="text-red-500">*</span> are mandatory.
      </p>
      <form onSubmit={handleSubmit}>
        <Input
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          type="text"
          placeholder="First Name *"
          required
        />
        <Input
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          type="text"
          placeholder="Last Name *"
          required
        />
        <Input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          type="text"
          placeholder="Username *"
          required
        />
        <Input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          placeholder="Password *"
          required
        />
        <Button
          type="submit"
          isActive={isRegisterButtonActive}
          className="bg-green-600 hover:bg-green-700 mt-4"
        >
          Register
        </Button>
      </form>
      <p className="text-gray-700 my-8">
        Already have an account?{" "}
        <Link
          to="/login"
          className="text-blue-600 hover:text-blue-800 hover:underline active:no-underline transition-colors duration-300 ease-in-out"
        >
          Login
        </Link>
      </p>
      <Button to="/">Go Home</Button>
    </PageWrapper>
  );
};

export { RegisterPage };
