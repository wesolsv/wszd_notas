package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listarTodasCategorias(){
        return repository.findAll();
    }

    public Optional<Categoria> pegarCategoria(Long id){
        return repository.findById(id);
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
