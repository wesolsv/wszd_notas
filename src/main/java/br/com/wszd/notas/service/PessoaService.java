package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.entity.Pessoa;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<PessoaDTO> listarTodasPessoas(){
        return repository.listaPessoas();
    }

    public PessoaDTO pegarPessoa(Long id){

        Pessoa pessoa = repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Pessoa não encontrada id = " + id ));

        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail());
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
