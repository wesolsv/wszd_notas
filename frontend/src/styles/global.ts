import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
  /* Reset básico */
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    outline: 0;
}

  /* Defina a fonte global */
  body {
    font-family: 'Roboto', sans-serif;
    background: var(--dark-700);
  }

  :root {
    --white: #FFF;
    --black: #000;

    --dark-900: #101026;
    --dark-700: #1d1d2e;

    --gray-100: #8a8a8a;
    --green-900: #3fffa3;
    --red-900: #ff3f4b;
}

button{
    cursor: pointer;
}

a{
    color: inherit;
    text-decoration: none;
}

body, input, textarea,select,button{
    font: 400 1rem sans-serif;
}

@media (max-width: 720px) {
    html {
        font-size: 87.5%;
    }
}

@media (max-width: 1080px) {
    html {
        font-size: 93.75%;
    }
}
`;

export default GlobalStyles;
