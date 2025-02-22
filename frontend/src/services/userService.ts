import { UserRequest, UserResponse } from '@/interfaces/user';
import { api } from './api';

const getUsers = async (): Promise<UserResponse[]> => {
  try {
    const response = await api.get<UserResponse[]>('/users');

    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);

    throw new Error('Failed to fetch users. Please try again later.');
  }
};

const createUser = async (user: UserRequest): Promise<UserResponse> => {
  try {
    const response = await api.post('/users', user);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to create user. Please try again later.';
    throw new Error(errorMessage);
  }
};

const deleteUser = async (userId: number): Promise<void> => {
  try {
    const response = await api.delete(`/users/${userId}`);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to delete user. Please try again later.';
    throw new Error(errorMessage);
  }
};

export {
  getUsers,
  createUser,
  deleteUser,
};
