package br.com.wszd.notas.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ws_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "pessoa_id")
    private Pessoa pessoa;

    public Categoria(Long id, String nome, Pessoa pessoa) {
        this.id = id;
        this.nome = nome;
        this.pessoa = pessoa;
    }

    public Categoria(String padrao, Pessoa pessoa) {
        this.nome = nome;
        this.pessoa = pessoa;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Categoria(){
    }

}


