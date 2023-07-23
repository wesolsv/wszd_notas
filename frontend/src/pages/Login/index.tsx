import { useContext } from 'react';
import Logo from '../../components/Logo/Logo';
import LoginForm from '../../components/LoginForm';
import { LoginContainer } from './styles';
import Head from '../../components/ui/Head';

const Login = () => {
  return (
    <>
      <Head title="Login" />

      <LoginContainer>
        <Logo />
        <LoginForm />
      </LoginContainer>
    </>
  );
};

export default Login;
