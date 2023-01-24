package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.AtividadeDTO;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository repository;


    public List<AtividadeDTO> listarTodasAtividades(){
        return repository.pegarTodasAtividades();
    }

    public Atividade pegarAtividade(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Atividade não encontrada"));
    }

    public AtividadeDTO pegarAtividadeDTO(Long id){
        Atividade atividade = repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Atividade não encontrada"));;
        return new AtividadeDTO(atividade.getId(), atividade.getNome(), atividade.getConteudo(), atividade.getDataLembrete(), atividade.getStatus());
    }

    public Atividade novaAtividade(Atividade nova){
        nova.setDataLembrete(LocalDateTime.now());
        return repository.save(nova);
    }

    public Atividade editarAtividade(Long id, Atividade nova){
        pegarAtividade(id);
        nova.setId(id);
        return repository.save(nova);
    }

    public void deletarAtividade(Long id){
        pegarAtividade(id);
        repository.deleteById(id);
    }

}
