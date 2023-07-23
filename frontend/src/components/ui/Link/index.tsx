import styled from 'styled-components';
import { NavLink } from 'react-router-dom';

const StyledNavLink = styled(NavLink)`
  /* Estilos do NavLink personalizados aqui */
  color: var(--white);
  text-decoration: none;
  display: block; /* Para ocupar a largura total disponível */

  /* Centralizar o texto horizontalmente */
  display: flex;
  align-items: center;
  justify-content: center;

  /* Espaçamento no topo */
  margin-top: 10px;

  &:hover {
    text-decoration: underline;
  }
`;

export default StyledNavLink;
