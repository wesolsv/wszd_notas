import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // adicionar a lógica para enviar os dados de login o backend

    // Resetar os campos de email e senha após o envio
    setEmail('');
    setPassword('');
  };

  return (
    <div className="login-container">
      <h2>Login Notes</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            placeholder='Digite seu e-mail'
            type="email"
            id="email"
            value={email}
            onChange={handleEmailChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha:</label>
          <input
            placeholder='Digite sua senha e-mail'
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
        </div>
        <button type="submit">Entrar</button>
        <br/>
        <Link to="/cadastro">Cadastrar novo usuario</Link>
      </form>    
    </div>
  );
};

export default Login;
