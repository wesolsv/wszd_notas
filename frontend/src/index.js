import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import Cadastro from './components/Cadastro';
import Login from './components/Login';
import Rotas from './components/Rotas';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
    <Cadastro />
    <Login />
    <Rotas />
  </React.StrictMode>
);
