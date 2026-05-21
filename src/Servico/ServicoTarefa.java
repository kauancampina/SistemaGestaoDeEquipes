package Servico;

import Modelo.Projeto;
import Modelo.StatusTarefa;
import Modelo.Tarefa;
import Repository.ProjetoRepository;
import Repository.TarefaRepository;
import Utilitario.Validacoes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicoTarefa {

    private final TarefaRepository repository;
    private final ProjetoRepository projetoRepository;

    public ServicoTarefa(TarefaRepository repository, ProjetoRepository projetoRepository) {
        this.repository = repository;
        this.projetoRepository = projetoRepository;
    }

    public Tarefa cadastrarTarefa(Tarefa tarefa) {
        validarTarefa(tarefa);
        Tarefa salva = repository.salvar(tarefa);
        // Toda tarefa nasce vinculada a um projeto real para preservar a consistencia do dominio.
        tarefa.getProjeto().adicionarTarefa(salva);
        projetoRepository.atualizar(tarefa.getProjeto());
        return salva;
    }

    public void editarTarefa(Tarefa tarefa) {
        Validacoes.idPositivo(tarefa.getId(), "tarefa");
        validarTarefa(tarefa);
        repository.atualizar(tarefa);
        projetoRepository.atualizar(tarefa.getProjeto());
    }

    public boolean removerTarefa(int id) {
        Validacoes.idPositivo(id, "tarefa");
        Optional<Tarefa> tarefa = repository.buscarPorId(id);
        tarefa.ifPresent(t -> {
            Projeto projeto = t.getProjeto();
            if (projeto != null) {
                projeto.removerTarefa(t);
                projetoRepository.atualizar(projeto);
            }
        });
        return repository.remover(id);
    }

    public Optional<Tarefa> buscarPorId(int id) {
        Validacoes.idPositivo(id, "tarefa");
        return repository.buscarPorId(id);
    }

    public List<Tarefa> buscarPorNome(String titulo) {
        Validacoes.campoObrigatorio(titulo, "Titulo");
        return repository.buscarPorNome(titulo);
    }

    public List<Tarefa> listarTarefas() {
        return repository.listarTodos();
    }

    public List<Tarefa> listarTarefasDoResponsavel(int usuarioId) {
        return repository.listarTodos().stream()
                .filter(tarefa -> tarefa.getResponsavel() != null && tarefa.getResponsavel().getId() == usuarioId)
                .collect(Collectors.toList());
    }

    public void atualizarStatus(Tarefa tarefa, StatusTarefa status) {
        if (tarefa == null || status == null) {
            throw new IllegalArgumentException("Tarefa e status sao obrigatorios.");
        }
        tarefa.setStatus(status);
        repository.atualizar(tarefa);
    }

    private void validarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa nao pode ser nula.");
        }
        Validacoes.campoObrigatorio(tarefa.getTitulo(), "Titulo");
        Validacoes.campoObrigatorio(tarefa.getDescricao(), "Descricao");
        if (tarefa.getPrioridade() == null) {
            throw new IllegalArgumentException("Prioridade e obrigatoria.");
        }
        if (tarefa.getStatus() == null) {
            throw new IllegalArgumentException("Status e obrigatorio.");
        }
        if (tarefa.getResponsavel() == null || tarefa.getResponsavel().getId() <= 0) {
            throw new IllegalArgumentException("A tarefa deve possuir responsavel real cadastrado.");
        }
        if (tarefa.getProjeto() == null || tarefa.getProjeto().getId() <= 0) {
            throw new IllegalArgumentException("A tarefa deve estar vinculada a um projeto cadastrado.");
        }
    }
}
