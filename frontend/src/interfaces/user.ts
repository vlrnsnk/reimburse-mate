import { UserRole } from './UserRole';

/**
 * Interface for User request object to backend
 */
export interface UserRequest {
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  role: UserRole;
}

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
