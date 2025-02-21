export interface ButtonProps {
  children: React.ReactNode;
  handleClick?: () => void;
  to?: string;
  className?: string;
  isActive?: boolean;
}
