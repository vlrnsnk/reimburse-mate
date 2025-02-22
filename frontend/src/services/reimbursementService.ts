import { ReimbursementResponse, ReimbursementRequest, ReimbursementResolveRequest } from '@/interfaces/reimbursement';
import { api } from './api';

const getReimbursements = async (): Promise<ReimbursementResponse[]> => {
  try {
    const response = await api.get<ReimbursementResponse[]>('/reimbursements');

    return response.data;
  } catch (error) {
    console.error('Error fetching reimbursements:', error);

    throw new Error('Failed to fetch reimbursements. Please try again later.');
  }
};

const getReimbursementsByUserId = async (userId: number): Promise<ReimbursementResponse[]> => {
  try {
    const response = await api.get<ReimbursementResponse[]>(`/users/${userId}/reimbursements`);

    return response.data;
  } catch (error) {
    console.error('Error fetching reimbursements by user ID:', error);

    throw new Error('Failed to fetch reimbursements by user ID. Please try again later.');
  }
}

const createReimbursement = async (reimbursementRequest: ReimbursementRequest): Promise<ReimbursementResponse> => {
  try {
    const response = await api.post<ReimbursementResponse>(`/users/${reimbursementRequest.userId}/reimbursements`, reimbursementRequest);

    return response.data;
  } catch (error) {
    console.error('Error creating reimbursement:', error);

    throw new Error('Failed to create reimbursement. Please try again later.');
  }
};

const updateReimbursement = async (reimbursementId: number, reimbursementRequest: ReimbursementRequest): Promise<ReimbursementResponse> => {
  try {
    const response = await api.patch<ReimbursementResponse>(`/users/${reimbursementRequest.userId}/reimbursements/${reimbursementId}`, reimbursementRequest);

    return response.data;
  } catch (error) {
    console.error('Error updating reimbursement:', error);

    throw new Error('Failed to update reimbursement. Please try again later.');
  }
};

const resolveReimbursement = async (reimbursementId: number, reimbursementResolveRequest: ReimbursementResolveRequest): Promise<ReimbursementResponse> => {
  try {
    const response = await api.patch<ReimbursementResponse>(`/reimbursements/${reimbursementId}`, reimbursementResolveRequest);

    return response.data;
  } catch (error) {
    console.error('Error resolving reimbursement:', error);

    throw new Error('Failed to resolve reimbursement. Please try again later.');
  }
}

export {
  getReimbursements,
  getReimbursementsByUserId,
  createReimbursement,
  updateReimbursement,
  resolveReimbursement,
};
