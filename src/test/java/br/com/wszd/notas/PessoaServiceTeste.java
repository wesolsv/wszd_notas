package br.com.wszd.notas;

import br.com.wszd.notas.dto.PessoaDTO;
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
    public void deveRetornarPessoa() throws Exception{
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(pessoa));
        pessoa = service.pegarPessoa(anyLong());

        assertNotNull(pessoa);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deveRetornarPessoaByEmail() throws Exception{
        when(repository.findByEmail(any())).thenReturn(new PessoaDTO());
        service.pessoaByEmail(any());

        verify(repository, times(1)).findByEmail(any());
    }

//    @Test
//    public void deveEditarPessoa() throws Exception {
//        pessoa.setEmail("email@alterado.com");
//        when(repository.save(pessoa)).thenReturn(pessoa);
//        Pessoa p = service.editarPessoa(anyLong(), pessoa);
//
//        assertNotNull(pessoa);
//        assertEquals("wes@test.com.br", pessoa.getEmail());
//        verify(repository, times(1)).save(pessoa);
//    }

//    @Test
//    public void deletarUsuario() throws Exception {
//        service.deletarPessoa(anyLong());
//
//        verify(repository, times(1)).deleteById(any());
//    }
}
