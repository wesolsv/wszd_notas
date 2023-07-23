import styled from 'styled-components';

export const StyledInput = styled.input`
  margin-bottom: 1rem;
  height: 40px;
  border: 0;
  border-radius: 0.5rem;
  background-color: var(--dark-900);
  color: var(--white);
  padding: 1rem;
  border: 1px solid var(--gray-100);

  &::placeholder {
    color: rgba(255, 255, 255, 0.8);
  }
`;