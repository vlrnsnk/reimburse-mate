import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { ReimbursementRow } from '../ReimbursementRow/ReimbursementRow';
import { useState } from 'react';
import { ReimbursementStatus } from '@/interfaces/ReimbursementStatus';

interface ReimbursementListProps {
  reimbursements: ReimbursementResponse[];
};

const ReimbursementList: React.FC<ReimbursementListProps> = ({ reimbursements }) => {
  console.log(JSON.stringify(reimbursements, null, 2));
  const [filterStatus, setFilterStatus] = useState<ReimbursementStatus>('ALL');

  const filteredReimbursements = reimbursements.filter((reimbursement) =>
    filterStatus === 'ALL' || reimbursement.status === filterStatus
  );

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full bg-white rounded-lg shadow-md">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Description
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Amount
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              <select
                value={filterStatus}
                onChange={(e) =>
                  setFilterStatus(e.target.value as ReimbursementStatus)
                }
                className="bg-transparent text-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 cursor-pointer"
              >
                <option value="ALL">STATUS</option>
                <option value="PENDING">PENDING</option>
                <option value="APPROVED">APPROVED</option>
                <option value="REJECTED">REJECTED</option>
              </select>
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Approver's Comment
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-gray-200">
          {filteredReimbursements.length > 0 ? (
            filteredReimbursements.map((reimbursement) => (
              <ReimbursementRow key={reimbursement.id} reimbursement={reimbursement} />
            ))
          ) : (
            <tr>
              <td colSpan={5} className="px-6 py-4 text-center text-gray-700 italic">
                No reimbursements found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export { ReimbursementList };
