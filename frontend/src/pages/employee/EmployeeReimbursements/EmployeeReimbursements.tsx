import { ReimbursementList } from '@/components/reimbursements/ReimbursementList/ReimbursementList';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { getReimbursements } from '@/services/reimbursementService';
import { useEffect, useState } from 'react';

const EmployeeReimbursements: React.FC = () => {
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
      <h1 className="text-2xl font-semibold mb-12 text-gray-700">Your Reimbursements</h1>
      {reimbursements && reimbursements.length > 0
        ? <ReimbursementList reimbursements={reimbursements} />
        : (
          <p className="text-lg text-gray-700 text-center py-4 italic">
            No reimbursements found.
          </p>
        )
      }
  </div>);
};

export { EmployeeReimbursements };
