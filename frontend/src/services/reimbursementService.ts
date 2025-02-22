import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { api } from './api';

const getReimbursements = async (): Promise<ReimbursementResponse[]> => {
  const response = await api.get('/reimbursements');

  return response.data;
};

const getReimbursementsByUserId = async (userId: number): Promise<ReimbursementResponse[]> => {
  const response = await api.get(`/users/${userId}/reimbursements`);

  return response.data;
}

export {
  getReimbursements,
  getReimbursementsByUserId,
};
