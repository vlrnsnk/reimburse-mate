import { UserCreateRequest, UserResponse, UserUpdateRequest } from '@/interfaces/user';
import { api } from './api';
import { UserRole } from '@/interfaces/UserRole';

const getUsers = async (): Promise<UserResponse[]> => {
  try {
    const response = await api.get<UserResponse[]>('/users');

    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);

    throw new Error('Failed to fetch users. Please try again later.');
  }
};

const createUser = async (user: UserCreateRequest): Promise<UserResponse> => {
  try {
    const response = await api.post('/users', user);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to create user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const deleteUser = async (userId: number): Promise<void> => {
  try {
    const response = await api.delete(`/users/${userId}`);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to delete user. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const updateUserRole = async (userId: number, role: UserRole): Promise<UserResponse> => {
  try {
    const response = await api.patch(`/users/${userId}/role`, { role });

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to update user role. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const deleteReimbursementByUserIdAndReimbursementId = async (userId: number, reimbursementId: number): Promise<void> => {
  try {
    const response = await api.delete(`/users/${userId}/reimbursements/${reimbursementId}`);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to delete reimbursement. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

const updateUserProfile = async  (userId: number | null, user: UserUpdateRequest): Promise<UserResponse> => {
  try {
    const response = await api.patch(`/users/${userId}/profile`, user);

    return response.data;
  } catch (error: any) {
    const errorMessage = error.response?.data?.message || 'Failed to update user profile. Please try again later.';
    console.error(errorMessage);

    throw new Error(errorMessage);
  }
};

export {
  getUsers,
  createUser,
  deleteUser,
  updateUserRole,
  deleteReimbursementByUserIdAndReimbursementId,
  updateUserProfile,
};
