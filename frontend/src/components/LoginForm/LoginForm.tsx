import React from 'react';
import { FormContainer } from './LoginForm.styles';

const LoginForm = () => {
  // Lógica do formulário de login
  return (
    <FormContainer>
      {/* Campos de login (exemplo) */}
      <input type="text" placeholder="Usuário" />
      <input type="password" placeholder="Senha" />
      <button type="submit">Entrar</button>
    </FormContainer>
  );
};

export default LoginForm;
