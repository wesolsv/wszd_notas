package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    NotaService notaService;

    public List<Categoria> listarTodasCategorias(){
        return repository.findAll();
    }

    public Categoria pegarCategoria(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Categoria não encontrada"));
    }

    public Categoria pegarCategoriaByName(String categoriaNome, Pessoa pessoa) {
        return repository.pegarCategoriaByName(categoriaNome, pessoa);
    }

    public Categoria novaCategoria(Categoria nova){
        return repository.save(nova);
    }

    public Categoria editarCategoria(Long id, Categoria nova){

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());
        Categoria cat = pegarCategoria(id);
        if(cat.getPessoa().getId() != pessoa.getId()){
            throw new ResourceBadRequestException("O usuário não tem acesso a este recurso");
        }
        cat.setNome(nova.getNome());
        repository.save(cat);
        repository.atualizarCategoriaNome(cat.getNome(), cat);
        return cat;
    }

    public void deletarCategoria(Long id){
        notaService.deletarTodasNotasCategoria(pegarCategoria(id).getNome().toUpperCase());
        repository.deleteById(id);
    }
}
