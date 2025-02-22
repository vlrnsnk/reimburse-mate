import { useState, useEffect } from 'react';
import { Button } from '@/components/ui/Button/Button';
import { Input } from '@/components/ui/Input/Input';
import { ReimbursementResponse } from '@/interfaces/reimbursement';

interface EditReimbursementModalProps {
  isOpen: boolean;
  handleClose: () => void;
  reimbursement: ReimbursementResponse;
}

const EditReimbursementModal: React.FC<EditReimbursementModalProps> = ({
  isOpen,
  handleClose,
  reimbursement,
}) => {
  const [description, setDescription] = useState<string>(reimbursement.description);
  const [amount, setAmount] = useState<number>(reimbursement.amount);

  const handleSaveButtonClick = () => {
    console.log('Updated Reimbursement:', { description, amount });
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
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-overlay"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Edit Reimbursement</h2>
        <Input
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Description"
          required
        />
        <Input
          type="number"
          value={amount}
          onChange={(e) => setAmount(parseFloat(e.target.value))}
          placeholder="Amount"
          required
        />
        <div className="flex justify-between space-x-2">
          <Button
            handleClick={handleSaveButtonClick}
            className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
            isActive={description.length > 0 && amount > 0}
          >
            Save
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

export { EditReimbursementModal };
