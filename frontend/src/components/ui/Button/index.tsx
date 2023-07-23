import { ReactNode, ButtonHTMLAttributes } from 'react';
import { StyledButton } from './styles';

import { FaSpinner } from 'react-icons/fa'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    loading?: boolean,
    children: ReactNode,
}

export function Button({ loading, children, ...rest }: ButtonProps) {
    return (
        <StyledButton disabled={loading} {...rest}>
            {loading ? (<FaSpinner color="#fff" size={16} />) : (<span>{children}</span>)}
        </StyledButton>
    )
}