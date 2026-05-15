package Modelo;

import java.util.ArrayList;

public class Equipe {

    private String nome;
    private String descricao;
    private ArrayList<Usuario> membros;

    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    public void adicionarMembro(Usuario usuario) {
        membros.add(usuario);
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Usuario> getMembros() {
        return membros;
    }

    @Override
    public String toString() {
        return "Equipe: " + nome +
                " | Descrição: " + descricao +
                " | Quantidade de membros: " + membros.size();
    }
}
aa
