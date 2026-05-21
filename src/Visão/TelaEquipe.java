package Visao;

import Controle.ControleEquipe;
import Controle.ControleProjeto;
import Controle.ControleUsuario;
import Modelo.Equipe;
import Modelo.Projeto;
import Modelo.Usuario;
import Utilitario.ConsoleHelper;

import java.util.List;
import java.util.Optional;

public class TelaEquipe {

    private final ControleEquipe controleEquipe;
    private final ControleUsuario controleUsuario;
    private final ControleProjeto controleProjeto;
    private final ConsoleHelper console;

    public TelaEquipe(ControleEquipe controleEquipe, ControleUsuario controleUsuario,
                      ControleProjeto controleProjeto, ConsoleHelper console) {
        this.controleEquipe = controleEquipe;
        this.controleUsuario = controleUsuario;
        this.controleProjeto = controleProjeto;
        this.console = console;
    }

    public void cadastrarEquipe() {
        try {
            Equipe equipe = lerEquipe(0);
            controleEquipe.cadastrarEquipe(equipe);
            System.out.println("Equipe cadastrada com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editarEquipe() {
        try {
            int id = console.lerInteiro("ID da equipe: ");
            if (controleEquipe.buscarPorId(id).isEmpty()) {
                System.out.println("Equipe nao encontrada.");
                return;
            }
            controleEquipe.editarEquipe(lerEquipe(id));
            System.out.println("Equipe atualizada com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removerEquipe() {
        try {
            int id = console.lerInteiro("ID da equipe: ");
            System.out.println(controleEquipe.removerEquipe(id) ? "Equipe removida." : "Equipe nao encontrada.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarEquipes() {
        try {
            List<Equipe> equipes = controleEquipe.listarEquipes();
            if (equipes.isEmpty()) {
                System.out.println("Nenhuma equipe cadastrada.");
                return;
            }
            equipes.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorId() {
        try {
            int id = console.lerInteiro("ID da equipe: ");
            controleEquipe.buscarPorId(id).ifPresentOrElse(this::exibirDetalhes, () -> System.out.println("Equipe nao encontrada."));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorNome() {
        try {
            String nome = console.lerTexto("Nome: ");
            List<Equipe> equipes = controleEquipe.buscarPorNome(nome);
            if (equipes.isEmpty()) {
                System.out.println("Equipe nao encontrada.");
                return;
            }
            equipes.forEach(this::exibirDetalhes);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void adicionarMembro() {
        try {
            int idEquipe = console.lerInteiro("ID da equipe: ");
            int idUsuario = console.lerInteiro("ID do usuario: ");
            Optional<Equipe> equipe = controleEquipe.buscarPorId(idEquipe);
            Optional<Usuario> usuario = controleUsuario.buscarPorId(idUsuario);
            if (equipe.isEmpty() || usuario.isEmpty()) {
                System.out.println("Equipe ou usuario nao encontrado.");
                return;
            }
            controleEquipe.adicionarMembro(equipe.get(), usuario.get());
            System.out.println("Membro adicionado a equipe.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarProjetosDaEquipe() {
        try {
            int id = console.lerInteiro("ID da equipe: ");
            Optional<Equipe> equipe = controleEquipe.buscarPorId(id);
            if (equipe.isEmpty()) {
                System.out.println("Equipe nao encontrada.");
                return;
            }
            equipe.get().getProjetos().forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void vincularProjeto() {
        try {
            int idEquipe = console.lerInteiro("ID da equipe: ");
            int idProjeto = console.lerInteiro("ID do projeto: ");
            Optional<Equipe> equipe = controleEquipe.buscarPorId(idEquipe);
            Optional<Projeto> projeto = controleProjeto.buscarPorId(idProjeto);
            if (equipe.isEmpty() || projeto.isEmpty()) {
                System.out.println("Equipe ou projeto nao encontrado.");
                return;
            }
            controleEquipe.adicionarProjeto(equipe.get(), projeto.get());
            controleProjeto.adicionarEquipeAoProjeto(projeto.get(), equipe.get());
            System.out.println("Projeto vinculado a equipe.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Equipe lerEquipe(int id) {
        String nome = console.lerTexto("Nome da equipe: ");
        String descricao = console.lerTexto("Descricao: ");
        boolean ativa = console.lerBooleano("Equipe ativa");
        return new Equipe(id, nome, descricao, ativa);
    }

    private void exibirDetalhes(Equipe equipe) {
        System.out.println(equipe);
        System.out.println("Membros:");
        equipe.getMembros().forEach(usuario -> System.out.println("  " + usuario));
        System.out.println("Projetos:");
        equipe.getProjetos().forEach(projeto -> System.out.println("  " + projeto));
    }
}
