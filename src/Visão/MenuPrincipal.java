package Visão;

import Controle.ControleEquipe;
import Controle.ControleProjeto;
import Controle.ControleTarefa;
import Controle.ControleUsuario;
import Modelo.Usuario;
import Servico.ServicoEquipe;
import Servico.ServicoProjeto;
import Servico.ServicoTarefa;
import Servico.ServicoUsuario;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner;
    private final Visao.TelaUsuario telaUsuario;
    private final Visao.TelaProjeto telaProjeto;
    private final Visao.TelaEquipe telaEquipe;
    private final Visao.TelaTarefa telaTarefa;

    public MenuPrincipal() {
        scanner = new Scanner(System.in);

        ControleUsuario controleUsuario = new ControleUsuario(new ServicoUsuario());
        ControleProjeto controleProjeto = new ControleProjeto(new ServicoProjeto());
        ControleEquipe controleEquipe = new ControleEquipe(new ServicoEquipe());
        ControleTarefa controleTarefa = new ControleTarefa(new ServicoTarefa());

        telaUsuario = new Visao.TelaUsuario(controleUsuario);
        telaProjeto = new Visao.TelaProjeto(controleProjeto);
        telaEquipe = new Visao.TelaEquipe(controleEquipe);
        telaTarefa = new Visao.TelaTarefa(controleTarefa);
    }

    public void iniciarSistema() {
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    telaUsuario.cadastrarUsuario();
                    break;
                case 2:
                    telaUsuario.listarUsuarios();
                    break;
                case 3:
                    telaProjeto.cadastrarProjeto(criarGerentePadrao());
                    break;
                case 4:
                    telaProjeto.listarProjetos();
                    break;
                case 5:
                    telaEquipe.cadastrarEquipe();
                    break;
                case 6:
                    telaEquipe.listarEquipes();
                    break;
                case 7:
                    telaTarefa.cadastrarTarefa(criarResponsavelPadrao());
                    break;
                case 8:
                    telaTarefa.listarTarefas();
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("===== MENU PRINCIPAL =====");
        System.out.println("1 - Cadastrar Usuario");
        System.out.println("2 - Listar Usuarios");
        System.out.println("3 - Cadastrar Projeto");
        System.out.println("4 - Listar Projetos");
        System.out.println("5 - Cadastrar Equipe");
        System.out.println("6 - Listar Equipes");
        System.out.println("7 - Cadastrar Tarefa");
        System.out.println("8 - Listar Tarefas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private Usuario criarGerentePadrao() {
        return new Usuario(
                "Gerente Sistema",
                "12345678901",
                "gerente@email.com",
                "Gerente",
                "gerente",
                "123",
                "Gerente"
        );
    }

    private Usuario criarResponsavelPadrao() {
        return new Usuario(
                "Colaborador Sistema",
                "99999999999",
                "colab@email.com",
                "Desenvolvedor",
                "dev",
                "123",
                "Colaborador"
        );
    }
}
