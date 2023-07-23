import { InputHTMLAttributes } from 'react';
import { StyledInput } from './styles';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {}

const Input = ({ ...rest }: InputProps) => {
  return <StyledInput {...rest} />;
};

export default Input;