package br.com.wszd.notas.services;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.NotaRepository;
import br.com.wszd.notas.repository.PessoaRepository;
import br.com.wszd.notas.service.CategoriaService;
import br.com.wszd.notas.service.NotaService;
import br.com.wszd.notas.service.PessoaService;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class NotaServiceTeste {

    @MockBean
    private NotaRepository repository;
    @MockBean
    private PessoaRepository pessoaRepository;
    @Lazy
    @MockBean
    private CategoriaService categoriaService;
    @MockBean
    private PessoaService pessoaService;
    @MockBean
    public UsuarioService usuarioService;
    @Autowired
    private NotaService service;

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
    public void deveRetornarNota() throws Exception {

        Nota nota = mock(Nota.class);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(nota));
        service.pegarNotaCompleta(anyLong());

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
    public void deveRetornarListaNotas() throws Exception {

        Pessoa pessoa = mock(Pessoa.class);
        PessoaDTO pessoaDTO = mock(PessoaDTO.class);
        Usuario usuario = mock(Usuario.class);

        when(usuario.getNomeUsuario()).thenReturn("email@email.com");
        when(pessoa.getEmail()).thenReturn("email@email.com");
        when(pessoaDTO.getId()).thenReturn(0L);
        when(pessoaService.pessoaByEmail(anyString())).thenReturn(pessoaDTO);
        when(pessoaService.pegarPessoa(0L)).thenReturn(pessoa);
        when(pessoaRepository.findByEmail(anyString())).thenReturn(pessoaDTO);
        when(repository.pegarTodasNotas(pessoa)).thenReturn(Collections.emptyList());
        when(usuarioService.retornaEmailUsuario()).thenReturn(usuario);

        service.listarTodasNotas();

        verify(repository, times(1)).pegarTodasNotas(pessoa);
    }

    @Test
    public void deletarNota() throws Exception {

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
