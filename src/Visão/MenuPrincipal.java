package Visao;

import Controle.ControleEquipe;
import Controle.ControleProjeto;
import Controle.ControleRelatorio;
import Controle.ControleTarefa;
import Controle.ControleUsuario;
import Modelo.Usuario;
import Repository.EquipeRepository;
import Repository.ProjetoRepository;
import Repository.TarefaRepository;
import Repository.UsuarioRepository;
import Servico.PermissaoService;
import Servico.ServicoEquipe;
import Servico.ServicoProjeto;
import Servico.ServicoRelatorio;
import Servico.ServicoTarefa;
import Servico.ServicoUsuario;
import Servico.Sessao;
import Utilitario.ConsoleHelper;

import java.util.Scanner;

public class MenuPrincipal {

    private final ConsoleHelper console;
    private final ControleUsuario controleUsuario;
    private final ControleRelatorio controleRelatorio;
    private final TelaUsuario telaUsuario;
    private final TelaProjeto telaProjeto;
    private final TelaEquipe telaEquipe;
    private final TelaTarefa telaTarefa;

    public MenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        this.console = new ConsoleHelper(scanner);

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ProjetoRepository projetoRepository = new ProjetoRepository();
        EquipeRepository equipeRepository = new EquipeRepository();
        TarefaRepository tarefaRepository = new TarefaRepository();

        Sessao sessao = new Sessao();
        PermissaoService permissaoService = new PermissaoService(sessao);

        ServicoUsuario servicoUsuario = new ServicoUsuario(usuarioRepository);
        servicoUsuario.garantirAdministradorInicial();
        ServicoProjeto servicoProjeto = new ServicoProjeto(projetoRepository);
        ServicoEquipe servicoEquipe = new ServicoEquipe(equipeRepository);
        ServicoTarefa servicoTarefa = new ServicoTarefa(tarefaRepository, projetoRepository);
        ServicoRelatorio servicoRelatorio = new ServicoRelatorio(servicoUsuario, servicoProjeto, servicoEquipe, servicoTarefa);

        this.controleUsuario = new ControleUsuario(servicoUsuario, sessao, permissaoService);
        ControleProjeto controleProjeto = new ControleProjeto(servicoProjeto, permissaoService);
        ControleEquipe controleEquipe = new ControleEquipe(servicoEquipe, permissaoService);
        ControleTarefa controleTarefa = new ControleTarefa(servicoTarefa, permissaoService, sessao);
        this.controleRelatorio = new ControleRelatorio(servicoRelatorio, permissaoService);

