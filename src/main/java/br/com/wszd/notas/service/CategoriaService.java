package br.com.wszd.notas.service;

import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

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
        pegarCategoria(id);
        nova.setId(id);
        return repository.save(nova);
    }

    public void deletarCategoria(Long id){
        pegarCategoria(id);
        repository.deleteById(id);
    }
}
