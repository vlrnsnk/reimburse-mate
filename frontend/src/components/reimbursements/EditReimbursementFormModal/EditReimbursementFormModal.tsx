import { useEffect } from "react";
import { Button } from "@/components/ui/Button/Button";
import { Input } from "@/components/ui/Input/Input";

interface EditReimbursementFormModalProps {
  isOpen: boolean;
  handleClose: () => void;
  handleSave: () => void;
  amount: number;
  setAmount: (value: number) => void;
  description: string;
  setDescription: (value: string) => void;
}

const EditReimbursementFormModal: React.FC<EditReimbursementFormModalProps> = ({
  isOpen,
  handleClose,
  handleSave,
  amount,
  setAmount,
  description,
  setDescription,
}) => {
  const handleSaveButtonClick = () => {
    handleSave();
    handleClose();
  };

  useEffect(() => {
    const handleEscape = (e: KeyboardEvent) => {
      if (e.key === "Escape") {
        handleClose();
      }
    };

    document.addEventListener("keydown", handleEscape);

    return () => {
      document.removeEventListener("keydown", handleEscape);
    };
  }, [handleClose]);

  const handleClickOutside = (e: React.MouseEvent) => {
    if ((e.target as HTMLElement).classList.contains("modal-overlay")) {
      handleClose();
    }
  };

  const isSaveButtonActive =
    description.length > 0 && amount !== undefined && amount > 0;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (isSaveButtonActive) {
      handleSaveButtonClick();
    }
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-overlay p-4"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Edit Reimbursement</h2>
        <form onSubmit={handleSubmit}>
          <label className="block mb-1 text-left">
            Description:
            <Input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Description"
              required
              additionalClasses="mt-2"
            />
          </label>
          <label className="block mb-1 text-left">
            Amount:
            <Input
              type="number"
              value={amount}
              onChange={(e) => setAmount(parseFloat(e.target.value))}
              placeholder="Amount"
              required
              additionalClasses="mt-2"
            />
          </label>
          <div className="flex justify-between gap-2">
            <Button
              type="submit"
              className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
              isActive={isSaveButtonActive}
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
        </form>
      </div>
    </div>
  );
};

export { EditReimbursementFormModal };
