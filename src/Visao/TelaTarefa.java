package Visao;

import Controle.ControleProjeto;
import Controle.ControleTarefa;
import Controle.ControleUsuario;
import Modelo.PrioridadeTarefa;
import Modelo.Projeto;
import Modelo.StatusTarefa;
import Modelo.Tarefa;
import Modelo.Usuario;
import Utilitario.ConsoleHelper;

import java.util.List;
import java.util.Optional;

public class TelaTarefa {

    private final ControleTarefa controleTarefa;
    private final ControleProjeto controleProjeto;
    private final ControleUsuario controleUsuario;
    private final ConsoleHelper console;

    public TelaTarefa(ControleTarefa controleTarefa, ControleProjeto controleProjeto,
                      ControleUsuario controleUsuario, ConsoleHelper console) {
        this.controleTarefa = controleTarefa;
        this.controleProjeto = controleProjeto;
        this.controleUsuario = controleUsuario;
        this.console = console;
    }

    public void cadastrarTarefa() {
        try {
            Tarefa tarefa = lerTarefa(0);
            controleTarefa.cadastrarTarefa(tarefa);
            System.out.println("Tarefa cadastrada com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editarTarefa() {
        try {
            int id = console.lerInteiro("ID da tarefa: ");
            if (controleTarefa.buscarPorId(id).isEmpty()) {
                System.out.println("Tarefa nao encontrada.");
                return;
            }
            controleTarefa.editarTarefa(lerTarefa(id));
            System.out.println("Tarefa atualizada com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void atualizarStatus() {
        try {
            int id = console.lerInteiro("ID da tarefa: ");
            Optional<Tarefa> tarefa = controleTarefa.buscarPorId(id);
            if (tarefa.isEmpty()) {
                System.out.println("Tarefa nao encontrada.");
                return;
            }
            StatusTarefa status = console.lerEnum("Novo status: ", StatusTarefa.class);
            controleTarefa.atualizarStatus(tarefa.get(), status);
            System.out.println("Status atualizado.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removerTarefa() {
        try {
            int id = console.lerInteiro("ID da tarefa: ");
            System.out.println(controleTarefa.removerTarefa(id) ? "Tarefa removida." : "Tarefa nao encontrada.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarTarefas() {
        try {
            List<Tarefa> tarefas = controleTarefa.listarTarefas();
            if (tarefas.isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada.");
                return;
            }
            tarefas.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarMinhasTarefas() {
        try {
            List<Tarefa> tarefas = controleTarefa.listarMinhasTarefas();
            if (tarefas.isEmpty()) {
                System.out.println("Nenhuma tarefa atribuida ao usuario logado.");
                return;
            }
            tarefas.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorId() {
        try {
            int id = console.lerInteiro("ID da tarefa: ");
            controleTarefa.buscarPorId(id).ifPresentOrElse(System.out::println, () -> System.out.println("Tarefa nao encontrada."));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorNome() {
        try {
            String titulo = console.lerTexto("Titulo: ");
            List<Tarefa> tarefas = controleTarefa.buscarPorNome(titulo);
            if (tarefas.isEmpty()) {
                System.out.println("Tarefa nao encontrada.");
                return;
            }
            tarefas.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Tarefa lerTarefa(int id) {
        String titulo = console.lerTexto("Titulo: ");
        String descricao = console.lerTexto("Descricao: ");
        PrioridadeTarefa prioridade = console.lerEnum("Prioridade: ", PrioridadeTarefa.class);
        StatusTarefa status = console.lerEnum("Status: ", StatusTarefa.class);
        Projeto projeto = controleProjeto.buscarPorId(console.lerInteiro("ID do projeto: "))
                .orElseThrow(() -> new IllegalArgumentException("Projeto nao encontrado."));
        Usuario responsavel = controleUsuario.buscarPorId(console.lerInteiro("ID do responsavel: "))
                .orElseThrow(() -> new IllegalArgumentException("Responsavel nao encontrado."));
        return new Tarefa(id, titulo, descricao, prioridade, status, responsavel, projeto);
    }
}
