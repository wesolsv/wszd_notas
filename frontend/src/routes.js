import { BrowserRouter, Routes, Route  } from 'react-router-dom';
import Login from './pages/Login';
import Cadastro from './pages/Cadastro';

const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
      </Routes>
    </BrowserRouter>
  );
};

export default RoutesApp;
