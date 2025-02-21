import { api } from './api';

const getReimbursements = async () => {
  const response = await api.get('/reimbursements');

  return response.data;
};

export { getReimbursements };
