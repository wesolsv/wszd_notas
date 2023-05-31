package br.com.wszd.notas.model;

import br.com.wszd.notas.util.StatusAtividade;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ws_atividades")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome da atividade não pode ser nulo.")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "O conteúdo não pode ser nulo.")
    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "data_lembrete")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataLembrete;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAtividade status;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Atividade() {
    }

    public Atividade(Long id, String nome, String conteudo, LocalDateTime dataLembrete, StatusAtividade status, Pessoa pessoa) {
        this.id = id;
        this.nome = nome;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public static class Builder{

        private String nome;
        private String conteudo;
        private LocalDateTime dataLembrete;
        private StatusAtividade status;
        private Pessoa pessoa;

        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }
        public Builder conteudo(String conteudo){
            this.conteudo = conteudo;
            return this;
        }
        public Builder dataLembrete(LocalDateTime dataLembrete){
            this.dataLembrete = dataLembrete;
            return this;
        }

        public Builder status(StatusAtividade status){
            this.status = status;
            return this;
        }

        public Builder pessoa(Pessoa pessoa){
            this.pessoa = pessoa;
            return this;
        }


        public Atividade build(){
            return new Atividade(this);
        }
    }

    private Atividade(Builder builder){
        nome = builder.nome;
        conteudo = builder.conteudo;
        dataLembrete = builder.dataLembrete;
        status = builder.status;
        pessoa = builder.pessoa;
    }
}
