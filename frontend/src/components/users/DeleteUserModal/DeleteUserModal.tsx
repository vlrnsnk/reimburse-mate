import { useEffect } from "react";
import { Button } from "@/components/ui/Button/Button";

interface DeleteUserModalProps {
  isOpen: boolean;
  handleClose: () => void;
  handleDelete: () => void;
  userId: number;
}

const DeleteUserModal: React.FC<DeleteUserModalProps> = ({
  isOpen,
  handleClose,
  handleDelete,
  userId,
}) => {
  // Close modal on ESC or click outside
  useEffect(() => {
    const handleEscape = (e: KeyboardEvent) => {
      if (e.key === "Escape") {
        handleClose();
      }
    };

    document.addEventListener("keydown", handleEscape);

    return () => {
      document.removeEventListener("keydown", handleEscape);
    };
  }, [handleClose]);

  const handleClickOutside = (e: React.MouseEvent) => {
    if ((e.target as HTMLElement).classList.contains("modal-overlay")) {
      handleClose();
    }
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-overlay"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Delete User</h2>
        <p className="text-gray-700 mb-6">
        Are you sure you want to delete this user with ID{" "}
          {userId}?
        </p>
        <div className="flex justify-between space-x-2">
          <Button
            handleClick={() => {
              handleDelete();
              handleClose();
            }}
            className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
          >
            Delete
          </Button>
          <Button
            handleClick={handleClose}
            className="text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-600 active:bg-red-700"
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
  );
};

export { DeleteUserModal };
