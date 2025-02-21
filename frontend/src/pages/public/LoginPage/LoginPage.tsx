import { Input } from '@/components/ui/Input/Input';
import { useState } from 'react';

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  return (
    <>
      <h1>Login to ReimburseMate!</h1>
      <Input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          type="text"
          placeholder="Username"
          required={true}
        />
        <br />
        <Input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          placeholder="Password"
          required={true}
        />
    </>
  );
};

export { LoginPage };
