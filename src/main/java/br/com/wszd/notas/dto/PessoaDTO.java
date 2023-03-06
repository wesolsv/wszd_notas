package br.com.wszd.notas.dto;

public class PessoaDTO {

    private Long id;
    private String nome;
    private String email;

    public PessoaDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public PessoaDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public PessoaDTO() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Builder{

        private Long id;
        private String nome;
        private String email;


        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }

        public PessoaDTO build(){
            return new PessoaDTO(this);
        }
    }

    private PessoaDTO(Builder builder){
        id = builder.id;
        nome = builder.nome;
        email = builder.email;
    }
}
