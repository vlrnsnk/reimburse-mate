import { ReimbursementResponse } from '@/interfaces/reimbursement';

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
        <div className="space-x-2">
          <button
            onClick={() => console.log('Edit button clicked')}
            className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors duration-300 ease-in-out"
          >
            Edit
          </button>
          <button
            onClick={() => console.log('Delete button clicked')}
            className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors duration-300 ease-in-out"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export { ReimbursementCard };
