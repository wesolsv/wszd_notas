package br.com.wszd.notas.util;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.service.PessoaService;
import br.com.wszd.notas.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ValidacaoUsuarioLogged {

    public static UsuarioService usuarioService;
    public static PessoaService pessoaService;

    public static Usuario usuario;

    public static void validarEmailPessoaUsuario(Pessoa pessoa) {
        log.info("Validando pessoa/usuario");

        usuario = usuarioService.retornaEmailUsuario();

        ArrayList<String> rolesRetorno = new ArrayList<>();

        if (usuario.getRoles() != null) {
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                String j = usuario.getRoles().get(i).getNome() + "";
                rolesRetorno.add(j);
            }
        }
        if (usuario.getPessoa() != null) {
            if (rolesRetorno.contains("ADMIN") || pessoa.getId().equals(usuario.getPessoa().getId())) {
                log.info("Validado email do usuario ou usuario é admin");
            } else {
                throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
            }
        }
    }

    public static void validarUsuarioNota(Nota nota) {
        log.info("Validando usuario");

        usuario = usuarioService.retornaEmailUsuario();

        if(nota.getPessoa().getId() != usuario.getPessoa().getId()){
            throw new ResourceBadRequestException("O usuário não tem acesso a esta nota");
        }
    }
}