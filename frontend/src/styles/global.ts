import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
  /* Reset básico */
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  /* Defina a fonte global */
  body {
    font-family: 'Roboto', sans-serif;
    background-color: #333;
  }

  /* Outros estilos globais aqui... */
`;

export default GlobalStyles;
