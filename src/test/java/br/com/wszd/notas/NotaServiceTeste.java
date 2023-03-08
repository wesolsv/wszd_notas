package br.com.wszd.notas;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.*;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.repository.NotaRepository;
import br.com.wszd.notas.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotaServiceTeste {

    @MockBean
    private NotaRepository repository;
    @Lazy
    @MockBean
    private CategoriaService categoriaService;
    @MockBean
    private PessoaService pessoaService;
    @Lazy
    @MockBean
    private LogsService logsService;
    @MockBean
    public UsuarioService usuarioService;
    @MockBean
    private EmailService emailService;
    @Autowired
    private NotaService service;

    Usuario usuario;
    Pessoa pessoa;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test.com.br", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@test.com.br", "123456", null);
    }

    @Test
    public void deveCriarNovaNota() throws Exception {

        Pessoa pessoa = new Pessoa.Builder()
                .nome("teste pessoa")
                .email("teste@pessoa.com")
                .senha("anystring")
                .usuario(new Usuario())
                .build();

        Categoria categoria = new Categoria("TESTE", pessoa);
        Nota nota = mock(Nota.class);

        when(nota.getPessoa()).thenReturn(pessoa);
        when(nota.getCategoria()).thenReturn(categoria);

        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(repository.save(nota)).thenReturn(nota);
        when(categoriaService.pegarCategoriaByName(anyString(), anyLong())).thenReturn(categoria);
        when(pessoaService.pegarPessoaDTO(anyLong())).thenReturn(new PessoaDTO());
        service.novaNota(nota);

        verify(repository, times(1)).save(any(Nota.class));
    }

    @Test
    public void deveRetornarNota() throws Exception{

        Nota nota = mock(Nota.class);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(nota));
        nota = service.pegarNotaCompleta(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deveEditarNota() throws Exception {

        Pessoa pessoa = new Pessoa.Builder()
                .nome("teste pessoa")
                .email("teste@pessoa.com")
                .senha("anystring")
                .usuario(new Usuario())
                .build();

        Categoria categoria = new Categoria("TESTE", pessoa);
        Nota nota = mock(Nota.class);

        when(nota.getPessoa()).thenReturn(pessoa);
        when(nota.getCategoria()).thenReturn(categoria);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(nota));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(repository.save(nota)).thenReturn(nota);
        when(categoriaService.pegarCategoriaByName(anyString(), anyLong())).thenReturn(categoria);
        service.editarNota(anyLong(), nota);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Nota.class));
    }

    @Test
    public void deletarUsuario() throws Exception {

        Pessoa pessoa = new Pessoa.Builder()
                .nome("teste pessoa")
                .email("teste@pessoa.com")
                .senha("anystring")
                .usuario(new Usuario())
                .build();

        Categoria categoria = new Categoria("TESTE", pessoa);
        Nota nota = mock(Nota.class);

        when(nota.getPessoa()).thenReturn(pessoa);
        when(nota.getCategoria()).thenReturn(categoria);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(nota));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(categoriaService.pegarCategoriaByName(anyString(), anyLong())).thenReturn(categoria);
        service.deletarNota(anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
