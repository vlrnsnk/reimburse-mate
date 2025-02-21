export interface ButtonProps {
  children: React.ReactNode;
  handleClick?: () => void;
  isActive?: boolean;
  to?: string;
  className?: string;
}
