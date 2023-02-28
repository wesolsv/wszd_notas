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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTeste {

    @MockBean
    private UsuarioRepository repository;
    @Autowired
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
    public void findUsuario() throws Exception {
        Mockito.when(repository.findByNomeUsuario(Mockito.any())).thenReturn(new Usuario());
        usuario = service.findUserByName(usuario.getNomeUsuario());

        assertNotNull(usuario);
        verify(repository, Mockito.times(1)).findByNomeUsuario(Mockito.any());
    }
}
