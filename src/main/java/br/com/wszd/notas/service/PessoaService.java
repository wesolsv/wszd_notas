package br.com.wszd.notas.service;

import br.com.wszd.notas.entity.Pessoa;
import br.com.wszd.notas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> listarTodasPessoas(){
        return repository.findAll();
    }

    public Optional<Pessoa> pegarPessoa(Long id){
        return repository.findById(id);
    }

    public Pessoa novaPessoa(Pessoa nova){
        return repository.save(nova);
    }

    public Pessoa editarPessoa(Long id, Pessoa nova){
        pegarPessoa(id);
        nova.setId(id);
        return repository.save(nova);
    }

    public void deletarPessoa(Long id){
        pegarPessoa(id);
        repository.deleteById(id);
    }
}
