package br.com.wszd.notas.model;

import br.com.wszd.notas.util.OperacoesCRUD;

import javax.persistence.*;

@Entity
@Table(name = "ws_logs")
public class Logs {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "operacao")
    @Enumerated(EnumType.STRING)
    private OperacoesCRUD operacao;

    @Column(name = "modulo")
    private String modulo;

    @Column(name = "detalhes")
    private String detalhes;

    @Column(name = "usuario_id")
    private String nomeUsuario;

    public Logs() {
    }

    public Logs(Long id, OperacoesCRUD operacao, String modulo, String detalhes, String nomeUsuario) {
        this.id = id;
        this.operacao = operacao;
        this.modulo = modulo;
        this.detalhes = detalhes;
        this.nomeUsuario = nomeUsuario;
    }

    public Logs( OperacoesCRUD operacao, String modulo, String detalhes, String nomeUsuario) {
        this.operacao = operacao;
        this.modulo = modulo;
        this.detalhes = detalhes;
        this.nomeUsuario = nomeUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperacoesCRUD getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacoesCRUD operacao) {
        this.operacao = operacao;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getUsuario() {
        return nomeUsuario;
    }

    public void setUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
