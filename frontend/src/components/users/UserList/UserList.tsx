import { UserResponse } from '@/interfaces/user';
import { UserRole } from '@/interfaces/UserRole';
import { useState } from 'react';
import { UserRow } from '@/components/users/UserRow/UserRow';

interface UserListProps {
  users: UserResponse[];
}

const UserList: React.FC<UserListProps> = ({ users }) => {
  const [filterRole, setFilterRole] = useState<UserRole | 'ALL'>('ALL');

  const filteredUsers = users.filter((user) =>
    filterRole === 'ALL' || user.role === filterRole
  );

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full bg-white rounded-lg shadow-md">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Name
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Username
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              <select
                value={filterRole}
                onChange={(e) => setFilterRole(e.target.value as UserRole | 'ALL')}
                className="bg-transparent text-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer"
              >
                <option value="ALL">ROLE</option>
                <option value={UserRole.EMPLOYEE}>EMPLOYEE</option>
                <option value={UserRole.MANAGER}>MANAGER</option>
              </select>
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Created At
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-gray-200">
          {filteredUsers.length > 0 ? (
            filteredUsers.map((user) => (
              <UserRow
                key={user.id}
                user={user}
              />
            ))
          ) : (
            <tr>
              <td colSpan={5} className="px-6 py-4 text-center text-gray-700 italic">
                No users found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export { UserList };
