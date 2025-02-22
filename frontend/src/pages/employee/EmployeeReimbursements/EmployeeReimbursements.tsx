import { CreateReimbursementFormModal } from '@/components/reimbursements/CreateReimbursementFormModal/CreateReimbursementFormModal';
import { ReimbursementList } from "@/components/reimbursements/ReimbursementList/ReimbursementList";
import { Button } from "@/components/ui/Button/Button";
import { ReimbursementRequest, ReimbursementResponse } from "@/interfaces/reimbursement";
import { UserRole } from "@/interfaces/UserRole";
import {
  createReimbursement,
  getReimbursements,
  getReimbursementsByUserId,
} from "@/services/reimbursementService";
import { PlusIcon } from "@heroicons/react/24/outline";
import { useEffect, useState } from "react";
import toast from 'react-hot-toast';

interface ReimbursementListProps {
  role?: UserRole;
}

const EmployeeReimbursements: React.FC<ReimbursementListProps> = ({ role }) => {
  const [reimbursements, setReimbursements] = useState<ReimbursementResponse[]>(
    []
  );
  const [newReimbursementAmount, setNewReimbursementAmount] = useState<number>(0);
  const [newReimbursementDescription, setNewReimbursementDescription] = useState<string>('');

  const fetchData = async () => {
    try {
      // TODO: delete hardcode
      let reimbursements: ReimbursementResponse[];
      if (role === "MANAGER") {
        reimbursements = await getReimbursements();
      } else {
        reimbursements = await getReimbursementsByUserId(4);
      }

      setReimbursements(reimbursements);
    } catch (error: any) {
      // TODO: Add type guard
      console.log(error.message);
    }
  };

  // TODO: Add isLoading
  useEffect(() => {
    fetchData();
  }, []);

  const handleSaveReimbursement = async () => {
    try {
      const payload: ReimbursementRequest = {
        userId: 4,
        description: newReimbursementDescription,
        amount: newReimbursementAmount,
        status: "PENDING",
      };
      console.log(payload)
      const response = await createReimbursement(payload);
      setNewReimbursementAmount(0);
      setNewReimbursementDescription('');
      toast.success("Reimbursement created successfully!");
      console.log(response);
    } catch (error: any) {
      toast.error("Failed to create reimbursement. Please try again later.");
      console.error("Error creating reimbursement:", error);
    }

    fetchData();
  };

  const [isCreateModalOpen, setIsCreateModalOpen] = useState<boolean>(false);

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-8 text-gray-700">
        {role === "MANAGER" ? "Manage" : "Your"} Reimbursements
      </h1>
      <div className="flex justify-center mb-8">
        <Button
          handleClick={() => setIsCreateModalOpen(true)}
          className="rounded-full flex items-center justify-center shadow-sm bg-green-600 hover:bg-green-700"
          aria-label="Add Reimbursement"
        >
          <PlusIcon className="w-6 h-6 pr-2" /> Create New Reimbursement
        </Button>
      </div>
      {reimbursements && reimbursements.length > 0 ? (
        <ReimbursementList reimbursements={reimbursements} role={role} />
      ) : (
        <p className="text-lg text-gray-700 text-center py-4 italic">
          No reimbursements found.
        </p>
      )}
      <CreateReimbursementFormModal
        isOpen={isCreateModalOpen}
        handleClose={() => setIsCreateModalOpen(false)}
        handleSave={handleSaveReimbursement}
        amount={newReimbursementAmount}
        setAmount={setNewReimbursementAmount}
        description={newReimbursementDescription}
        setDescription={setNewReimbursementDescription}
      />
    </div>
  );
};

export { EmployeeReimbursements };
