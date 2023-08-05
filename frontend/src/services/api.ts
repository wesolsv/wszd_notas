import axios, { AxiosError } from 'axios';
import { parseCookies } from 'nookies'
import { AuthTokenError } from './errors/AuthTokenError';
import { signOut } from '../contexts/AuthContext';

export function setupAPIClient(contexto = undefined) {
    let cookies = parseCookies(contexto);

    const api = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            Authorization: `Bearer ${cookies['@wszdauth.token']}`
        }
    })

    api.interceptors.response.use(response => {
        return response
    }, (error: AxiosError) => {
        if (!error?.response ||error.response.status === 401) {
            if (typeof window !== undefined) {
                signOut();
            } else {
                return Promise.reject(new AuthTokenError())
            }
        }

        return Promise.reject(error)
    })

    return api;
}