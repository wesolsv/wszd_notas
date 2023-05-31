package br.com.wszd.notas.model;

import javax.persistence.*;

@Entity
@Table(name = "ws_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Role() {
    }

    public Role(Long id){
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.nome = name;
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
