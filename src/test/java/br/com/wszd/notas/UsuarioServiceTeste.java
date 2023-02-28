package br.com.wszd.notas;

import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.UsuarioRepository;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTeste {

    @MockBean
    private UsuarioRepository repository;
    @Autowired
    private UsuarioService service;

    Usuario usuario;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test2e.com.br", "123456", new Pessoa());
    }

    @Test
    public void deveCriarUsuario() throws Exception {
        when(repository.save(usuario)).thenReturn(usuario);
        usuario = service.newUser(usuario);

        assertNotNull(usuario);
        assertEquals("wes@test2e.com.br", usuario.getNomeUsuario());
    }
}
