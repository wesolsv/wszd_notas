package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void novoUsuario(Usuario usuario){
        String senha = usuario.getSenha();

        usuario.setSenha(passwordEncoder().encode(senha));
        repository.save(usuario);
    }
}
