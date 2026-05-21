package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Projeto implements Entidade {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private StatusProjeto status;
    private Usuario gerente;
    private final List<Tarefa> tarefas;
    private final List<Equipe> equipes;

    public Projeto(String nome, String descricao, LocalDate dataInicio,
                   LocalDate dataTermino, StatusProjeto status, Usuario gerente) {
        this(0, nome, descricao, dataInicio, dataTermino, status, gerente);
    }

    public Projeto(int id, String nome, String descricao, LocalDate dataInicio,
                   LocalDate dataTermino, StatusProjeto status, Usuario gerente) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.status = status;
        this.gerente = gerente;
        this.tarefas = new ArrayList<>();
        this.equipes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public void setStatus(StatusProjeto status) {
        this.status = status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public List<Tarefa> getTarefas() {
        return Collections.unmodifiableList(tarefas);
    }

    public List<Equipe> getEquipes() {
        return Collections.unmodifiableList(equipes);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        if (tarefa != null && !tarefas.contains(tarefa)) {
            tarefas.add(tarefa);
            tarefa.setProjeto(this);
        }
    }

    public void removerTarefa(Tarefa tarefa) {
        if (tarefas.remove(tarefa) && tarefa != null) {
            tarefa.setProjeto(null);
        }
    }

    public void adicionarEquipe(Equipe equipe) {
        if (equipe != null && !equipes.contains(equipe)) {
            // Mantem os dois lados do relacionamento N:N sincronizados no dominio.
            equipes.add(equipe);
            equipe.adicionarProjeto(this);
        }
    }

    public void removerEquipe(Equipe equipe) {
        if (equipes.remove(equipe) && equipe != null) {
            equipe.removerProjeto(this);
        }
    }

    @Override
    public String toString() {
        String nomeGerente = gerente == null ? "Sem gerente" : gerente.getNomeCompleto();
        return "ID: " + id +
                " | Projeto: " + nome +
                " | Status: " + status +
                " | Gerente: " + nomeGerente +
                " | Tarefas: " + tarefas.size() +
                " | Equipes: " + equipes.size();
    }
}
