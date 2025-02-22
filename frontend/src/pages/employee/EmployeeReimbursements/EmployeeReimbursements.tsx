import { ReimbursementFormModal } from '@/components/reimbursements/ReimbursementFormModal/ReimbursementFormModal';
import { ReimbursementList } from '@/components/reimbursements/ReimbursementList/ReimbursementList';
import { Button } from '@/components/ui/Button/Button';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { UserRole } from '@/interfaces/UserRole';
import { getReimbursements, getReimbursementsByUserId } from '@/services/reimbursementService';
import { PlusIcon } from '@heroicons/react/24/outline';
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
          reimbursements = await getReimbursementsByUserId(2);
        }

        setReimbursements(reimbursements);
      } catch (error: any) {
        // TODO: Add type guard
        console.log(error.message);
      }
    };

    fetchData();
  }, []);

  const [isCreateModalOpen, setIsCreateModalOpen] = useState<boolean>(false);

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-12 text-gray-700">{role === 'MANAGER' ? 'Manage' : 'Your'} Reimbursements</h1>
      {reimbursements && reimbursements.length > 0
        ? <ReimbursementList
            reimbursements={reimbursements}
            role={role}
          />
        : (
          <>
          <p className="text-lg text-gray-700 text-center py-4 italic">
            No reimbursements found.
          </p>
                <ReimbursementFormModal
                isOpen={isCreateModalOpen}
                isCreating={true}
                handleClose={() => setIsCreateModalOpen(false)}
                handleSave={() => console.log("Created new Reimbursement")}
              />
              <Button
        handleClick={() => setIsCreateModalOpen(true)}
        className="rounded-full flex items-center justify-center shadow-sm bg-green-600 hover:bg-green-700"
        aria-label="Add Reimbursement"
      >
        <PlusIcon className="w-6 h-6 pr-2" /> Create New Reimbursement
      </Button>
          </>
        )
      }
    </div>
  );
};

export { EmployeeReimbursements };
