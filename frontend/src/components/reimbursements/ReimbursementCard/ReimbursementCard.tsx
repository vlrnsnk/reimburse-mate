import { Button } from '@/components/ui/Button/Button';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { PencilIcon, TrashIcon } from '@heroicons/react/24/outline';

interface ReimbursementCardProps {
  reimbursement: ReimbursementResponse;
};

const ReimbursementCard: React.FC<ReimbursementCardProps> = ({ reimbursement }) => {
  const { description, amount, status, userId, approverId, comment, createdAt, updatedAt } = reimbursement;

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <div className="flex justify-between items-center">
        <div>
          <h3 className="text-lg font-semibold text-gray-900">{description}</h3>
          <p className="text-gray-700">Amount: ${amount}</p>
          <p className="text-gray-700">Status: {status}</p>
          <p className="text-gray-700">Submitted by User ID: {userId}</p>
          {approverId && (
            <p className="text-gray-700">Approved/Denied by User ID: {approverId}</p>
          )}
          {comment && <p className="text-gray-700">Comment: {comment}</p>}
          <p className="text-gray-700">
            Created At: {new Date(createdAt).toLocaleString()}
          </p>
          <p className="text-gray-700">
            Updated At: {new Date(updatedAt).toLocaleString()}
          </p>
        </div>
        <div className="flex gap-2">
          <Button
            handleClick={() => console.log('Edit button clicked')}
            className="w-12 h-12 flex items-center justify-center text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-700"
            aria-label="Edit"
          >
            <PencilIcon />
          </Button>
          <Button
            handleClick={() => console.log('Delete button clicked')}
            className="w-12 h-12 flex items-center justify-center text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-700 active:bg-red-600"
            aria-label="Delete"
          >
            <TrashIcon />
          </Button>
        </div>
      </div>
    </div>
  );
};

export { ReimbursementCard };
