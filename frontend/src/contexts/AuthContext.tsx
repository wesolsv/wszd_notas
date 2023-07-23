import { createContext, ReactNode, useState, useEffect } from "react";
import { destroyCookie, setCookie, parseCookies } from 'nookies';
// import { useNavigate } from 'react-router-dom';
import { api } from "../services/apiClient";
import { toast } from 'react-toastify';

type AuthContextData = {
  user: UserProps;
  isAuthenticated: boolean;
  signIn: (credentials: SignInProps) => Promise<void>
  signOut: () => void;
}

type UserProps = {
  email: string;
}

type SignInProps = {
  email: string;
  senha: string;
}

type AuthProviderProps = {
  children?: ReactNode;
}

export const AuthContext = createContext({} as AuthContextData)

export function signOut() {
  try {
    destroyCookie(undefined, '@wszdauth.token');
    //window.location.href = '/';
  } catch (error) {
    console.log("erro ao deslogar")
  }
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<UserProps>()
  const isAuthenticated = !!user;

  async function signIn({ email, senha }: SignInProps) {
    try {
      const response = await api.post('/usuario/login', {
        email, senha
      })

      const { token } = response.data;
      setCookie(undefined, '@wszdauth.token', token, {
        maxAge: 60 * 60 * 24 * 30, //Expira em 1 mÊs
        path: "/" //Quais caminhso terao acesso ao cookie
      })

      setUser({
        email
      })

      //Passar para próximas requisições o token

      api.defaults.headers['Authorization'] = `Bearer ${token}`

      //Redirecionar o usuario para outra página
      window.location.href = '/dashboard';

    } catch (error) {
      console.log("ERRO AO ACESSAR")
    }
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  )
}