package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceInternalException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PessoaService pessoaService;

    public List<NotaDTO> listarTodasNotas(){
        return repository.pegarTodasNotas();
    }

    public NotaDTO pegarNota(Long id){
        try{
            return repository.pegarNota(id);
        }catch(ResourceObjectNotFoundException e){
            throw new ResourceObjectNotFoundException("Não encontrada com id = " + id);
        }
    }

    public Nota pegarNotaCompleta(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Nota não encontrada"));
    }

    public Nota novaNota(Nota nova){
        Nota notaAjustada = ajusteCategoria(nova);

        nova.setDataCriacao(LocalDateTime.now());
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
            nova.setCategoriaNome(nova.getCategoria().getNome());
            return nova;
        }

        Categoria padraoCat = categoriaService.pegarCategoriaByName(nova.getCategoriaNome(), nova.getPessoa());

        if(padraoCat != null){
            nova.setCategoria(padraoCat);
        }else{
            padraoCat = new Categoria(nova.getCategoriaNome(), nova.getPessoa());
            categoriaService.novaCategoria(padraoCat);
            nova.setCategoria(padraoCat);
        }
        nova.setCategoriaNome(nova.getCategoria().getNome());
        return nova;
    }

    public Nota editarNota(Long id, Nota nova){

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());
        Nota nota = pegarNotaCompleta(id);
        if(nota.getPessoa().getId() == pessoa.getId()){
            nota.setNome(nova.getNome());
            nota.setConteudo(nova.getConteudo());
            nota.setDataAlteracao(LocalDateTime.now());
        }else{
            throw new ResourceInternalException("O usuário não tem acesso a nota");
        }
        if(nova.getCategoriaNome() != null){
            Nota n = ajusteCategoria(nova);
            nota.setCategoria(n.getCategoria());
            nota.setCategoriaNome(n.getCategoriaNome());
            return repository.save(nota);
        }else{
            throw new ResourceInternalException("Não foi possível incluir a categoria");
        }

    }

    public void deletarNota(Long id){
        pegarNotaCompleta(id);
        repository.deleteById(id);
    }
}

