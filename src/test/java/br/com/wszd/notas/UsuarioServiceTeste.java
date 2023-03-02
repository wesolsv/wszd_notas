package br.com.wszd.notas;

import br.com.wszd.notas.dto.SessaoDTO;
import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Role;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTeste {

    @Mock
    private UsuarioRepository repository;
    @InjectMocks
    private UsuarioService service;
    Usuario usuario;
    Pessoa pessoa;

    UserLoginDTO usuarioLogin;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@email.com", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@email.com", "123456", null);
        usuarioLogin = new UserLoginDTO("wes@email.com", "123456");
    }

    @Test
    public void deveCriarUsuario() throws Exception {
        when(repository.save(usuario)).thenReturn(usuario);
        usuario = service.newUser(usuario);

        assertNotNull(usuario);
        assertEquals("wes@test2e.com.br", usuario.getNomeUsuario());
    }

    @Test
    public void encontrarUsuario() throws Exception {
        when(repository.findByNomeUsuario(any())).thenReturn(new Usuario());
        usuario = service.findUserByName(usuario.getNomeUsuario());

        assertNotNull(usuario);
        verify(repository, times(1)).findByNomeUsuario(any());
    }

    @Test
    public void deveRealizarLogin() throws Exception{

        List<Role> roles = Arrays.asList(new Role(1L, "ADMIN"));

        usuario = new Usuario("wes@test2e.com.br",
                "$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK",
                new Pessoa());

        usuario.setRoles(roles);

        UserLoginDTO login = new UserLoginDTO("wes@test2e.com.br","123456");

        SessaoDTO session = new SessaoDTO();

        session = service.validarLogin(usuario, login);

        assertNotNull(session);
        assertEquals("wes@test2e.com.br", session.getLogin());
    }

    @Test
    public void deletarUsuario() throws Exception {
        service.deleteUsuario(Mockito.anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
