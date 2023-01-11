package br.com.wszd.notas.repository.nota.model;

import br.com.wszd.notas.repository.categoria.model.Categoria;
import br.com.wszd.notas.repository.pessoa.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ws_notas")
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String nome;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataCriaco;

    @Column(name = "data_alteracao")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataAlteracao;

    @Column(name = "pessoa_id")
    private Pessoa pessoa;

    @Column(name = "categoria_id")
    private Categoria categoria;
}
