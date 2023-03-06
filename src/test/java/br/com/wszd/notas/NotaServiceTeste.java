package br.com.wszd.notas;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.repository.NotaRepository;
import br.com.wszd.notas.service.CategoriaService;
import br.com.wszd.notas.service.NotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotaServiceTeste {

    @Mock
    private NotaRepository repository;
    @InjectMocks
    private NotaService service;

    Usuario usuario;
    Pessoa pessoa;
    Nota nota;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario("wes@test.com.br", "123456", new Pessoa());
        pessoa = new Pessoa(null, "Teste","wes@test.com.br", "123456", null);
        nota = new Nota("Primeira Nota", "Descrição aleatória", new Pessoa(), new Categoria("PADRAO", pessoa));
    }

//    @Test
//    public void deveCriarNovaNota() throws Exception {
//        when(repository.save(nota)).thenReturn(nota);
//        nota = service.novaNota(nota);
//
//        assertNotNull(nota);
//        assertEquals("Primeira Nota", pessoa.getNome());
//        verify(repository, times(1)).save(nota);
//    }

    @Test
    public void deveRetornarNota() throws Exception{
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(nota));
        nota = service.pegarNotaCompleta(anyLong());

        assertNotNull(nota);
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

//    @Test
//    public void deletarUsuario() throws Exception {
//        service.deletarNota(anyLong());
//
//        verify(repository, times(1)).deleteById(any());
//    }
}
