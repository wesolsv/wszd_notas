package br.com.wszd.notas;

import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.PessoaRepository;
import br.com.wszd.notas.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PessoaServiceTeste {

    @Mock
    private PessoaRepository repository;
    @InjectMocks
    private PessoaService service;
    Usuario usuario;
    Pessoa pessoa;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test.com.br", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@test.com.br", "123456", null);
    }

    @Test
    public void deveCriarNovaPessoa() throws Exception {
        when(repository.save(pessoa)).thenReturn(pessoa);
        pessoa = service.novaPessoa(pessoa);

        assertNotNull(pessoa);
        assertEquals("wes@test.com.br", pessoa.getEmail());
        verify(repository, times(1)).save(pessoa);
    }

    @Test
    public void deveEditarPessoa() throws Exception {
        when(repository.save(pessoa)).thenReturn(pessoa);
        pessoa = service.editarPessoa(anyLong(), pessoa);

        assertNotNull(pessoa);
        assertEquals("wes@test.com.br", pessoa.getEmail());
        verify(repository, times(1)).save(pessoa);
    }

    @Test
    public void deletarUsuario() throws Exception {
        //service.deleteUsuario(Mockito.anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
