package br.com.wszd.notas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ws_notas")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JsonIgnoreProperties("pessoa")
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("pessoa")
    private Categoria categoria;

    @Column(name = "categoria_nome")
    private String categoriaNome;

    public Nota() {
    }

    public Nota(String nome, String conteudo, Pessoa pessoa, Categoria categoria) {
        this.nome = nome;
        this.conteudo = conteudo;
        this.pessoa = pessoa;
        this.categoria = categoria;
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public Nota(Long id, String nome, String conteudo, LocalDateTime dataCriacao, LocalDateTime dataAlteracao, Pessoa pessoa, Categoria categoria, String categoriaNome) {
        this.id = id;
        this.nome = nome;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
        this.pessoa = pessoa;
        this.categoria = categoria;
        this.categoriaNome = categoriaNome;
    }


    public static class Builder{
        
        private String nome;
        private String conteudo;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAlteracao;
        private Pessoa pessoa;
        private Categoria categoria;
        private String categoriaNome;

        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }
        public Builder conteudo(String conteudo){
            this.conteudo = conteudo;
            return this;
        }
        public Builder dataCriacao(LocalDateTime dataCriacao){
            this.dataCriacao = dataCriacao;
            return this;
        }
        
        public Builder dataAlteracao(LocalDateTime dataAlteracao){
            this.dataAlteracao = dataAlteracao;
            return this;
        }
        public Builder pessoa(Pessoa pessoa){
            this.pessoa = pessoa;
            return this;
        }
        public Builder categoria(Categoria categoria){
            this.categoria = categoria;
            return this;
        }
        public Builder categoriaNome(String categoriaNome){
            this.categoriaNome = categoriaNome;
            return this;
        }

        public Nota build(){
            return new Nota(this);
        }
    }

    private Nota(Builder builder){
        nome = builder.nome;
        conteudo = builder.conteudo;
        dataCriacao = builder.dataCriacao;
        dataAlteracao = builder.dataAlteracao;
        pessoa = builder.pessoa;
        categoria = builder.categoria;
        categoriaNome = builder.categoriaNome;
    }
}
