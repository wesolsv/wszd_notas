package br.com.wszd.notas.model;

import br.com.wszd.notas.util.StatusAtividade;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ws_atividades")
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

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Atividade() {
    }

    public Atividade(Long id, String conteudo, LocalDateTime dataLembrete, StatusAtividade status, Pessoa pessoa) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataLembrete = dataLembrete;
        this.status = status;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataLembrete() {
        return dataLembrete;
    }

    public void setDataLembrete(LocalDateTime dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    public StatusAtividade getStatus() {
        return status;
    }

    public void setStatus(StatusAtividade status) {
        this.status = status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
