package br.com.wszd.notas;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.service.CategoriaService;
import br.com.wszd.notas.service.NotaService;
import br.com.wszd.notas.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoriaServiceTeste {

    @MockBean
    private CategoriaRepository repository;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private NotaService notaService;
    @Autowired
    private CategoriaService service;

    Usuario usuario;
    Pessoa pessoa;
    Nota nota;
    Categoria categoria;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test.com.br", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@test.com.br", "123456", null);
        nota = new Nota("Primeira Nota", "Descrição aleatória", new Pessoa(), new Categoria("PADRAO", pessoa));
        categoria = new Categoria("TESTE CAT", pessoa);
    }

    @Test
    public void deveCriarNovaCategoria() throws Exception {
        when(repository.save(categoria)).thenReturn(categoria);
        categoria = service.novaCategoria(categoria);

        assertNotNull(nota);
        assertEquals("TESTE CAT", categoria.getNome());
        verify(repository, times(1)).save(categoria);
    }

    @Test
    public void deveRetornarCategoria() throws Exception{
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(categoria));
        categoria = service.pegarCategoria(anyLong());

        assertNotNull(categoria);
        verify(repository, times(1)).findById(anyLong());
    }

//    @Test
//    public void deveEditarNota() throws Exception {
//        nota.setNome("Teste edição");
//        when(repository.save(nota)).thenReturn(nota);
//        Nota n = service.editarNota(anyLong(), nota);
//
//        assertNotNull(nota);
//        assertEquals("Teste edição", nota.getNome());
//        verify(repository, times(1)).save(nota);
//    }

    @Test
    public void deletarCategoria() throws Exception {
        System.out.println("teste");
        service.deletarCategoria(anyLong());

        verify(repository, times(1)).deleteById(any());
    }
}
