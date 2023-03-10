package br.com.wszd.notas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ws_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @Column(name = "senha")
    private String senha;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties("usuario")
    private Pessoa pessoa;

    @ManyToMany
    private List<Role> roles;

    public Usuario() {
    }

    public Usuario( String nomeUsuario, String senha, List<Role> roles) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.roles = roles;
    }

    public Usuario( String nomeUsuario, String senha, Pessoa pessoa) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public static class Builder{
        private String senha;
        private String nomeUsuario;
        private Pessoa pessoa;
        private List<Role> roles;

        public Builder nomeUsuario(String nomeUsuario){
            this.nomeUsuario = nomeUsuario;
            return this;
        }
        public Builder senha(String senha){
            this.senha = senha;
            return this;
        }
        public Builder pessoa(Pessoa pessoa){
            this.pessoa = pessoa;
            return this;
        }
        public Builder roles(List<Role> roles){
            this.roles = roles;
            return this;
        }

        public Usuario build(){
            return new Usuario(this);
        }
    }

    private Usuario(Usuario.Builder builder){
        nomeUsuario = builder.nomeUsuario;
        senha = builder.senha;
        pessoa = builder.pessoa;
        roles = builder.roles;
    }
}
