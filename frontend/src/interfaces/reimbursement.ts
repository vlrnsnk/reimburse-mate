/**
 * Interface for Reimbursement response object from backend
 */
export interface ReimbursementRequest {
  description: string;
  amount: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  userId: number;
};

/**
 * Interface for Reimbursement request object to backend
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
