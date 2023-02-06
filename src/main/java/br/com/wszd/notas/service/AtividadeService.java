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

        validaRequisicao(id);
        Atividade atividade = pegarAtividade(id);

        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getConteudo(), atividade.getDataLembrete(), atividade.getStatus());
    }

    public Atividade novaAtividade(Atividade nova) {

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (nova.getDataLembrete() == null) {
            nova.setDataLembrete(LocalDateTime.now());
        }
        if (nova.getStatus() == null) {
            nova.setStatus(StatusAtividade.A);
        }

        gerarLog(Operacoes.ADICIONAR, nova.getClass().getSimpleName(), "Inclusão de nova atividade", email.toString());
        emailService.enviarEmailNovaAtividade(nova.getPessoa(), nova);
        return repository.save(nova);
    }

    public Atividade editarAtividade(Long id, Atividade nova) {
        validaRequisicao(id);

        Atividade atv = pegarAtividade(id);

        atv.setNome(nova.getNome());
        atv.setConteudo(nova.getConteudo());
        if (nova.getDataLembrete() != null) {
            atv.setDataLembrete(nova.getDataLembrete());
        }

        gerarLog(Operacoes.EDITAR, nova.getClass().getSimpleName(), "Edição de uma atividade " + atv.getNome(), atv.getPessoa().getEmail());
        return repository.save(atv);
    }

    public void cancelarAtividade(Long id) {

        validaRequisicao(id);
        Atividade atv = pegarAtividade(id);
        atv.setStatus(StatusAtividade.C);
        gerarLog(Operacoes.CANCELAR, atv.getClass().getSimpleName(), "Cancelamento da atividade " + atv.getNome(), atv.getPessoa().getEmail());
        repository.save(atv);
    }

    public boolean validaRequisicao(Long id) {
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());

        Atividade atv = pegarAtividade(id);

        if (atv.getPessoa().getId() != pessoa.getId()) {
            throw new ResourceBadRequestException("O usuário não tem acesso a esta atividade");
        }
        return true;
    }

    public void gerarLog(Operacoes operacao, String modulo, String detalhes, String nomeUsuario) {
        Logs log = new Logs(operacao, modulo, detalhes, nomeUsuario);
        logsService.salvarLog(log);
    }
}
