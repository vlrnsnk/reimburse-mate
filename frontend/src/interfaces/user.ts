import { UserRole } from './UserRole';

/**
 * Interface for User request object to backend
 */
export interface UserCreateRequest {
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  role: UserRole;
}

/**
 * Interface for User login request object to backend
 */
export interface UserLoginRequest {
  username: string;
  password: string;
};

/**
 * Interface for User response object from backend
 */
export interface UserResponse {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  role: UserRole;
  createdAt: string;
  updatedAt: string;
}
