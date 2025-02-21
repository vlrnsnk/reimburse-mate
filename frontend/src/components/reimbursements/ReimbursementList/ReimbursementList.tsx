import { ReimbursementResponse } from '@/interfaces/reimbursement';
import { ReimbursementCard } from '../ReimbursementCard/ReimbursementCard';
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
    <div className="space-y-4">
      <div className="flex justify-end">
        <select
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value as ReimbursementStatus)}
          className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="ALL">All</option>
          <option value="PENDING">Pending</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>
      </div>
      <div className="space-y-4">
        {filteredReimbursements && filteredReimbursements.length > 0 ? (
          <ul>
            {filteredReimbursements.map((reimbursement) => ((
              <li key={reimbursement.id}>
                <ReimbursementCard reimbursement={reimbursement} />
              </li>
            )))}
          </ul>
        ) : (
          <p className="text-lg text-gray-700 text-center py-4 italic">
            No reimbursements found.
          </p>
        )}
      </div>
    </div>
  );
};

export { ReimbursementList };
