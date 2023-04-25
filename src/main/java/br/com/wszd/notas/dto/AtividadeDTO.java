package br.com.wszd.notas.dto;

import br.com.wszd.notas.util.StatusAtividade;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AtividadeDTO {

    private Long id;

    private String nome;

    private String conteudo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataLembrete;

    private StatusAtividade status;

    public AtividadeDTO() {
    }

    public AtividadeDTO(Long id, String nome, String conteudo, LocalDateTime dataLembrete, StatusAtividade status) {
        this.id = id;
        this.nome = nome;
        this.conteudo = conteudo;
        this.dataLembrete = dataLembrete;
        this.status = status;
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
}
