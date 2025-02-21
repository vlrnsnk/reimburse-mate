const Footer: React.FC = () => {
  return (
    <footer className="bg-gray-800 text-gray-50 py-6 mt-auto">
      <div className="container mx-auto px-4 text-center text-sm">
        <p>
          &copy; {new Date().getFullYear()} ReimburseMate. All rights reserved.
        </p>
      </div>
    </footer>
  );
};

export { Footer };
