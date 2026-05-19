package Visao;

import Controle.ControleEquipe;
import Modelo.Equipe;

import java.util.Scanner;

public class TelaEquipe {

    private ControleEquipe controleEquipe;
    private Scanner scanner;

    public TelaEquipe(ControleEquipe controleEquipe) {
        this.controleEquipe = controleEquipe;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarEquipe() {

        System.out.println("===== CADASTRO DE EQUIPE =====");

        System.out.print("Nome da equipe: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Equipe equipe = new Equipe(nome, descricao);

        controleEquipe.cadastrarEquipe(equipe);

        System.out.println("Equipe cadastrada com sucesso!");
    }

    public void listarEquipes() {
        controleEquipe.listarEquipes();
    }
}