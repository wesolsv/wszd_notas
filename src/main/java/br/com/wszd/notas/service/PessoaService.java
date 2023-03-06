package br.com.wszd.notas.service;

import br.com.wszd.notas.dto.PessoaDTO;
import br.com.wszd.notas.exception.ResourceBadRequestException;
import br.com.wszd.notas.exception.ResourceObjectNotFoundException;
import br.com.wszd.notas.model.Categoria;
import br.com.wszd.notas.model.Logs;
import br.com.wszd.notas.model.Pessoa;
import br.com.wszd.notas.repository.PessoaRepository;
import br.com.wszd.notas.util.Operacoes;
import br.com.wszd.notas.util.ValidacaoEmailUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PessoaService {

    @Autowired
    private PessoaRepository repository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LogsService logsService;

    @Lazy
    @Autowired
    private CategoriaService categoriaService;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<PessoaDTO> listarTodasPessoas(){
        return repository.listaPessoas();
    }

    public PessoaDTO pegarPessoaDTO(Long id){

        Pessoa pessoa = repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Pessoa não encontrada id = " + id ));

        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail());
    }

    public Pessoa pegarPessoa(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceObjectNotFoundException("Pessoa não encontrada"));
    }

    public PessoaDTO pessoaByEmail(String email){
        PessoaDTO pessoa = repository.findByEmail(email);
        if( pessoa == null){
            throw new ResourceBadRequestException("Usuário não encontrado");
        }

        return pessoa;
    }

    public PessoaDTO novaPessoaDTO(Pessoa nova){

        Pessoa pessoaNova = novaPessoa(nova);

        usuarioService.novoUsuario(pessoaNova);

        gerarLog(Operacoes.ADICIONAR, pessoaNova.getClass().getSimpleName(), "Inclusão de nova pessoa", nova.getEmail());

        return new PessoaDTO.Builder()
                .id(pessoaNova.getId())
                .nome(pessoaNova.getNome())
                .email(pessoaNova.getEmail())
                .build();
    }

    public Pessoa novaPessoa(Pessoa nova){
        log.info("Adicionando nova pessoa!");

        if(repository.findByEmail(nova.getEmail()) != null){
            throw new ResourceBadRequestException("Email já cadastrado, verfique seus dados");
        }

        Pessoa pessoaCriada = new Pessoa.Builder()
                .nome(nova.getNome())
                .email(nova.getEmail())
                .senha((passwordEncoder().encode(nova.getSenha())))
                .usuario(nova.getUsuario())
                .build();

        return repository.save(pessoaCriada);
    }

    public Pessoa editarPessoa(Long id, Pessoa nova){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ValidacaoEmailUsuario.validarEmailUsuario(pegarPessoa(id), usuarioService.findUserByName(email.toString()));

        nova.setSenha(passwordEncoder().encode(nova.getSenha()));
        nova.setId(id);
        Pessoa pessoaNova = repository.save(nova);

        usuarioService.editUser(pessoaNova);

        gerarLog(Operacoes.EDITAR, pessoaNova.getClass().getSimpleName(), "Edição de pessoa", nova.getEmail());

        return pessoaNova;
    }

    public void deletarPessoa(Long id){
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ValidacaoEmailUsuario.validarEmailUsuario(pegarPessoa(id), usuarioService.findUserByName(email.toString()));

        List<Categoria> listCat = categoriaService.listarCategoriasPessoa(pegarPessoa(id));

        if(!listCat.isEmpty()){
            listCat.forEach(categoria -> {
                categoriaService.deletarCategoria(categoria.getId());
            });
        }

        PessoaDTO pessoa = pegarPessoaDTO(id);
        usuarioService.deleteUsuarioByNomeUsuario(pessoa);

        gerarLog(Operacoes.DELETAR, pegarPessoa(id).getClass().getSimpleName(), "Delete de pessoa", pessoa.getEmail());

        repository.deleteById(id);
    }

    public void gerarLog(Operacoes operacao, String modulo, String detalhes, String nomeUsuario ){
        Logs log = new Logs(operacao, modulo, detalhes, nomeUsuario);
        logsService.salvarLog(log);
    }
}