        this.telaUsuario = new TelaUsuario(controleUsuario, console);
        this.telaProjeto = new TelaProjeto(controleProjeto, controleUsuario, controleEquipe, console);
        this.telaEquipe = new TelaEquipe(controleEquipe, controleUsuario, controleProjeto, console);
        this.telaTarefa = new TelaTarefa(controleTarefa, controleProjeto, controleUsuario, console);
    }

    public void iniciarSistema() {
        realizarLogin();
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = console.lerInteiro("Escolha uma opcao: ");
            switch (opcao) {
                case 1: menuUsuarios(); break;
                case 2: menuProjetos(); break;
                case 3: menuEquipes(); break;
                case 4: menuTarefas(); break;
                case 5: exibirRelatorio(); break;
                case 6: trocarUsuario(); break;
                case 0: System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void realizarLogin() {
        System.out.println("===== LOGIN =====");
        while (controleUsuario.getUsuarioLogado() == null) {
            try {
                String login = console.lerTexto("Login: ");
                String senha = console.lerTexto("Senha: ");
                Usuario usuario = controleUsuario.autenticar(login, senha);
                System.out.println("Bem-vindo, " + usuario.getNomeCompleto() + " (" + usuario.getPerfil() + ").");
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void trocarUsuario() {
        controleUsuario.logout();
        realizarLogin();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1 - Usuarios");
        System.out.println("2 - Projetos");
        System.out.println("3 - Equipes");
        System.out.println("4 - Tarefas");
        System.out.println("5 - Relatorios");
        System.out.println("6 - Trocar usuario");
        System.out.println("0 - Sair");
    }

    private void menuUsuarios() {
        int opcao;
        do {
            System.out.println("\n===== USUARIOS =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Editar");
            System.out.println("3 - Remover");
            System.out.println("4 - Buscar por ID");
            System.out.println("5 - Buscar por nome");
            System.out.println("6 - Listar");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            switch (opcao) {
                case 1: telaUsuario.cadastrarUsuario(); break;
                case 2: telaUsuario.editarUsuario(); break;
                case 3: telaUsuario.removerUsuario(); break;
                case 4: telaUsuario.buscarPorId(); break;
                case 5: telaUsuario.buscarPorNome(); break;
                case 6: telaUsuario.listarUsuarios(); break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void menuProjetos() {
        int opcao;
        do {
            System.out.println("\n===== PROJETOS =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Editar");
            System.out.println("3 - Remover");
            System.out.println("4 - Buscar por ID");
            System.out.println("5 - Buscar por nome");
            System.out.println("6 - Listar");
            System.out.println("7 - Adicionar equipe ao projeto");
            System.out.println("8 - Listar equipes do projeto");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            switch (opcao) {
                case 1: telaProjeto.cadastrarProjeto(); break;
                case 2: telaProjeto.editarProjeto(); break;
                case 3: telaProjeto.removerProjeto(); break;
                case 4: telaProjeto.buscarPorId(); break;
                case 5: telaProjeto.buscarPorNome(); break;
                case 6: telaProjeto.listarProjetos(); break;
                case 7: telaProjeto.adicionarEquipeAoProjeto(); break;
                case 8: telaProjeto.listarEquipesDoProjeto(); break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void menuEquipes() {
        int opcao;
        do {
            System.out.println("\n===== EQUIPES =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Editar");
            System.out.println("3 - Remover");
            System.out.println("4 - Buscar por ID");
            System.out.println("5 - Buscar por nome");
            System.out.println("6 - Listar");
            System.out.println("7 - Adicionar membro");
            System.out.println("8 - Vincular projeto");
            System.out.println("9 - Listar projetos da equipe");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            switch (opcao) {
                case 1: telaEquipe.cadastrarEquipe(); break;
                case 2: telaEquipe.editarEquipe(); break;
                case 3: telaEquipe.removerEquipe(); break;
                case 4: telaEquipe.buscarPorId(); break;
                case 5: telaEquipe.buscarPorNome(); break;
                case 6: telaEquipe.listarEquipes(); break;
                case 7: telaEquipe.adicionarMembro(); break;
                case 8: telaEquipe.vincularProjeto(); break;
                case 9: telaEquipe.listarProjetosDaEquipe(); break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void menuTarefas() {
        int opcao;
        do {
            System.out.println("\n===== TAREFAS =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Editar");
            System.out.println("3 - Remover");
            System.out.println("4 - Buscar por ID");
            System.out.println("5 - Buscar por titulo");
            System.out.println("6 - Listar");
            System.out.println("7 - Minhas tarefas");
            System.out.println("8 - Atualizar status");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            switch (opcao) {
                case 1: telaTarefa.cadastrarTarefa(); break;
                case 2: telaTarefa.editarTarefa(); break;
                case 3: telaTarefa.removerTarefa(); break;
                case 4: telaTarefa.buscarPorId(); break;
                case 5: telaTarefa.buscarPorNome(); break;
                case 6: telaTarefa.listarTarefas(); break;
                case 7: telaTarefa.listarMinhasTarefas(); break;
                case 8: telaTarefa.atualizarStatus(); break;
                case 0: break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void exibirRelatorio() {
        try {
            System.out.println("========== RELATORIO ==========");
            System.out.println(controleRelatorio.gerarResumoGeral());
            System.out.println("===============================");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
