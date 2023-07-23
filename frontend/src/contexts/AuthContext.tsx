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

export function signOut(){
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
      console.log(response.data)
      
    } catch (error) {
      
    }
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  )
}