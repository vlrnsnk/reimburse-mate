import { Button } from "@/components/ui/Button/Button";
import { UserResponse } from "@/interfaces/user";
import { UserRole } from "@/interfaces/UserRole";
import { useEffect, useState } from "react";

interface ChangeUserRoleModalProps {
  isOpen: boolean;
  handleClose: () => void;
  handleChangeRole: (newRole: UserRole) => void;
  user: UserResponse;
}

const ChangeUserRoleModal: React.FC<ChangeUserRoleModalProps> = ({
  isOpen,
  handleClose,
  handleChangeRole,
  user,
}) => {
  const [newRole, setNewRole] = useState<UserRole>(user.role);

  const handleChangeButtonClick = () => {
    // handleChangeRole();
    handleClose();
  };

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
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-backdrop"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Change User Role</h2>
        <p className="mb-4">Select the new role for this user:</p>
        <select
          defaultValue={newRole}
          className="w-full p-2 border border-gray-300 rounded mb-12"
        >
          <option value={UserRole.EMPLOYEE}>EMPLOYEE</option>
          <option value={UserRole.MANAGER}>MANAGER</option>
        </select>
        <div className="flex justify-between gap-2">
          <Button
            //() => handleChangeRole(role === UserRole.EMPLOYEE ? UserRole.MANAGER : UserRole.EMPLOYEE)
            handleClick={handleChangeButtonClick}
            className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
          >
            Change
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

export { ChangeUserRoleModal };
