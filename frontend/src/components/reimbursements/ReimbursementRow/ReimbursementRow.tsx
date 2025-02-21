import { Button } from '@/components/ui/Button/Button';
import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { PencilIcon, TrashIcon } from '@heroicons/react/24/outline';

interface ReimbursementCardProps {
  reimbursement: ReimbursementResponse;
};

const ReimbursementRow: React.FC<ReimbursementCardProps> = ({ reimbursement }) => {
  const { description, amount, status, comment } = reimbursement;

  return (
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
              status === 'PENDING'
                ? 'bg-yellow-100 text-yellow-800'
                : status === 'APPROVED'
                ? 'bg-green-100 text-green-800'
                : 'bg-red-100 text-red-800'
            }`}
          >
            {status}
          </span>
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          {comment ?? 'N/A'}
        </td>
        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
          <div className="flex gap-4">
                   <Button
            handleClick={() => console.log('Edit button clicked')}
            isActive={status === 'PENDING'}
            className="w-12 h-12 flex items-center justify-center text-green-600 hover:text-green-100 bg-green-100 hover:bg-green-600 active:bg-green-700"
            aria-label="Edit"
          >
            <PencilIcon />
          </Button>
          <Button
            handleClick={() => console.log('Delete button clicked')}
            isActive={status === 'PENDING'}
            className="w-12 h-12 flex items-center justify-center text-red-600 hover:text-red-100 bg-red-100 hover:bg-red-600 active:bg-red-700"
            aria-label="Delete"
          >
            <TrashIcon />
          </Button>
          </div>
        </td>
      </tr>
  );
};

export { ReimbursementRow };
