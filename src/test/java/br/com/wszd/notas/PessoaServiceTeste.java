package br.com.wszd.notas;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.PessoaRepository;
import br.com.wszd.notas.service.CategoriaService;
import br.com.wszd.notas.service.LogsService;
import br.com.wszd.notas.service.PessoaService;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PessoaServiceTeste {

    @MockBean
    private PessoaRepository repository;
    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private LogsService logsService;
    @Lazy
    @MockBean
    private CategoriaService categoriaService;
    @Autowired
    private PessoaService service;

    @Test
    @DisplayName("deve criar uma nova pessoa")
    public void deveCriarNovaPessoa() throws Exception {

        Pessoa pessoa = mock(Pessoa.class);

        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);
        service.novaPessoa(pessoa);

        verify(repository, times(1)).save(pessoa);
    }

    @Test
    @DisplayName("deve criar uma nova pessoa")
    public void deveRetornarPessoa() throws Exception{
        Pessoa pessoa = mock(Pessoa.class);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(pessoa));
        service.pegarPessoa(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deveRetornarPessoaByEmail() throws Exception{
        when(repository.findByEmail(any())).thenReturn(new PessoaDTO());
        service.pessoaByEmail(anyString());

        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    public void deveEditarPessoa() throws Exception {
        pessoa.setEmail("email@alterado.com");
        when(repository.save(pessoa)).thenReturn(pessoa);
        Pessoa p = service.editarPessoa(anyLong(), pessoa);

        assertNotNull(pessoa);
        assertEquals("wes@test.com.br", pessoa.getEmail());
        verify(repository, times(1)).save(pessoa);
    }

//    @Test
//    public void deletarUsuario() throws Exception {
//        service.deletarPessoa(anyLong());
//
//        verify(repository, times(1)).deleteById(any());
//    }
}
