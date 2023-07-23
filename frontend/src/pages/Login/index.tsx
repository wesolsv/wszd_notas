import React from 'react';
import Logo from '../../components/Logo/Logo';
import LoginForm from '../../components/LoginForm';
import { LoginContainer } from './styles';

const Login = () => {
  return (
    <LoginContainer>
      <Logo />
      <LoginForm />
    </LoginContainer>
  );
};

export default Login;
