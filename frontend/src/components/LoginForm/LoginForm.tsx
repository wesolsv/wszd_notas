import React from 'react';

const LoginForm = () => {
  // Lógica do formulário de login
  return (
    <form>
      {/* Campos de login (exemplo) */}
      <input type="email" placeholder="Usuário" />
      <input type="password" placeholder="Senha" />
      <button type="submit">Entrar</button>
    </form>
  );
};

export default LoginForm;
