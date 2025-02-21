import { PageWrapper } from '@/components/layout/PageWrapper/PageWrapper';
import { Button } from '@/components/ui/Button/Button';
import { Input } from '@/components/ui/Input/Input';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const isLoginButtonActive = username !== '' && password !== '';

  const handleLogin = () => {
    console.log('Username:', username);
    console.log('Password:', password);
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Login</h1>
      <Input
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        type="text"
        placeholder="Username"
        required={true}
      />
      <Input
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        type="password"
        placeholder="Password"
        required={true}
      />
      <Button
        handleClick={handleLogin}
        className="bg-green-600 hover:bg-green-700"
        isActive={isLoginButtonActive}
      >
        Login
      </Button>
      <p className="text-gray-700 my-6">
        Don't have an account?{' '}
        <Link
          to="/register"
          className="text-blue-600 hover:text-blue-800 hover:underline active:no-underline transition-colors duration-300 ease-in-out"
        >Register</Link>
      </p>
      <Button
        to="/"
        // className="bg-gray-600 hover:bg-gray-700"
      >
        Go Home
      </Button>
    </PageWrapper>
  );
};

export { LoginPage };
