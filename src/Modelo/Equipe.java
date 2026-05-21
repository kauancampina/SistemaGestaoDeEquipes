package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipe implements Entidade {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private boolean ativa;
    private final List<Usuario> membros;
    private final List<Projeto> projetos;

    public Equipe(String nome, String descricao) {
        this(0, nome, descricao, true);
    }

    public Equipe(int id, String nome, String descricao, boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativa = ativa;
        this.membros = new ArrayList<>();
        this.projetos = new ArrayList<>();
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

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<Usuario> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    public List<Projeto> getProjetos() {
        return Collections.unmodifiableList(projetos);
    }

    public void adicionarMembro(Usuario usuario) {
        if (usuario != null && !membros.contains(usuario)) {
            membros.add(usuario);
        }
    }

    public void removerMembro(Usuario usuario) {
        membros.remove(usuario);
    }

    public void adicionarProjeto(Projeto projeto) {
        if (projeto != null && !projetos.contains(projeto)) {
            projetos.add(projeto);
        }
    }

    public void removerProjeto(Projeto projeto) {
        projetos.remove(projeto);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Equipe: " + nome +
                " | Descricao: " + descricao +
                " | Ativa: " + (ativa ? "Sim" : "Nao") +
                " | Membros: " + membros.size() +
                " | Projetos: " + projetos.size();
    }
}
