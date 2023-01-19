package br.com.wszd.notas.service;

import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.CategoriaRepository;
import br.com.wszd.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Nota notaAjustada = ajusteCategoria(nova);

        nova.setDataCriaco(LocalDateTime.now());
        nova.setDataAlteracao(LocalDateTime.now());
        notaAjustada.setCategoriaNome(notaAjustada.getCategoria().getNome());

        return repository.save(nova);
    }

    public Nota ajusteCategoria(Nota nova){
        if(nova.getCategoriaNome() == null || nova.getCategoriaNome().equals("")){
            Categoria padraoCat = categoriaService.pegarCategoriaByName("PADRAO", nova.getPessoa());
            if(padraoCat != null){
                nova.setCategoria(padraoCat);
            }else{
                padraoCat = new Categoria("PADRAO", nova.getPessoa());
                categoriaService.novaCategoria(padraoCat);
                nova.setCategoria(padraoCat);
            }
        }
        nova.setCategoriaNome(nova.getCategoria().getNome());
        return nova;
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

