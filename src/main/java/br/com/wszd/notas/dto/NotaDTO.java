package br.com.wszd.notas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class NotaDTO {

    private Long id;

    private String nome;

    private String conteudo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataAlteracao;

    private String categoriaNome;

    public NotaDTO() {
    }

    public NotaDTO(Long id, String nome, String conteudo, LocalDateTime dataCriaco, LocalDateTime dataAlteracao, String categoriaNome) {
        this.id = id;
        this.nome = nome;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriaco;
        this.dataAlteracao = dataAlteracao;
        this.categoriaNome = categoriaNome;
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

    public LocalDateTime getDataCriaco() {
        return dataCriacao;
    }

    public void setDataCriaco(LocalDateTime dataCriaco) {
        this.dataCriacao = dataCriaco;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
