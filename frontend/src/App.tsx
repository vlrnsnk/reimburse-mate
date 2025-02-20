import { HomePage } from "./pages/public/HomePage/HomePage";
import { LoginPage } from "./pages/public/LoginPage/LoginPage";
import { RegisterPage } from "./pages/public/RegisterPage/RegisterPage";

const App: React.FC = () => {
  return (
    <>
      <HomePage />
      <LoginPage />
      <RegisterPage />
    </>
  );
};

export { App };
