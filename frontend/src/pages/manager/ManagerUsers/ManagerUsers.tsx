import { UserList } from '@/components/users/UserList/UserList';
import { UserResponse } from '@/interfaces/user';
import { getUsers } from '@/services/userService';
import { useEffect, useState } from 'react';
import { ScaleLoader } from 'react-spinners';

const ManagerUsers: React.FC = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);

  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isErrorLoading, setIsErrorLoading] = useState<boolean>(false);

  const fetchData = async () => {
    setIsLoading(true);
    setIsErrorLoading(false);

    try {
      const users = await getUsers();
      setUsers(users);
    } catch (error: any) {
      // TODO: Add type guard
      setIsErrorLoading(true);
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
          <ScaleLoader color="navy" margin={4} loading={isLoading} />
        </div>
      ) : isErrorLoading ? (
        <div className="error-message text-red-500">
          Something went wrong while loading the data. Please try again later.
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
