package br.com.wszd.notas;

import br.com.wszd.notas.dto.UserLoginDTO;
import br.com.wszd.notas.dto.UserRoleDTO;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Role;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTeste {

    @Mock
    private UsuarioRepository repository;
    @InjectMocks
    private UsuarioService service;

    @Test
    public void deveCriarUsuario() throws Exception {

        Pessoa pessoa = mock(Pessoa.class);
        when(pessoa.getEmail()).thenReturn("email@email.com");
        when(pessoa.getSenha()).thenReturn("123456");

        service.newUser(pessoa);

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void deveAdicionarRoleUsuario() throws Exception {
        List<Role> roles = Arrays.asList(new Role(1L, "ADMIN"));

        Usuario usuario = mock(Usuario.class);

        when(usuario.getId()).thenReturn(0L);
        when(usuario.getNomeUsuario()).thenReturn("wes@test2e.com.br");
        when(usuario.getSenha()).thenReturn("$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK");
        when(usuario.getPessoa()).thenReturn(new Pessoa());
        when(usuario.getRoles()).thenReturn(roles);

        UserRoleDTO userRoleDTO = mock(UserRoleDTO.class);
        when(userRoleDTO.getIdUser()).thenReturn(0L);

        service.addRoleInUser(usuario);

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void encontrarUsuario() throws Exception {
        Usuario usuario = mock(Usuario.class);

        when(usuario.getNomeUsuario()).thenReturn("testename");
        when(repository.findByNomeUsuario(anyString())).thenReturn(new Usuario());
        service.findUserByName(usuario.getNomeUsuario());

        verify(repository, times(1)).findByNomeUsuario(anyString());
    }

    @Test
    public void deveRealizarLogin() throws Exception {
        List<Role> roles = Arrays.asList(new Role(1L, "ADMIN"));

        Usuario usuario = mock(Usuario.class);

        when(usuario.getNomeUsuario()).thenReturn("wes@test2e.com.br");
        when(usuario.getSenha()).thenReturn("$2a$08$sOOxkOE/arGYc6N1IBdzxO8kaWB7HWqlg/mhANhGeazRdDALX9vWK");
        when(usuario.getPessoa()).thenReturn(new Pessoa());
        when(usuario.getRoles()).thenReturn(roles);

        UserLoginDTO login = mock(UserLoginDTO.class);
        when(login.getEmail()).thenReturn("wes@test2e.com.br");
        when(login.getSenha()).thenReturn("123456");

        when(repository.findByNomeUsuario(anyString())).thenReturn(usuario);

        service.validarLogin(login);

        verify(repository, times(1)).findByNomeUsuario(anyString());
    }

    @Test
    public void editarUsuario() throws Exception {

    }

    @Test
    public void deletarUsuario() throws Exception {
        service.deleteUsuario(Mockito.anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
