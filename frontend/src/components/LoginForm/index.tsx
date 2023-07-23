import { FormEvent, useState, useContext } from 'react';
import { CustomLink } from '../ui/Link';
import { FormContainer } from './styles';
import Input from '../ui/Input';
import { Button } from '../ui/Button';

const LoginForm = () => {
  // Lógica do formulário de login
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [loading, setLoading] = useState(false);

  return (
    <>
      <FormContainer>
        <Input type="email" placeholder="Digite seu Email" />
        <Input type="password" placeholder="Senha" />
        <Button type="submit" loading={loading}>Entrar</Button>

        <CustomLink to="/signup">
          Não possui uma conta? Cadastre-se
        </CustomLink>
      </FormContainer>
    </>
  );
};

export default LoginForm;
