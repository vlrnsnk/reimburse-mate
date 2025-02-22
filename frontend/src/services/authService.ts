import { UserRequest } from '@/interfaces/user';
import { api } from './api';

const registerUser = async (user: UserRequest): Promise<UserRequest> => {
  try {
    const response = await api.post('/auth/register', user);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to create user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

export {
  registerUser,
};
