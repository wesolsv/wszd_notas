package br.com.wszd.notas.services;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.service.CategoriaService;
import br.com.wszd.notas.service.NotaService;
import br.com.wszd.notas.service.PessoaService;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoriaServiceTeste {

    @MockBean
    private CategoriaRepository repository;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private NotaService notaService;
    @Lazy
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private CategoriaService service;

    @Test
    public void deveCriarNovaCategoria() throws Exception {

        Categoria categoria = mock(Categoria.class);

        when(repository.save(categoria)).thenReturn(categoria);
        categoria = service.novaCategoria(categoria);
        verify(repository, times(1)).save(categoria);
    }

    @Test
    public void deveRetornarCategoria() throws Exception {

        Categoria categoria = mock(Categoria.class);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(categoria));
        service.pegarCategoria(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deveEditarCategoria() throws Exception {

        Categoria categoria = mock(Categoria.class);

        when(repository.save(categoria)).thenReturn(categoria);
        when(repository.findById(anyLong())).thenReturn(Optional.of(categoria));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(categoria.getNome()).thenReturn("TESTE");
        service.editarCategoria(anyLong(), categoria);

        verify(repository, times(1)).save(categoria);
    }

    @Test
    public void deletarCategoria() throws Exception {

        Categoria categoria = mock(Categoria.class);

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoria));
        when(categoria.getNome()).thenReturn("TESTE");
        service.deletarCategoria(anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
