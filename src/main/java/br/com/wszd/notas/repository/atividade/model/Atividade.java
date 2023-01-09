package br.com.wszd.notas.repository.atividade.model;

import br.com.wszd.notas.repository.pessoa.Pessoa;
import br.com.wszd.notas.util.StatusAtividade;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "ws_atividades", schema = "noteswszd")
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_lembrete")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataLembrete;

    @Column(name = "status")
    private StatusAtividade status;

    @Column(name = "ws_pessoa_id")
    private Pessoa pessoa;
}
