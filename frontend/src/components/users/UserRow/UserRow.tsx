import { UserResponse } from '@/interfaces/user';
import { UserRole } from '@/interfaces/UserRole';
import { Button } from '@/components/ui/Button/Button';
import { TrashIcon, AdjustmentsHorizontalIcon } from '@heroicons/react/24/outline';
import { useState } from 'react';
import { DeleteUserModal } from '@/components/users/DeleteUserModal/DeleteUserModal';
import { ChangeUserRoleModal } from '@/components/users/ChangeUserRoleModal/ChangeUserRoleModal';
import { deleteUser, updateUserRole } from '@/services/userService';
import toast from 'react-hot-toast';

interface UserRowProps {
  user: UserResponse;
  handleRowDeleted?: () => void;
}

const UserRow: React.FC<UserRowProps> = ({ user, handleRowDeleted }) => {
  const { id, firstName, lastName, username, role, createdAt } = user;

  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState<boolean>(false);
  const [isRoleChangeModalOpen, setIsRoleChangeModalOpen] = useState<boolean>(false);

  const [newRole, setNewRole] = useState<UserRole>(user.role);

  const handleDeleteUser = async (userId: number) => {
    try {
      const response = await deleteUser(userId);

      if (handleRowDeleted) {
        handleRowDeleted();
      }

      toast.success('User deleted successfully!');
      console.log(response);
    } catch (error: any) {
      toast.error('Failed to delete user. Please try again later.');
      console.error('Error deleting user:', error);
    }

    setIsDeleteModalOpen(false);
  };

  const handleChangeRole = async (userId: number, newRole: UserRole) => {
    try {
      const response = await updateUserRole(userId, newRole);

      if (handleRowDeleted) {
        handleRowDeleted();
      }

      toast.success('User role updated successfully!');
      console.log(response);
    } catch (error: any) {
      toast.error('Failed to update user role. Please try again later.');
      console.error('Error updating user role:', error);
    }

    setIsRoleChangeModalOpen(false);
  };

  return (
    <>
      <tr className="hover:bg-gray-50 transition-colors duration-200">
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          {firstName} {lastName}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          {username}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          <span
            className={`px-2 py-1 rounded-full text-xs font-semibold ${
              role === UserRole.MANAGER
                ? "bg-blue-100 text-blue-800"
                : "bg-gray-100 text-gray-800"
            }`}
          >
            {role}
          </span>
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          {new Date(createdAt).toLocaleDateString()}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          <div className="flex gap-4">
            <Button
              handleClick={() => setIsRoleChangeModalOpen(true)}
              className="w-12 h-12 flex items-center justify-center text-orange-600 hover:text-orange-100 bg-orange-100 hover:bg-orange-600 active:bg-orange-700"
              aria-label="Change Role"
              isActive={localStorage.getItem('userId') !== id.toString()}
            >
              <AdjustmentsHorizontalIcon />
            </Button>
            <Button
              handleClick={() => setIsDeleteModalOpen(true)}
              className="w-12 h-12 flex items-center justify-center text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-600 active:bg-red-700"
              aria-label="Delete"
              isActive={localStorage.getItem('userId') !== id.toString()}
            >
              <TrashIcon />
            </Button>
          </div>
        </td>
      </tr>

      <DeleteUserModal
        isOpen={isDeleteModalOpen}
        handleClose={() => setIsDeleteModalOpen(false)}
        handleDeleteUser={() => handleDeleteUser(user.id)}
      />

      <ChangeUserRoleModal
        isOpen={isRoleChangeModalOpen}
        handleClose={() => setIsRoleChangeModalOpen(false)}
        handleChangeRole={handleChangeRole}
        newRole={newRole}
        setNewRole={setNewRole}
        userId={id}
      />
    </>
  );
};

export { UserRow };
