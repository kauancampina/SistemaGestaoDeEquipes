package Visao;

import Controle.ControleTarefa;
import Modelo.Tarefa;
import Modelo.Usuario;

import java.util.Scanner;

public class TelaTarefa {

    private ControleTarefa controleTarefa;
    private Scanner scanner;

    public TelaTarefa(ControleTarefa controleTarefa) {
        this.controleTarefa = controleTarefa;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarTarefa(Usuario responsavel) {

        System.out.println("===== CADASTRO DE TAREFA =====");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Prioridade: ");
        String prioridade = scanner.nextLine();

        System.out.print("Status: ");
        String status = scanner.nextLine();

        Tarefa tarefa = new Tarefa(titulo, descricao, prioridade, status, responsavel);

        controleTarefa.cadastrarTarefa(tarefa);

        System.out.println("Tarefa cadastrada com sucesso!");
    }

    public void listarTarefas() {
        controleTarefa.listarTarefas();
    }
}