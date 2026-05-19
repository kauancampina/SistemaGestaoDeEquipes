package Controle;

import Modelo.Equipe;
import Servico.ServicoEquipe;

public class ControleEquipe {

    private ServicoEquipe servicoEquipe;

    public ControleEquipe(ServicoEquipe servicoEquipe) {
        this.servicoEquipe = servicoEquipe;
    }

    public void cadastrarEquipe(Equipe equipe) {
        servicoEquipe.cadastrarEquipe(equipe);
    }

    public void listarEquipes() {

        for (Equipe equipe : servicoEquipe.listarEquipes()) {
            System.out.println(equipe);
        }
    }
}