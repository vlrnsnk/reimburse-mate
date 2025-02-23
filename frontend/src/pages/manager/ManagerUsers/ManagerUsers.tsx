import { UserList } from '@/components/users/UserList/UserList';
import { UserResponse } from '@/interfaces/user';
import { getUsers } from '@/services/userService';
import { useEffect, useState } from 'react';
import { ClipLoader } from 'react-spinners';

const ManagerUsers: React.FC = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const fetchData = async () => {
    setIsLoading(true);

    try {
      const users = await getUsers();
      setUsers(users);
    } catch (error: any) {
      // TODO: Add type guard
      console.log(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleUserDeleted = () => {
    fetchData();
  };

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-12 text-gray-700">Manage Users</h1>
      {isLoading ? (
        <div className="flex justify-center">
          <ClipLoader size={50} color="blue" loading={isLoading} />
        </div>
      ) : users && users.length > 0
        ? <UserList
            users={users}
            handleRowDeleted={handleUserDeleted}
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
