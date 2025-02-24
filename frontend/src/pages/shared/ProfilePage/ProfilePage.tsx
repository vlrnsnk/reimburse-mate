import { PageWrapper } from "@/components/layout/PageWrapper/PageWrapper";
import { Button } from "@/components/ui/Button/Button";
import { Input } from "@/components/ui/Input/Input";
import { UserUpdateRequest } from "@/interfaces/user";
import { updateUserProfile } from "@/services/userService";
import { useState } from "react";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";

const ProfilePage: React.FC = () => {
  const [username, setUsername] = useState<string>(
    localStorage.getItem("username") || ""
  );
  const [firstName, setFirstName] = useState<string>(
    localStorage.getItem("firstName") || ""
  );
  const [lastName, setLastName] = useState<string>(
    localStorage.getItem("lastName") || ""
  );

  const navigate = useNavigate();

  const isFormValid = firstName !== "" && lastName !== "" && username !== "";

  const isManager: boolean = localStorage.getItem("role") === "MANAGER";

  const handleUpdateProfile = async () => {
    try {
      const userId = localStorage.getItem("userId") as unknown as number;
      const payload: UserUpdateRequest = { username, firstName, lastName };

      const updatedUser = await updateUserProfile(userId, payload);

      const loadingToast = toast.loading(
        "You are being redirected to your dashboard..."
      );
      toast.success("Profile updated successfully!");

      setTimeout(() => {
        navigate(`/${isManager ? "manager" : "employee"}/reimbursements`);
        toast.dismiss(loadingToast);
      }, 1000);

      localStorage.setItem("firstName", updatedUser.firstName);
      localStorage.setItem("lastName", updatedUser.lastName);
      localStorage.setItem("username", updatedUser.username);

      console.log("Updated user:", updatedUser);
    } catch (error: any) {
      if (error.message) {
        toast.error(error.message);
      } else {
        toast.error("Failed to update profile. Please try again.");
      }
      console.error(error.message);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (isFormValid) {
      handleUpdateProfile();
    }
  };

  return (
    <PageWrapper>
      <h1 className="text-2xl font-bold text-gray-900 mb-4">Update Profile</h1>
      <p className="text-sm text-gray-600 mb-4">
        <span className="text-red-500">*</span> Fields marked with{" "}
        <span className="text-red-500">*</span> are mandatory.
      </p>
      <form onSubmit={handleSubmit}>
        <label className="text-left">
          <span className="text-gray-700 block mb-2">First Name:</span>
          <Input
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            type="text"
            placeholder="First Name *"
            required
          />
        </label>
        <label className="text-left">
          <span className="text-gray-700 block mb-2">Last Name:</span>
          <Input
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            type="text"
            placeholder="Last Name *"
            required
          />
        </label>
        <label className="text-left">
          <span className="text-gray-700 block mb-2">Username:</span>
          <Input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            type="text"
            placeholder="Username *"
            required
          />
        </label>
        <Button
          type="submit"
          isActive={isFormValid}
          className="bg-green-600 hover:bg-green-700 mt-4"
        >
          Update Profile
        </Button>
      </form>
      <p className="text-gray-700 my-8">
        Go back to your{" "}
        <Link
          to={
            isManager ? "/manager/reimbursements" : "/employee/reimbursements"
          }
          className="text-blue-600 hover:text-blue-800 hover:underline active:no-underline transition-colors duration-300 ease-in-out"
        >
          dashboard
        </Link>
      </p>
    </PageWrapper>
  );
};

export { ProfilePage };
