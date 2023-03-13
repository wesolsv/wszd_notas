package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.util.ValidacaoUsuarioLogged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private NotaService notaService;

    public List<Categoria> listarTodasCategorias(){
        return repository.findAll();
    }

    public Categoria pegarCategoria(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Categoria não encontrada"));
    }

    public Categoria pegarCategoriaByName(String categoriaNome, Long pessoaId) {
        return repository.pegarCategoriaByName(categoriaNome, pessoaId);
    }

    public Categoria novaCategoria(Categoria nova){
        return repository.save(nova);
    }

    public Categoria editarCategoria(Long id, Categoria nova){

        Categoria cat = pegarCategoria(id);

        ValidacaoUsuarioLogged.validarUsuarioCategoria(cat, cat.getPessoa());

        cat.setNome(nova.getNome());
        repository.save(cat);
        repository.atualizarCategoriaNome(cat.getNome(), cat);
        return cat;
    }

    public void deletarCategoria(Long id){
        notaService.deletarTodasNotasCategoria(pegarCategoria(id).getNome().toUpperCase());
        repository.deleteById(id);
    }

    public List<Categoria> listarCategoriasPessoa(Pessoa pessoa) {
        return repository.listarCategoriasPessoa(pessoa);
    }
}
