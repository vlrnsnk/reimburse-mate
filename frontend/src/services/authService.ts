import { UserCreateRequest, UserLoginRequest, UserResponse } from '@/interfaces/user';
import { api } from './api';

const registerUser = async (user: UserCreateRequest): Promise<UserResponse> => {
  try {
    const response = await api.post('/auth/register', user);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to create user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const loginUser = async (user: UserLoginRequest): Promise<UserResponse> => {
  try {
    const response = await api.post(
      '/auth/login',
      user, {
      withCredentials: true,
    });

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to authenticate user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const logoutUser = async (): Promise<void> => {
  try {
    const response = await api.post('/auth/logout', {}, {
      withCredentials: true,
    });

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to logout user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

export {
  registerUser,
  loginUser,
  logoutUser,
};
