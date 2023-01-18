package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Atividade;
import br.com.wszd.notas.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository repository;


    public List<Atividade> listarTodasAtividades(){
        return repository.findAll();
    }

    public Optional<Atividade> pegarAtividade(Long id){
        return repository.findById(id);
    }

    public Atividade novaAtividade(Atividade nova){
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
