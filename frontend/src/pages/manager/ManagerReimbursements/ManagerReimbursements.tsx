import { UserRole } from '@/interfaces/UserRole';
import { EmployeeReimbursements } from '@/pages/employee/EmployeeReimbursements/EmployeeReimbursements';

const ManagerReimbursements: React.FC = () => {
  return (
    <EmployeeReimbursements
      role={UserRole.MANAGER}
    />
  );
};

export { ManagerReimbursements };
