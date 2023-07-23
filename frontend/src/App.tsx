import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GlobalStyles from './styles/global';
import Login from './pages/Login';
import { AuthProvider } from './contexts/AuthContext';

function App() {
  return (
    <AuthProvider>
      <Router>
        <GlobalStyles />
        <Routes>
          <Route path="/" element={<Login />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
