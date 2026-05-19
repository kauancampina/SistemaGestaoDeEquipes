package Visao;

import Controle.ControleProjeto;
import Modelo.Projeto;
import Modelo.Usuario;

import java.util.Scanner;

public class TelaProjeto {

    private ControleProjeto controleProjeto;
    private Scanner scanner;

    public TelaProjeto(ControleProjeto controleProjeto) {
        this.controleProjeto = controleProjeto;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarProjeto(Usuario gerente) {

        System.out.println("===== CADASTRO DE PROJETO =====");

        System.out.print("Nome do projeto: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Data de início: ");
        String inicio = scanner.nextLine();

        System.out.print("Data de término: ");
        String termino = scanner.nextLine();

        System.out.print("Status: ");
        String status = scanner.nextLine();

        Projeto projeto = new Projeto(nome, descricao, inicio, termino, status, gerente);

        controleProjeto.cadastrarProjeto(projeto);

        System.out.println("Projeto cadastrado com sucesso!");
    }

    public void listarProjetos() {
        controleProjeto.listarProjetos();
    }
}