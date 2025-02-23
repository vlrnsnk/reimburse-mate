import { CreateReimbursementFormModal } from "@/components/reimbursements/CreateReimbursementFormModal/CreateReimbursementFormModal";
import { ReimbursementList } from "@/components/reimbursements/ReimbursementList/ReimbursementList";
import { Button } from "@/components/ui/Button/Button";
import {
  ReimbursementRequest,
  ReimbursementResponse,
} from "@/interfaces/reimbursement";
import { UserRole } from "@/interfaces/UserRole";
import {
  createReimbursement,
  getReimbursements,
  getReimbursementsByUserId,
} from "@/services/reimbursementService";
import { PlusIcon } from "@heroicons/react/24/outline";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { ClipLoader } from "react-spinners";

interface ReimbursementListProps {
  role?: UserRole;
  isPersonal?: boolean;
}

const EmployeeReimbursements: React.FC<ReimbursementListProps> = ({
  role,
  isPersonal,
}) => {
  const [reimbursements, setReimbursements] = useState<ReimbursementResponse[]>(
    []
  );
  const [newReimbursementAmount, setNewReimbursementAmount] = useState<
    number | undefined
  >(undefined);
  const [newReimbursementDescription, setNewReimbursementDescription] =
    useState<string>("");

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const fetchData = async () => {
    setIsLoading(true);

    try {
      const userId: number = localStorage.getItem(
        "userId"
      ) as unknown as number;

      const reimbursements: ReimbursementResponse[] =
        role === "MANAGER" && !isPersonal
          ? await getReimbursements()
          : await getReimbursementsByUserId(userId);

      setReimbursements(reimbursements);
    } catch (error: any) {
      // TODO: Add type guard
      console.log(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [isPersonal]);

  const handleSaveReimbursement = async () => {
    const payload: ReimbursementRequest = {
      userId: localStorage.getItem("userId") as unknown as number,
      description: newReimbursementDescription,
      amount: newReimbursementAmount,
      status: "PENDING",
    };

    try {
      const response = await createReimbursement(payload);
      setNewReimbursementAmount(undefined);
      setNewReimbursementDescription("");
      toast.success("Reimbursement created successfully!");
      console.log(response);
    } catch (error: any) {
      toast.error("Failed to create reimbursement. Please try again later.");
      console.error("Error creating reimbursement:", error);
    }

    fetchData();
  };

  const handleReimbursementChanged = async () => {
    await fetchData();
  };

  const [isCreateModalOpen, setIsCreateModalOpen] = useState<boolean>(false);

  return (
    <div className="text-center">
      <h1 className="text-2xl font-semibold mb-8 text-gray-700">
        {role === "MANAGER" ? "Manage" : "Your"} Reimbursements
      </h1>
      <div className="flex justify-center mb-8">
        {(isPersonal || localStorage.getItem("role") === "EMPLOYEE") && (
          <Button
            handleClick={() => setIsCreateModalOpen(true)}
            className="rounded-full flex items-center justify-center shadow-sm bg-green-600 hover:bg-green-700"
            aria-label="Add Reimbursement"
          >
            <PlusIcon className="w-6 h-6 pr-2" />
            Create New Reimbursement
          </Button>
        )}
      </div>
      {isLoading ? (
        <div className="flex justify-center">
          <ClipLoader size={50} color="blue" loading={isLoading} />
        </div>
      ) : reimbursements && reimbursements.length > 0 ? (
        <ReimbursementList
          reimbursements={reimbursements}
          role={role}
          handleReimbursementChanged={handleReimbursementChanged}
        />
      ) : (
        <p className="text-lg text-gray-700 text-center py-4 italic">
          No reimbursements found.
        </p>
      )}
      <CreateReimbursementFormModal
        isOpen={isCreateModalOpen}
        handleClose={() => setIsCreateModalOpen(false)}
        handleSave={handleSaveReimbursement}
        amount={newReimbursementAmount}
        setAmount={setNewReimbursementAmount}
        description={newReimbursementDescription}
        setDescription={setNewReimbursementDescription}
      />
    </div>
  );
};

export { EmployeeReimbursements };
