import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const CustomLink = styled(Link)`
  /* Estilos do link personalizado aqui */
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

export default CustomLink;
