package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.dto.UserRoleDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Logs;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Role;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.security.JWTCreator;
import br.com.wszd.notas.security.JWTObject;
import br.com.wszd.notas.security.SecurityConfig;
import br.com.wszd.notas.util.Operacoes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Lazy
    @Autowired
    private EmailService emailService;

    @Autowired
    private LogsService logsService;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void novoUsuario(Pessoa pessoa){

        Usuario usuario = newUser(pessoa);

        //Criando e atribuindo a role ao user
        addRoleInUser(usuario);

        gerarLog(Operacoes.ADICIONAR, Usuario.class.getSimpleName(), "Inclusão de novo usuário " + pessoa.getNome(), pessoa.getEmail());

        emailService.enviarEmailNovoUsuario(usuario);
    }

    public Usuario newUser(Pessoa pessoa) {
        Usuario usuario = new Usuario.Builder()
                .nomeUsuario(pessoa.getEmail())
                .senha(pessoa.getSenha())
                .pessoa(pessoa)
                .build();

        return repository.save(usuario);
    }

    public Usuario findUserByName(String nomeUsuario){

        try{
           return repository.findByNomeUsuario(nomeUsuario);
        }catch (ResourceObjectNotFoundException e){
            throw new ResourceObjectNotFoundException("Usuário não encotrado");
        }

    }

    public Usuario addRoleInUser(Usuario usuario) {
        log.info("Adicionando role ao usuario");

        List<Long> listIdRoles = Arrays.asList(1L);

        UserRoleDTO userRoleDTO = new UserRoleDTO(usuario.getId(), listIdRoles);

        List<Role> roles = userRoleDTO.getIdsRoles()
                .stream()
                .map(role -> new Role(role) ).collect(Collectors.toList());

        usuario.setRoles(roles);

        return repository.save(usuario);
    }

    public SessaoDTO validarLogin(UserLoginDTO infoLogin) {
        log.info("Validando senha e retornando sessao");
        Usuario usuario = findUserByName(infoLogin.getEmail());
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

    public Usuario retornaEmailUsuario(){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserByName(email+"");
    }

    public void editUser(Pessoa pessoa) {
        log.info("Editando usuario");
        Usuario usuario = repository.findByNomeUsuario(pessoa.getEmail());

        usuario.setNomeUsuario(pessoa.getEmail());
        usuario.setSenha(pessoa.getSenha());

        repository.save(usuario);
        gerarLog(Operacoes.EDITAR, usuario.getClass().getSimpleName(), "Editando usuário " + usuario.getNomeUsuario(), usuario.getNomeUsuario());

        emailService.enviarEmailEdicaoUsuario(usuario);
    }

    public void deleteUsuarioByNomeUsuario(PessoaDTO pessoa) {
        log.info("Deletando usuario");

        Usuario usuario = findUserByName(pessoa.getEmail());

        repository.deleteById(usuario.getId());
        gerarLog(Operacoes.DELETAR, usuario.getClass().getSimpleName(), "Deletando usuário pelo nome " + usuario.getNomeUsuario(), usuario.getNomeUsuario());

        emailService.enviarEmailUsuarioExcluido(usuario);
    }

    public void deleteUsuario(Long id) {
        log.info("Deletando usuario");
        repository.deleteById(id);
    }

    public void gerarLog(Operacoes operacao, String modulo, String detalhes, String nomeUsuario ){
        Logs log = new Logs(operacao, modulo, detalhes, nomeUsuario);
        logsService.salvarLog(log);
    }
}
