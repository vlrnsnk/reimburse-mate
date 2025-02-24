export interface ButtonProps {
  children: React.ReactNode;
  handleClick?: () => void;
  isActive?: boolean;
  type?: 'button' | 'submit' | 'reset';
  to?: string;
  className?: string;
}
