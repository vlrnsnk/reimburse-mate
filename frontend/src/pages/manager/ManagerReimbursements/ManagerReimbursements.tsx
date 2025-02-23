import { UserRole } from '@/interfaces/UserRole';
import { EmployeeReimbursements } from '@/pages/employee/EmployeeReimbursements/EmployeeReimbursements';

const ManagerReimbursements: React.FC<{ isPersonal?: boolean}> = ({ isPersonal = false }) => {
  return (
    <EmployeeReimbursements
      role={UserRole.MANAGER}
      isPersonal={isPersonal}
    />
  );
};

export { ManagerReimbursements };
