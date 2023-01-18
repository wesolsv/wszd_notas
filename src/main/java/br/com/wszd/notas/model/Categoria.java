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

    @Column(name = "name")
    private String nome;

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Categoria(){
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
}


