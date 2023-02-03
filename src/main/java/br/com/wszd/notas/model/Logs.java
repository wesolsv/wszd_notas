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
    private OperacoesCRUD operacao;

    @Column(name = "modulo")
    private String modulo;

    @Column(name = "detalhes")
    private String detalhes;

    @Column(name = "usuario_id")
    private Usuario usuario;

    public Logs() {
    }

    public Logs(Long id, OperacoesCRUD operacao, String modulo, String detalhes, Usuario usuario) {
        this.id = id;
        this.operacao = operacao;
        this.modulo = modulo;
        this.detalhes = detalhes;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
