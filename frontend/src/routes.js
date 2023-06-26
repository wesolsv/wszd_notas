import { BrowserRouter, Routes, Route  } from 'react-router-dom';
import Login from './pages/Login';
import Cadastro from './pages/Cadastro';
import ListNotas from './pages/Notas';
import Nota from './pages/Notas/Nota';
import Header from './componentes/Header';
import Erro from './pages/Erro';

const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/"element={<Login />} />
        <Route path="/cadastro" element={<Cadastro />} />
        <Route path="/list-notas" element={<ListNotas />} />
        <Route path="/list-notas/nota" element={<Nota />} />

        <Route path="*" element={<Erro/>}/>
      </Routes>
    </BrowserRouter>
  );
};

export default RoutesApp;
