package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    public List<Nota> listarTodasNotas(){
        return repository.findAll();
    }

    public Optional<Nota> pegarNota(Long id){
        return repository.findById(id);
    }

    public Nota novaNota(Nota nova){
        if(nova.getCategoriaNome() == null || nova.getCategoriaNome() == ""){
           nova.setCategoria(ajusteCategoria("", nova.getPessoa()));
        }
        Categoria cat = categoriaService.pegarCategoriaByName(nova.getCategoriaNome());
        if( cat !=  null){
            nova.setCategoria(cat);
        }else {
            nova.setCategoria(categoriaService.novaCategoria(new Categoria(nova.getCategoriaNome(), nova.getPessoa())));
        }
        return repository.save(nova);
    }

    public Categoria ajusteCategoria(String categoriaNome, Pessoa pessoa){
        Categoria cat = categoriaService.pegarCategoriaByName("PADRAO");
        if(cat != null){
            return cat;
        }
        return categoriaService.novaCategoria(new Categoria("PADRAO", pessoa));
    }

    public Nota editarNota(Long id, Nota nova){
        pegarNota(id);
        nova.setId(id);
        return repository.save(nova);
    }

    public void deletarNota(Long id){
        pegarNota(id);
        repository.deleteById(id);
    }
}

