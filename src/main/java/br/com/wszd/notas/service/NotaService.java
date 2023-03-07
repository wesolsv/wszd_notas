package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.NotaDTO;
import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceInternalException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Logs;
import br.com.wszd.notas.model.Nota;
import br.com.wszd.notas.repository.NotaRepository;
import br.com.wszd.notas.util.Operacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Lazy
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;
    @Lazy
    @Autowired
    private LogsService logsService;

    public List<Nota> listarNotas(){
        return repository.findAll();
    }

    public List<NotaDTO> listarTodasNotas(){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());
        return repository.pegarTodasNotas(pessoaService.pegarPessoa(pessoa.getId()));
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

        notaAjustada = new Nota.Builder()
                .nome(notaAjustada.getNome())
                .conteudo(notaAjustada.getConteudo())
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .pessoa(notaAjustada.getPessoa())
                .categoria(notaAjustada.getCategoria())
                .categoriaNome(notaAjustada.getCategoriaNome())
                .build();

        gerarLog(Operacoes.ADICIONAR, nova.getClass().getSimpleName(), "Adicionando uma nota " + notaAjustada.getNome(), notaAjustada.getPessoa().getEmail());
        emailService.enviarEmailNovaNota(nova.getPessoa(), notaAjustada);

        return repository.save(nova);
    }

    public Nota ajusteCategoria(Nota nova){
        if(nova.getCategoriaNome() == null || nova.getCategoriaNome().equals("")){
            Categoria padraoCat = categoriaService.pegarCategoriaByName("PADRAO", nova.getPessoa().getId());

            nova.setCategoria(padraoCat);
            nova.setCategoriaNome(nova.getCategoria().getNome());

            return nova;
        }

        Categoria categoria = categoriaService.pegarCategoriaByName(nova.getCategoriaNome(), nova.getPessoa().getId());

        if(categoria != null){
            nova.setCategoria(categoria);
        }else{
            categoria = new Categoria(nova.getCategoriaNome(), nova.getPessoa());
            nova.setCategoria(categoriaService.novaCategoria(categoria));
        }
        nova.setCategoriaNome(nova.getCategoria().getNome());
        return nova;
    }

    public Nota editarNota(Long id, Nota novaNota){
        validaRequisicao(id);
        Nota notaAntiga = pegarNotaCompleta(id);

        Nota objBuilderNota = new Nota.Builder()
                .nome(novaNota.getNome())
                .conteudo(novaNota.getConteudo())
                .dataCriacao(notaAntiga.getDataCriacao())
                .dataAlteracao(LocalDateTime.now())
                .pessoa(novaNota.getPessoa())
                .categoria(novaNota.getCategoria())
                .categoriaNome(novaNota.getCategoriaNome())
                .build();

        objBuilderNota.setId(id);

        try{
            objBuilderNota = ajusteCategoria(objBuilderNota);

            gerarLog(Operacoes.EDITAR, novaNota.getClass().getSimpleName(), "Editando a nota " + notaAntiga.getNome(), notaAntiga.getPessoa().getEmail());

            return repository.save(objBuilderNota);
        }catch (ResourceInternalException e){
            throw new ResourceInternalException("Não foi possível incluir a categoria");
        }
    }

    public void deletarNota(Long id){
        validaRequisicao(id);
        Nota nota = pegarNotaCompleta(id);

        gerarLog(Operacoes.DELETAR, nota.getClass().getSimpleName(), "Deletando a nota " + nota.getNome(), nota.getPessoa().getEmail());

        repository.deleteById(id);
    }

    public void deletarTodasNotasCategoria(String nomeCategoria){

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<NotaDTO> notas = repository.pegarTodasNotasCategoria(nomeCategoria.toUpperCase());

        if(!notas.isEmpty()){
            notas.forEach(notaDTO -> {
                repository.deleteById(notaDTO.getId());
            });
        }

        gerarLog(Operacoes.DELETAR, "Nota", "Deletando todas as notas por categoria" , email.toString());
    }

    public void deletarTodasNotasIds(List<Long> ids){

        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<NotaDTO> notas = repository.pegarTodasNotasIds(ids);

        if(!notas.isEmpty()){
            notas.forEach(notaDTO -> {
                repository.deleteById(notaDTO.getId());
            });
        }

        gerarLog(Operacoes.DELETAR, "Nota", "Deletando todas as notas por ids" , email.toString());
    }

    public boolean validaRequisicao(Long id){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PessoaDTO pessoa = pessoaService.pessoaByEmail(email.toString());

        Nota nota = pegarNotaCompleta(id);

        if(nota.getPessoa().getId() != pessoa.getId()){
            throw new ResourceBadRequestException("O usuário não tem acesso a esta nota");
        }
        return true;
    }

    public void gerarLog(Operacoes operacao, String modulo, String detalhes, String nomeUsuario) {
        Logs log = new Logs(operacao, modulo, detalhes, nomeUsuario);
        logsService.salvarLog(log);
    }
}

