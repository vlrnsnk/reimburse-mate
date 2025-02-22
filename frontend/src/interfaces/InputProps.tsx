export interface InputProps {
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  type: string;
  placeholder: string;
  required?: boolean;
}
