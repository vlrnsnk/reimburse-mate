import { Button } from "@/components/ui/Button/Button";
import { ReimbursementResponse } from "@/interfaces/reimbursement";
import { PencilIcon, PlusIcon, TrashIcon, CheckCircleIcon } from "@heroicons/react/24/outline";
import { useState } from 'react';
import { ReimbursementFormModal } from '@/components/reimbursements/ReimbursementFormModal/ReimbursementFormModal';
import { DeleteReimbursementModal } from '@/components/reimbursements/DeleteReimbursementModal/DeleteReimbursementModal';
import { UserRole } from '@/interfaces/UserRole';
import { ResolveReimbursementModal } from '../ResolveReimbursementModal/ResolveReimbursementModal';

interface ReimbursementCardProps {
  reimbursement: ReimbursementResponse;
  role?: UserRole;
}

const ReimbursementRow: React.FC<ReimbursementCardProps> = ({
  reimbursement,
  role,
}) => {
  const { id, description, amount, status, comment } = reimbursement;

  const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState<boolean>(false);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState<boolean>(false);
  const [isResolveModalOpen, setIsResolveModalOpen] = useState<boolean>(false);

  const isAnyModalOpened = isEditModalOpen || isDeleteModalOpen || isCreateModalOpen || isResolveModalOpen;

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

      <ReimbursementFormModal
        isOpen={isEditModalOpen}
        handleClose={() => setIsEditModalOpen(false)}
        handleSave={() => console.log("Saved Reimbursement ID:", id)}
        reimbursement={reimbursement}
      />

      <DeleteReimbursementModal
        isOpen={isDeleteModalOpen}
        handleClose={() => setIsDeleteModalOpen(false)}
        handleDelete={() => console.log("Deleted Reimbursement ID:", id)}
        reimbursementId={id}
      />

      <ReimbursementFormModal
        isOpen={isCreateModalOpen}
        isCreating={true}
        handleClose={() => setIsCreateModalOpen(false)}
        handleSave={() => console.log("Created new Reimbursement")}
        reimbursement={reimbursement}
      />

      <ResolveReimbursementModal
        isOpen={isResolveModalOpen}
        handleClose={() => setIsResolveModalOpen(false)}
        handleResolve={() => {
          console.log("Resolved Reimbursement ID:", id, "with status:" + "and comment:", comment);
        }}
        reimbursement={reimbursement}
      />

      {/*
        // TODO: move it up and hide when another modal opened
      */}
      {!isAnyModalOpened ? (<Button
        handleClick={() => setIsCreateModalOpen(true)}
        className="fixed bottom-8 right-8 w-14 h-14 rounded-full flex items-center justify-center shadow-sm bg-green-600 hover:bg-green-700"
        aria-label="Add Reimbursement"
      >
        <PlusIcon className="w-6 h-6" />
      </Button>) : null
      }
    </>
  );
};

export { ReimbursementRow };
