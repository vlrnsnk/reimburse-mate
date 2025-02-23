import { ReimbursementStatus } from './ReimbursementStatus';

/**
 * Interface for Reimbursement request object to backend
 */
export interface ReimbursementRequest {
  description: string;
  amount: number | undefined;
  status: ReimbursementStatus;
  userId: number;
};

export interface ReimbursementResolveRequest {
  status: ReimbursementStatus;
  comment: string | null;
  approverId: number;
};

/**
 * Interface for Reimbursement response object from backend
 */
export interface ReimbursementResponse {
  id: number;
  description: string;
  amount: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  userId: number;
  approverId: number | null;
  comment: string | null;
  createdAt: string;
  updatedAt: string;
};
