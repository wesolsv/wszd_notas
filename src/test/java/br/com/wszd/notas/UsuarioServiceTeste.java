package br.com.wszd.notas;

import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test2e.com.br", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@test3ee.com.br", "123456", null);
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
    public void deletarUsuario() throws Exception {
        service.deleteUsuario(Mockito.anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
