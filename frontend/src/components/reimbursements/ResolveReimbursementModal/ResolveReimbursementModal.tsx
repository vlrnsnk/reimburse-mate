import { Button } from '@/components/ui/Button/Button';
import { ReimbursementStatus } from '@/interfaces/ReimbursementStatus';
import { useEffect } from 'react';

interface ResolveReimbursementModalProps {
  isOpen: boolean;
  handleClose: () => void;
  handleResolve: () => void;
  newStatus: ReimbursementStatus;
  setNewStatus: (value: ReimbursementStatus) => void;
  newComment: string | null;
  setNewComment: (value: string | null) => void;
};

const ResolveReimbursementModal: React.FC<ResolveReimbursementModalProps> = ({
  isOpen,
  handleClose,
  handleResolve,
  newStatus,
  setNewStatus,
  newComment,
  setNewComment,
}) => {
  const handleResolveButtonClick = () => {
    handleResolve();
    handleClose();
  };

  useEffect(() => {
      const handleEscape = (e: KeyboardEvent) => {
        if (e.key === 'Escape') {
          handleClose();
        }
      };

      document.addEventListener('keydown', handleEscape);

      return () => {
        document.removeEventListener('keydown', handleEscape);
      };
    }, [handleClose]);

    const handleClickOutside = (e: React.MouseEvent) => {
      if ((e.target as HTMLElement).classList.contains('modal-overlay')) {
        handleClose();
      }
    };

  if (!isOpen) {
    return null;
  }

  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-backdrop p-4"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Resolve Reimbursement</h2>
        <select
          value={newStatus}
          onChange={(e) => setNewStatus(e.target.value as ReimbursementStatus)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        >
          <option value="ALL">Select Status</option>
          <option value="PENDING">Pending</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>
        <textarea
          value={newComment ?? ''}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="Add a comment..."
          className="w-full p-2 border border-gray-300 rounded mb-4 resize-none"
          rows={4}
        />
        <div className="flex justify-between gap-2">
          <Button
            handleClick={handleResolveButtonClick}
            className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
            isActive={newComment !== null && newStatus !== 'ALL'}
          >
            Resolve
          </Button>
          <Button
            handleClick={handleClose}
            className="text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-600 active:bg-red-700"
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
  );
};

export { ResolveReimbursementModal };
