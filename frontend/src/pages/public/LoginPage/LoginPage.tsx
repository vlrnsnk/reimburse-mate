import { Input } from '@/components/ui/Input/Input';
import { useState } from 'react';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState<string>('');

  return (
    <>
      <h1>Login to ReimburseMate!</h1>
      <Input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
    </>
  );
};

export { LoginPage };
