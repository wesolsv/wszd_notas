package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Pessoa;
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

    public void novoUsuario(Pessoa pessoa){
        String senha = pessoa.getSenha();

        pessoa.setSenha(passwordEncoder().encode(senha));
        Usuario usuario = new Usuario(pessoa.getEmail(), pessoa.getSenha(), pessoa);
        repository.save(usuario);
    }

    public void newUser(Usuario user) {
        String senha = user.getSenha();

        user.setSenha(passwordEncoder().encode(senha));
        repository.save(user);
    }
}
