package br.com.wszd.notas.util;

import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Slf4j
public class ValidacaoEmailUsuario {

    public static void validarEmailUsuario(Pessoa pessoa, Usuario usuario) {
        log.info("Validando usuario");

        ArrayList<String> rolesRetorno = new ArrayList<>();

        for (int i = 0; i < usuario.getRoles().size(); i++) {
            String j = usuario.getRoles().get(i).getNome() + "";
            rolesRetorno.add(j);
        }

        if (rolesRetorno.contains("ADMIN") || pessoa.getId().equals(usuario.getPessoa().getId())) {
            log.info("Validado email do usuario ou usuario é admin");
        } else {
            throw new ResourceBadRequestException("O usuário utilizado não tem acesso a este recurso");
        }
    }
}
