package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.dto.UserRoleDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Role;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.security.JWTCreator;
import br.com.wszd.notas.security.JWTObject;
import br.com.wszd.notas.security.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void novoUsuario(Pessoa pessoa){
        List<Long> listIdRoles = Arrays.asList(1L);
        Usuario usuario = repository.save(new Usuario(pessoa.getEmail(), pessoa.getSenha(), pessoa));
        //Criando e atribuindo a role ao user
        UserRoleDTO userRoleDTO = new UserRoleDTO(usuario.getId(), listIdRoles);
        addRoleInUser(userRoleDTO);
        repository.save(usuario);
    }

    public void newUser(Usuario user) {
        String senha = user.getSenha();

        user.setSenha(passwordEncoder().encode(senha));
        repository.save(user);
    }

    public Usuario findUserByName(String nomeUsuario){

        try{
           return repository.findByNomeUsuario(nomeUsuario);
        }catch (ResourceObjectNotFoundException e){
            throw new ResourceObjectNotFoundException("Usuário não encotrado");
        }

    }

    public Usuario addRoleInUser(UserRoleDTO userRoleDTO) {
        log.info("Adicionando role ao usuario");
        Optional<Usuario> userExists = repository.findById(userRoleDTO.getIdUser());
        List<Role> roles = new ArrayList<>();

        if (userExists.isEmpty()) {
            throw new Error("User does not exists!");
        }

        roles = userRoleDTO.getIdsRoles().stream().map(role -> {
            return new Role(role);
        }).collect(Collectors.toList());

        Usuario usuario = userExists.get();

        usuario.setRoles(roles);

        repository.save(usuario);

        return usuario;
    }

    public SessaoDTO validarLogin(Usuario usuario, UserLoginDTO infoLogin) {
        log.info("Validando senha e retornando sessao");
        if(usuario!=null) {
            boolean passwordOk = passwordEncoder().matches(infoLogin.getSenha(),usuario.getSenha());
            if (!passwordOk) {
                throw new ResourceBadRequestException("Senha incorreta para o email: " + infoLogin.getEmail());
            }

            //Cria o objeto de sessão para retornar email e token do usuario
            SessaoDTO sessaoDTO = new SessaoDTO();
            sessaoDTO.setLogin(usuario.getNomeUsuario());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(usuario.getNomeUsuario());
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(usuario.getRoles());

            sessaoDTO.setToken(JWTCreator.createToken(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return sessaoDTO;
        }else {
            throw new ResourceBadRequestException("Erro ao tentar fazer login");
        }
    }

    public void editUser(Usuario usuario) {
        log.info("Editando usuario");
        repository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        log.info("Deletando usuario");
        repository.deleteById(id);
    }
}
