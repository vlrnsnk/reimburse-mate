import { UserList } from '@/components/users/UserList/UserList';
import { UserResponse } from '@/interfaces/user';
import { getUsers } from '@/services/userService';
import { useEffect, useState } from 'react';

const ManagerUsers: React.FC = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);

  // TODO: Add isLoading
  useEffect(() => {
    const fetchData = async () => {
      try {
        const users = await getUsers();
        setUsers(users);
      } catch (error: any) {
        // TODO: Add type guard
        console.log(error.message);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-12 text-gray-700">Manage Users</h1>
      {users && users.length > 0
        ? <UserList
            users={users}
          />
        : (
          <p className="text-lg text-gray-700 text-center py-4 italic">
            No Users found.
          </p>
        )
      }
    </div>
  );
};

export { ManagerUsers };
