import { BrowserRouter, Routes, Route  } from 'react-router-dom';
import Login from './pages/Login';
import Cadastro from './pages/Cadastro';
import ListNotas from './pages/Notas';
import Nota from './pages/Notas/Nota';

const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/list-notas" element={<ListNotas />} />
        <Route path="/list-notas/nota" element={<Nota />} />
      </Routes>
    </BrowserRouter>
  );
};

export default RoutesApp;
