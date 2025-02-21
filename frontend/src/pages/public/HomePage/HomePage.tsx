import { Button } from '@/components/ui/Button/Button';
import Logo from '@/assets/logo.svg?react';

const HomePage: React.FC = () => {
  return (
    <>
      <Button to="/login">Login</Button>
      <Button to="/register">Register</Button>
    </>
  );
};

export { HomePage };
