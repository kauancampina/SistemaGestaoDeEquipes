package Visao;

import Controle.ControleEquipe;
import Controle.ControleProjeto;
import Controle.ControleUsuario;
import Modelo.Equipe;
import Modelo.PerfilUsuario;
import Modelo.Projeto;
import Modelo.StatusProjeto;
import Modelo.Usuario;
import Utilitario.ConsoleHelper;
import Utilitario.Validacoes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TelaProjeto {

    private final ControleProjeto controleProjeto;
    private final ControleUsuario controleUsuario;
    private final ControleEquipe controleEquipe;
    private final ConsoleHelper console;

    public TelaProjeto(ControleProjeto controleProjeto, ControleUsuario controleUsuario,
                       ControleEquipe controleEquipe, ConsoleHelper console) {
        this.controleProjeto = controleProjeto;
        this.controleUsuario = controleUsuario;
        this.controleEquipe = controleEquipe;
        this.console = console;
    }

    public void cadastrarProjeto() {
        try {
            System.out.println("===== CADASTRO DE PROJETO =====");
            Projeto projeto = lerProjeto(0);
            controleProjeto.cadastrarProjeto(projeto);
            System.out.println("Projeto cadastrado com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editarProjeto() {
        try {
            int id = console.lerInteiro("ID do projeto: ");
            if (controleProjeto.buscarPorId(id).isEmpty()) {
                System.out.println("Projeto nao encontrado.");
                return;
            }
            controleProjeto.editarProjeto(lerProjeto(id));
            System.out.println("Projeto atualizado com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removerProjeto() {
        try {
            int id = console.lerInteiro("ID do projeto: ");
            System.out.println(controleProjeto.removerProjeto(id) ? "Projeto removido." : "Projeto nao encontrado.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarProjetos() {
        try {
            List<Projeto> projetos = controleProjeto.listarProjetos();
            if (projetos.isEmpty()) {
                System.out.println("Nenhum projeto cadastrado.");
                return;
            }
            projetos.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorId() {
        try {
            int id = console.lerInteiro("ID do projeto: ");
            controleProjeto.buscarPorId(id).ifPresentOrElse(this::exibirDetalhes, () -> System.out.println("Projeto nao encontrado."));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorNome() {
        try {
            String nome = console.lerTexto("Nome: ");
            List<Projeto> projetos = controleProjeto.buscarPorNome(nome);
            if (projetos.isEmpty()) {
                System.out.println("Projeto nao encontrado.");
                return;
            }
            projetos.forEach(this::exibirDetalhes);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void adicionarEquipeAoProjeto() {
        try {
            int idProjeto = console.lerInteiro("ID do projeto: ");
            int idEquipe = console.lerInteiro("ID da equipe: ");
            Optional<Projeto> projeto = controleProjeto.buscarPorId(idProjeto);
            Optional<Equipe> equipe = controleEquipe.buscarPorId(idEquipe);
            if (projeto.isEmpty() || equipe.isEmpty()) {
                System.out.println("Projeto ou equipe nao encontrado.");
                return;
            }
            controleProjeto.adicionarEquipeAoProjeto(projeto.get(), equipe.get());
            controleEquipe.adicionarProjeto(equipe.get(), projeto.get());
            System.out.println("Equipe vinculada ao projeto.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarEquipesDoProjeto() {
        try {
            int id = console.lerInteiro("ID do projeto: ");
            Optional<Projeto> projeto = controleProjeto.buscarPorId(id);
            if (projeto.isEmpty()) {
                System.out.println("Projeto nao encontrado.");
                return;
            }
            projeto.get().getEquipes().forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Projeto lerProjeto(int id) {
        String nome = console.lerTexto("Nome do projeto: ");
        String descricao = console.lerTexto("Descricao: ");
        LocalDate inicio = Validacoes.data(console.lerTexto("Data de inicio (DD-MM-AAAA): "), "Data de inicio");
        LocalDate termino = Validacoes.data(console.lerTexto("Data de termino (DD-MM-AAAA): "), "Data de termino");
        StatusProjeto status = console.lerEnum("Status: ", StatusProjeto.class);
        Usuario gerente = selecionarGerente();
        return new Projeto(id, nome, descricao, inicio, termino, status, gerente);
    }

    private Usuario selecionarGerente() {
        int idGerente = console.lerInteiro("ID do gerente: ");
        Usuario gerente = controleUsuario.buscarPorId(idGerente)
                .orElseThrow(() -> new IllegalArgumentException("Gerente nao encontrado."));
        if (gerente.getPerfil() != PerfilUsuario.GERENTE && gerente.getPerfil() != PerfilUsuario.ADMINISTRADOR) {
            throw new IllegalArgumentException("Usuario selecionado nao possui perfil de gestao.");
        }
        return gerente;
    }

    private void exibirDetalhes(Projeto projeto) {
        System.out.println(projeto);
        System.out.println("Equipes:");
        projeto.getEquipes().forEach(equipe -> System.out.println("  " + equipe));
        System.out.println("Tarefas:");
        projeto.getTarefas().forEach(tarefa -> System.out.println("  " + tarefa));
    }
}
