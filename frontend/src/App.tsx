import { Route, Routes } from "react-router-dom";
import { HomePage } from "./pages/public/HomePage/HomePage";
import { LoginPage } from "./pages/public/LoginPage/LoginPage";
import { RegisterPage } from "./pages/public/RegisterPage/RegisterPage";

const App: React.FC = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Routes>
    </>
  );
};

export { App };
