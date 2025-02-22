import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';
import { Button } from '@/components/ui/Button/Button';
import { Input } from '@/components/ui/Input/Input';
import { UserRequest } from '@/interfaces/user';
import { UserRole } from '@/interfaces/UserRole';
import { createUser } from '@/services/userService';
import { useState } from 'react';
import toast from 'react-hot-toast';
import { Link, useNavigate } from 'react-router-dom';

const RegisterPage: React.FC = () => {
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const navigate = useNavigate();

  const isRegisterButtonActive = firstName !== '' && lastName !== '' && username !== '' && password !== '';

  const handleRegister = async () => {
    const payload: UserRequest = {
      firstName,
      lastName,
      username,
      password,
      role: UserRole.EMPLOYEE,
    };

    try {
      const response = await createUser(payload);
      // TODO: clear fields
      const loadingToast = toast.loading('You are being redirected to the login page...')
      toast.success('Registration successful!');

      setTimeout(() => {
        toast.dismiss(loadingToast);
        navigate('/login');
      }, 3000);
      console.log(response);
    } catch (error: any) {
      toast.error('Registration failed! Please try again.');
      console.warn(error.message);
    }
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Register</h1>
      <p className="text-sm text-gray-600 mb-4">
        <span className="text-red-500">*</span> Fields marked with <span className="text-red-500">*</span> are mandatory.
      </p>
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
          handleClick={handleRegister}
          isActive={isRegisterButtonActive}
          className="bg-green-600 hover:bg-green-700 mt-4"
        >
          Register
        </Button>
      <p className="text-gray-700 my-8">
        Already have an account?{' '}
        <Link
          to="/login"
          className="text-blue-600 hover:text-blue-800 hover:underline active:no-underline transition-colors duration-300 ease-in-out"
        >
          Login
        </Link>
      </p>
      <Button
        to="/"
      >
        Go Home
      </Button>
    </PageWrapper>
  );
};

export { RegisterPage };
