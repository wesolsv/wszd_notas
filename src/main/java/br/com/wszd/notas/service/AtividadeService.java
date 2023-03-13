package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.model.Logs;
import br.com.wszd.notas.repository.AtividadeRepository;
import br.com.wszd.notas.util.Operacoes;
import br.com.wszd.notas.util.StatusAtividade;
import br.com.wszd.notas.util.ValidacaoUsuarioLogged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private LogsService logsService;

    @Lazy
    @Autowired
    private EmailService emailService;

    @Lazy
    @Autowired
    private UsuarioService usuarioService;


    public List<Atividade> listAtividades() {
        return repository.findAll();
    }

    public List<AtividadeDTO> listarTodasAtividades() {
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());
        return repository.pegarTodasAtividades(pessoaService.pegarPessoa(pessoa.getId()));
    }

    public List<AtividadeDTO> listarTodasAtividadesPorStatus(StatusAtividade status) {
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());

        return repository.listarTodasAtividadesPorStatus(pessoaService.pegarPessoa(pessoa.getId()), status);
    }

    public Atividade pegarAtividade(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Atividade não encontrada"));
    }

    public AtividadeDTO pegarAtividadeDTO(Long id) {

        Atividade atividade = pegarAtividade(id);
        ValidacaoUsuarioLogged.validarUsuarioAtividade(atividade, usuarioService.retornaEmailUsuario());

        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getConteudo(), atividade.getDataLembrete(), atividade.getStatus());
    }

    public Atividade novaAtividade(Atividade nova) {

        Atividade atv = new Atividade.Builder()
                .nome(nova.getNome())
                .conteudo(nova.getConteudo())
                .dataLembrete(nova.getDataLembrete())
                .status(nova.getStatus())
                .pessoa(nova.getPessoa())
                .build();

        if (atv.getDataLembrete() == null) {
            atv.setDataLembrete(LocalDateTime.now());
        }
        if (atv.getStatus() == null) {
            atv.setStatus(StatusAtividade.A);
        }

        gerarLog(Operacoes.ADICIONAR, nova.getClass().getSimpleName(), "Inclusão de nova atividade", atv.getPessoa().getEmail());
        emailService.enviarEmailNovaAtividade(atv.getPessoa(), atv);
        return repository.save(atv);
    }

    public Atividade editarAtividade(Long id, Atividade nova) {

        Atividade atividade = pegarAtividade(id);
        ValidacaoUsuarioLogged.validarUsuarioAtividade(atividade, usuarioService.retornaEmailUsuario());

        Atividade atv = new Atividade.Builder()
                .nome(nova.getNome())
                .conteudo(nova.getConteudo())
                .dataLembrete(nova.getDataLembrete())
                .status(nova.getStatus())
                .pessoa(atividade.getPessoa())
                .build();

        if (nova.getDataLembrete() != null) {
            atv.setDataLembrete(nova.getDataLembrete());
        }

        gerarLog(Operacoes.EDITAR, nova.getClass().getSimpleName(), "Edição de uma atividade " + atv.getNome(), atv.getPessoa().getEmail());
        return repository.save(atv);
    }

    public void cancelarAtividade(Long id) {

        Atividade atv = pegarAtividade(id);
        ValidacaoUsuarioLogged.validarUsuarioAtividade(atv, usuarioService.retornaEmailUsuario());

        atv.setStatus(StatusAtividade.C);
        gerarLog(Operacoes.CANCELAR, atv.getClass().getSimpleName(), "Cancelamento da atividade " + atv.getNome(), atv.getPessoa().getEmail());
        repository.save(atv);
    }


    public void gerarLog(Operacoes operacao, String modulo, String detalhes, String nomeUsuario) {
        Logs log = new Logs(operacao, modulo, detalhes, nomeUsuario);
        logsService.salvarLog(log);
    }
}
