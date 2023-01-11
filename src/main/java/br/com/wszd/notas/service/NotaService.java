package br.com.wszd.notas.service;

import br.com.wszd.notas.entity.Nota;
import br.com.wszd.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    public List<Nota> listarTodasNotas(){
        return repository.findAll();
    }

    public Optional<Nota> pegarNota(Long id){
        return repository.findById(id);
    }

    public Nota novaNota(Nota nova){
        return repository.save(nova);
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

