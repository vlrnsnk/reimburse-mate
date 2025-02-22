import { Button } from "@/components/ui/Button/Button";
import { ReimbursementRequest, ReimbursementResponse } from "@/interfaces/reimbursement";
import { PencilIcon, TrashIcon, CheckCircleIcon } from "@heroicons/react/24/outline";
import { useState } from 'react';
import { EditReimbursementFormModal } from '@/components/reimbursements/EditReimbursementFormModal/EditReimbursementFormModal';
import { DeleteReimbursementModal } from '@/components/reimbursements/DeleteReimbursementModal/DeleteReimbursementModal';
import { UserRole } from '@/interfaces/UserRole';
import { ResolveReimbursementModal } from '../ResolveReimbursementModal/ResolveReimbursementModal';
import { updateReimbursement } from '@/services/reimbursementService';
import toast from 'react-hot-toast';

interface ReimbursementCardProps {
  reimbursement: ReimbursementResponse;
  role?: UserRole;
  handleReimbursementChanged?: () => void;
}

const ReimbursementRow: React.FC<ReimbursementCardProps> = ({
  reimbursement,
  role,
  handleReimbursementChanged,
}) => {
  const { id, description, amount, status, comment } = reimbursement;

  const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState<boolean>(false);
  const [isResolveModalOpen, setIsResolveModalOpen] = useState<boolean>(false);

  const [newReimbursementAmount, setNewReimbursementAmount] = useState<number>(amount);
  const [newReimbursementDescription, setNewReimbursementDescription] = useState<string>(description);

  const handleEditReimbursement = async () => {
    const payload: ReimbursementRequest = {
      userId: 4,
      description: newReimbursementDescription,
      amount: newReimbursementAmount,
      status: status,
    };
    console.log(payload);

    try {
      const response = await updateReimbursement(reimbursement.id, payload);

      if (handleReimbursementChanged) {
        handleReimbursementChanged();
      }

      toast.success("Reimbursement updated successfully!");
      console.log(response);
    } catch (error: any) {
      toast.error("Failed to update reimbursement. Please try again later.");
      console.error("Error updating reimbursement:", error);
    }
  };


  return (
    <>
      <tr className="hover:bg-gray-50 transition-colors duration-200">
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 max-w-xs">
          {description}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          ${amount}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          <span
            className={`px-2 py-1 rounded-full text-xs font-semibold ${
              status === "PENDING"
                ? "bg-yellow-100 text-yellow-800"
                : status === "APPROVED"
                ? "bg-green-100 text-green-800"
                : "bg-red-100 text-red-800"
            }`}
          >
            {status}
          </span>
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          {comment ?? "N/A"}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          <div className="flex gap-4">
            {role === 'MANAGER' && (
              <Button
                handleClick={() => setIsResolveModalOpen(true)}
                className="w-12 h-12 flex items-center justify-center text-orange-600 hover:text-orange-100 bg-orange-100 hover:bg-orange-600 active:bg-orange-700"
                aria-label="Resolve"
              >
                <CheckCircleIcon />
              </Button>
            )}
            <Button
              handleClick={() => setIsEditModalOpen(true)}
              isActive={status === "PENDING" || role === 'MANAGER'}
              className="w-12 h-12 flex items-center justify-center text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
              aria-label="Edit"
            >
              <PencilIcon />
            </Button>
            <Button
              handleClick={() => setIsDeleteModalOpen(true)}
              isActive={status === "PENDING" || role === 'MANAGER'}
              className="w-12 h-12 flex items-center justify-center text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-600 active:bg-red-700"
              aria-label="Delete"
            >
              <TrashIcon />
            </Button>
          </div>
        </td>
      </tr>

      <EditReimbursementFormModal
        isOpen={isEditModalOpen}
        handleClose={() => setIsEditModalOpen(false)}
        handleSave={handleEditReimbursement}
        amount={newReimbursementAmount}
        setAmount={setNewReimbursementAmount}
        description={newReimbursementDescription}
        setDescription={setNewReimbursementDescription}
      />

      <DeleteReimbursementModal
        isOpen={isDeleteModalOpen}
        handleClose={() => setIsDeleteModalOpen(false)}
        handleDelete={() => console.log("Deleted Reimbursement ID:", id)}
        reimbursementId={id}
      />

      <ResolveReimbursementModal
        isOpen={isResolveModalOpen}
        handleClose={() => setIsResolveModalOpen(false)}
        handleResolve={() => {
          console.log("Resolved Reimbursement ID:", id, "with status:" + "and comment:", comment);
        }}
        reimbursement={reimbursement}
      />
    </>
  );
};

export { ReimbursementRow };
