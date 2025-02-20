import { Outlet } from "react-router-dom";
import { Footer } from "../Footer/Footer";

const Layout: React.FC = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <main className="flex flex-col flex-grow items-center justify-center p-4">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export { Layout };
