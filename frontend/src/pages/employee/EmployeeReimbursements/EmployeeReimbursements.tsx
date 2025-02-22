import { ReimbursementList } from '@/components/reimbursements/ReimbursementList/ReimbursementList';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { UserRole } from '@/interfaces/UserRole';
import { getReimbursements, getReimbursementsByUserId } from '@/services/reimbursementService';
import { useEffect, useState } from 'react';

interface ReimbursementListProps {
  role?: UserRole;
};

const EmployeeReimbursements: React.FC<ReimbursementListProps> = ({ role }) => {
  const [reimbursements, setReimbursements] = useState<ReimbursementResponse[]>([]);

  // TODO: Add isLoading
  useEffect(() => {
    const fetchData = async () => {
      try {
        // TODO: delete hardcode
        let reimbursements: ReimbursementResponse[];
        if (role === 'MANAGER') {
          reimbursements = await getReimbursements();
        } else {
          reimbursements = await getReimbursementsByUserId(1);
        }

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
      <h1 className="text-2xl font-semibold mb-12 text-gray-700">{role === 'MANAGER' ? 'Manage' : 'Your'} Reimbursements</h1>
      {reimbursements && reimbursements.length > 0
        ? <ReimbursementList
            reimbursements={reimbursements}
            role={role}
          />
        : (
          <p className="text-lg text-gray-700 text-center py-4 italic">
            No reimbursements found.
          </p>
        )
      }
    </div>
  );
};

export { EmployeeReimbursements };
