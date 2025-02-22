import { useEffect } from "react";
import { Button } from "@/components/ui/Button/Button";

interface DeleteReimbursementModalProps {
  isOpen: boolean;
  handleClose: () => void;
  handleDelete: () => void;
  reimbursementId: number;
}

const DeleteReimbursementModal: React.FC<DeleteReimbursementModalProps> = ({
  isOpen,
  handleClose,
  handleDelete,
  reimbursementId,
}) => {
  // Close modal on ESC or click outside
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

  if (!isOpen) {
    return null;
  }

  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-gray-900/50 modal-overlay"
      onClick={handleClickOutside}
    >
      <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-xl font-semibold mb-8">Delete Reimbursement</h2>
        <p className="text-gray-700 mb-6">
          Are you sure you want to delete the reimbursement with ID{" "}
          {reimbursementId}?
        </p>
        <div className="flex justify-between space-x-2">
          <Button
            handleClick={() => {
              handleDelete();
              handleClose(); // Close the modal after deletion
            }}
            className="text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
          >
            Delete
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

export { DeleteReimbursementModal };

// import React from 'react';

// interface DeleteReimbursementModalProps {
//   isOpen: boolean;
//   onClose: () => void;
//   reimbursementId: number;
// }

// const DeleteReimbursementModal: React.FC<DeleteReimbursementModalProps> = ({
//   isOpen,
//   onClose,
//   reimbursementId,
// }) => {
//   const handleDelete = () => {
//     // Handle deletion (e.g., call API to delete reimbursement)
//     console.log('Deleted Reimbursement ID:', reimbursementId);
//     onClose();
//   };

//   if (!isOpen) return null;

//   return (
//     <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
//       <div className="bg-white p-6 rounded-lg shadow-md w-full max-w-md">
//         <h2 className="text-xl font-semibold mb-4">Delete Reimbursement</h2>
//         <p className="text-gray-700 mb-4">Are you sure you want to delete this reimbursement?</p>
//         <div className="flex justify-end space-x-2">
//           <button
//             onClick={onClose}
//             className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors duration-300 ease-in-out"
//           >
//             Cancel
//           </button>
//           <button
//             onClick={handleDelete}
//             className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors duration-300 ease-in-out"
//           >
//             Delete
//           </button>
//         </div>
//       </div>
//     </div>
//   );
// };

// export { DeleteReimbursementModal };
