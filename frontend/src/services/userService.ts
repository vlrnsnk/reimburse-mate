import { UserResponse } from '@/interfaces/user';
import { api } from './api';

const getUsers = async (): Promise<UserResponse[]> => {
  const response = await api.get('/users');

  return response.data;
};

export {
  getUsers,
};
