import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './Login';
import Cadastro from './Cadastro';

const Rotas = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
      </Routes>
    </BrowserRouter>
  );
};

export default Rotas;
