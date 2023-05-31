package br.com.wszd.notas.services;

import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.model.Usuario;
import br.com.wszd.notas.repository.AtividadeRepository;
import br.com.wszd.notas.service.*;
import br.com.wszd.notas.util.StatusAtividade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AtividadeServiceTeste {

    @MockBean
    private AtividadeRepository repository;

    @MockBean
    private PessoaService pessoaService;

    @MockBean
    private LogsService logsService;

    @Lazy
    @MockBean
    private EmailService emailService;

    @Lazy
    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private AtividadeService service;


    @Test
    public void deveCriarAtividade() throws Exception {

        Atividade atividade = mock(Atividade.class);
        Pessoa pessoa = mock(Pessoa.class);

        when(atividade.getNome()).thenReturn("Atividade");
        when(atividade.getConteudo()).thenReturn("Conteudo");
        when(atividade.getDataLembrete()).thenReturn(LocalDateTime.now());
        when(atividade.getStatus()).thenReturn(StatusAtividade.A);
        when(atividade.getPessoa()).thenReturn(pessoa);

        service.novaAtividade(atividade);

        verify(repository, times(1)).save(any(Atividade.class));
    }

    @Test
    public void deveRetornarAtividade() throws Exception {

        Atividade atividade = mock(Atividade.class);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(atividade));
        service.pegarAtividade(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deveEditaAtividade() throws Exception {

        Atividade atividade = mock(Atividade.class);
        Pessoa pessoa = mock(Pessoa.class);

        when(atividade.getNome()).thenReturn("Atividade");
        when(atividade.getConteudo()).thenReturn("Conteudo");
        when(atividade.getDataLembrete()).thenReturn(LocalDateTime.now());
        when(atividade.getStatus()).thenReturn(StatusAtividade.A);
        when(atividade.getPessoa()).thenReturn(pessoa);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(atividade));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(repository.save(atividade)).thenReturn(atividade);
        service.editarAtividade(anyLong(), atividade);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Atividade.class));
    }

    @Test
    public void cancelarAtividade() throws Exception {

        Atividade atividade = mock(Atividade.class);
        Pessoa pessoa = mock(Pessoa.class);

        when(atividade.getNome()).thenReturn("Atividade");
        when(atividade.getConteudo()).thenReturn("Conteudo");
        when(atividade.getDataLembrete()).thenReturn(LocalDateTime.now());
        when(atividade.getStatus()).thenReturn(StatusAtividade.A);
        when(atividade.getPessoa()).thenReturn(pessoa);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(atividade));
        when(usuarioService.retornaEmailUsuario()).thenReturn(new Usuario());
        when(repository.save(atividade)).thenReturn(atividade);

        service.cancelarAtividade(anyLong());

        verify(repository, times(1)).save(atividade);
    }
}
