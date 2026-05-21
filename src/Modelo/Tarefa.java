package Modelo;

import java.io.Serializable;

public class Tarefa implements Entidade {

    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String descricao;
    private PrioridadeTarefa prioridade;
    private StatusTarefa status;
    private Usuario responsavel;
    private Projeto projeto;

    public Tarefa(String titulo, String descricao,
                  PrioridadeTarefa prioridade, StatusTarefa status,
                  Usuario responsavel, Projeto projeto) {
        this(0, titulo, descricao, prioridade, status, responsavel, projeto);
    }

    public Tarefa(int id, String titulo, String descricao,
                  PrioridadeTarefa prioridade, StatusTarefa status,
                  Usuario responsavel, Projeto projeto) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.responsavel = responsavel;
        this.projeto = projeto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PrioridadeTarefa getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeTarefa prioridade) {
        this.prioridade = prioridade;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public String toString() {
        String nomeResponsavel = responsavel == null ? "Sem responsavel" : responsavel.getNomeCompleto();
        String nomeProjeto = projeto == null ? "Sem projeto" : projeto.getNome();
        return "ID: " + id +
                " | Tarefa: " + titulo +
                " | Prioridade: " + prioridade +
                " | Status: " + status +
                " | Responsavel: " + nomeResponsavel +
                " | Projeto: " + nomeProjeto;
    }
}
