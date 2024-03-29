package br.com.wszd.notas.services;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.PessoaRepository;
import br.com.wszd.notas.service.PessoaService;
import br.com.wszd.notas.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PessoaServiceTeste {

    @MockBean
    private PessoaRepository repository;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private PessoaService service;

    @Test
    @DisplayName("deve criar uma nova pessoa")
    public void deveCriarNovaPessoa() throws Exception {

        Pessoa pessoa = mock(Pessoa.class);
        PessoaDTO p = mock(PessoaDTO.class);

        when(pessoa.getSenha()).thenReturn("123456");
        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);
        when(repository.findByEmail("teste@teste.com")).thenReturn(p);
        service.novaPessoa(pessoa);

        verify(repository, times(1)).save(any(Pessoa.class));
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

        Pessoa pessoa = new Pessoa.Builder()
                .nome("teste pessoa")
                .email("teste@pessoa.com")
                .senha("anystring")
                .usuario(new Usuario())
                .build();

        Pessoa p = mock(Pessoa.class);

        when(repository.save(any(Pessoa.class))).thenReturn(p);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(p));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        service.editarPessoa(0L, pessoa);
        verify(repository, times(1)).save(any(Pessoa.class));
        verify(repository, times(1)).findById(anyLong());
    }
}
