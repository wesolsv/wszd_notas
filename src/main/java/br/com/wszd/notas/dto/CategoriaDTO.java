package br.com.wszd.notas.dto;

public class CategoriaDTO {

    private Long id;

    private String nome;

    private PessoaDTO pessoaDTO;

    public CategoriaDTO(Long id, String nome, PessoaDTO pessoaDTO) {
        this.id = id;
        this.nome = nome;
        this.pessoaDTO = pessoaDTO;
    }

    public CategoriaDTO(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PessoaDTO getPessoa() {
        return pessoaDTO;
    }

    public void setPessoa(PessoaDTO pessoaDTO) {
        this.pessoaDTO = pessoaDTO;
    }
}


