import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { api } from './api';

const getReimbursements = async (): Promise<ReimbursementResponse[]> => {
  const response = await api.get('/reimbursements');

  return response.data;
};

export { getReimbursements };
