import { FormEvent, useState, useContext } from 'react';
import { CustomLink } from '../ui/Link';
import { FormContainer } from './styles';
import Input from '../ui/Input';
import { Button } from '../ui/Button';
import { AuthContext } from '../../contexts/AuthContext';

const LoginForm = () => {
  // Lógica do formulário de login
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [loading, setLoading] = useState(false);

  const { signIn } = useContext(AuthContext);

  async function handleLogin(event: FormEvent){
    event.preventDefault();

    let data = {
      email:"teste@teste.com",
      password:"123456"
    }

    await signIn(data)
  }

  return (
    <>
      <FormContainer onSubmit={handleLogin}>
        <Input type="email" placeholder="Digite seu Email" />
        <Input type="password" placeholder="Senha" />
        <Button type="submit" loading={loading}>Acessar</Button>

        <CustomLink to="/signup">
          Não possui uma conta? Cadastre-se
        </CustomLink>
      </FormContainer>
    </>
  );
};

export default LoginForm;
