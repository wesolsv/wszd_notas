package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public void novoUsuario(Usuario usuario){
        String senha = usuario.getSenha();

        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }
}
