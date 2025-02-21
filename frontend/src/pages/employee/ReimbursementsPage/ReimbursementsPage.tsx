import { ReimbursementCard } from '@/components/reimbursements/ReimbursementCard/ReimbursementCard';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { getReimbursements } from '@/services/reimbursementService';
import { useEffect, useState } from 'react';

const ReimbursementsPage: React.FC = () => {
  const [reimbursements, setReimbursements] = useState<ReimbursementResponse[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const reimbursements: ReimbursementResponse[] = await getReimbursements();
        setReimbursements(reimbursements);
      } catch (error: any) {
        // TODO: Add type guard
        console.log(error.message);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-4">Reimbursements</h1>
      <p>Here you can view and manage your reimbursements.</p>
      {reimbursements && reimbursements.length > 0 ? (
        <ul>
          {reimbursements.map((reimbursement) => (
            <li key={reimbursement.id}>
              <ReimbursementCard reimbursement={reimbursement} />
            </li>
          ))}
        </ul>
      ) : (
        'No reimbursements found.'
      )}
      {/* {reimbursements && reimbursements.length > 0 ? (
        reimbursements.map((reimbursement) => (
          <div key={reimbursement.id}>
            <p>Reimbursement ID: {reimbursement.id}</p>
            <p>Amount: {reimbursement.amount}</p>
            <p>Description: {reimbursement.description}</p>
            <p>Status: {reimbursement.status}</p>
            <p>Submitted: {reimbursement.comment}</p>
            <p>Resolved: {reimbursement.createdAt}</p>
            <p>Resolver: {reimbursement.updatedAt}</p>
          </div>
        ))
      ) : <p>No reimbursements found.</p>} */}
  </div>);
};

export { ReimbursementsPage };
