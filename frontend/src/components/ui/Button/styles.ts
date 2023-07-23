import styled, { keyframes } from 'styled-components';

const rotate360 = keyframes`
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
`;

export const StyledButton  = styled.button`
  max-width: 600px;
  background-color: var(--red-900);
  border: 0;
  padding: 0.4rem;
  color: var(--white);
  border-radius: 0.5rem;
  transition: filter 0.2s;

  &:disabled {
    cursor: not-allowed;

    svg {
      animation: ${rotate360} 2s infinite;
    }
  }

  &:hover {
    cursor: pointer;
    filter: brightness(1.08);
  }
`;
