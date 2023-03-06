package br.com.wszd.notas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ws_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pessoa")
    private Usuario usuario;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, String email, String senha, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.usuario = usuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static class Builder{

        private String nome;
        private String email;
        private String senha;

        private Usuario usuario;

        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder senha(String senha){
            this.senha = senha;
            return this;
        }
        public Builder usuario(Usuario usuario){
            this.usuario = usuario;
            return this;
        }

        public Pessoa build(){
            return new Pessoa(this);
        }
    }

    private Pessoa(Builder builder){
        nome = builder.nome;
        email = builder.email;
        senha = builder.senha;
        usuario = builder.usuario;
    }
}
