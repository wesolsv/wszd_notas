import { FormEvent, useState, useContext } from 'react';
import { FormContainer } from './styles';
import Input from '../ui/Input';
import { Button } from '../ui/Button';
import { AuthContext } from '../../contexts/AuthContext';
import StyledNavLink from '../ui/Link';

const LoginForm = () => {

  const { signIn } = useContext(AuthContext);

  // Lógica do formulário de login
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const [loading, setLoading] = useState(false);

  async function handleLogin(event: FormEvent){
    event.preventDefault();

    let data = {
      email,
      senha
    }
    
    await signIn(data)
    alert("teste")
  }

  return (
    <>
      <FormContainer onSubmit={handleLogin}>
        <Input 
          type="email" 
          placeholder="Digite seu Email" 
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <Input 
          type="password" 
          placeholder="Senha" 
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <Button type="submit" loading={loading}>Acessar</Button>

        <StyledNavLink  to="/signup">
          Não possui uma conta? Cadastre-se
        </StyledNavLink >
      </FormContainer>
    </>
  );
};

export default LoginForm;
