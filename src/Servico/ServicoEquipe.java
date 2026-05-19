package Servico;

import Modelo.Equipe;

import java.util.ArrayList;

public class ServicoEquipe {

    private ArrayList<Equipe> equipes = new ArrayList<>();

    public void cadastrarEquipe(Equipe equipe) {
        equipes.add(equipe);
    }

    public ArrayList<Equipe> listarEquipes() {
        return equipes;
    }
}