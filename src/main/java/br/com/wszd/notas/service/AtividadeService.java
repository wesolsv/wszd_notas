package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.repository.AtividadeRepository;
import br.com.wszd.notas.util.StatusAtividade;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<AtividadeDTO> listarTodasAtividades(){
        return repository.pegarTodasAtividades();
    }

    public Atividade pegarAtividade(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Atividade não encontrada"));
    }

    public AtividadeDTO pegarAtividadeDTO(Long id){

        validaRequisicao(id);
        Atividade atividade = pegarAtividade(id);

        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getConteudo(), atividade.getDataLembrete(), atividade.getStatus());
    }

    public Atividade novaAtividade(Atividade nova){

        if(nova.getDataLembrete() == null){
            nova.setDataLembrete(LocalDateTime.now());
        }
        if(nova.getStatus() == null){
            nova.setStatus(StatusAtividade.A);
        }

        return repository.save(nova);
    }

    public Atividade editarAtividade(Long id, Atividade nova){
        validaRequisicao(id);

        Atividade atv = pegarAtividade(id);

        atv.setNome(nova.getNome());
        atv.setConteudo(nova.getConteudo());
        if(nova.getDataLembrete() != null){
            atv.setDataLembrete(nova.getDataLembrete());
        }
        return repository.save(atv);
    }

    public void cancelarAtividade(Long id){

        validaRequisicao(id);
        Atividade atv = pegarAtividade(id);
        atv.setStatus(StatusAtividade.C);
        repository.save(atv);
    }

    public boolean validaRequisicao(Long id){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());

        Atividade atv = pegarAtividade(id);

        if(atv.getPessoa().getId() != pessoa.getId()){
            throw new ResourceBadRequestException("O usuário não tem acesso a esta atividade");
        }
        return true;
    }


}
