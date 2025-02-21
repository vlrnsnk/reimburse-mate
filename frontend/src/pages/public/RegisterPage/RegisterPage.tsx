import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';
import { Button } from '@/components/ui/Button/Button';
import { Input } from '@/components/ui/Input/Input';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const RegisterPage: React.FC = () => {
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const isRegisterButtonActive = firstName !== '' && lastName !== '' && username !== '' && password !== '';

  const handleRegister: () => void = () => {
    console.log(`First Name: ${firstName} Last Name: ${lastName} Username: ${username} Password: ${password}`);
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Register</h1>
        <Input
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          type="text"
          placeholder="First Name"
          required
        />
        <Input
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          type="text"
          placeholder="Last Name"
          required
        />
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
          handleClick={handleRegister}
          isActive={isRegisterButtonActive}
          className="bg-green-600 hover:bg-green-700"
        >
          Register
        </Button>
      <p className="text-gray-700 my-6">
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
