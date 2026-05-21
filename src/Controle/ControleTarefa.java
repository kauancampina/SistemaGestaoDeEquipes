package Controle;

import Modelo.Tarefa;
import Modelo.StatusTarefa;
import Servico.PermissaoService;
import Servico.ServicoTarefa;
import Servico.Sessao;

import java.util.List;
import java.util.Optional;

public class ControleTarefa {

    private final ServicoTarefa servicoTarefa;
    private final PermissaoService permissaoService;
    private final Sessao sessao;

    public ControleTarefa(ServicoTarefa servicoTarefa, PermissaoService permissaoService, Sessao sessao) {
        this.servicoTarefa = servicoTarefa;
        this.permissaoService = permissaoService;
        this.sessao = sessao;
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        permissaoService.exigirGestao();
        servicoTarefa.cadastrarTarefa(tarefa);
    }

    public void editarTarefa(Tarefa tarefa) {
        permissaoService.exigirPodeAtualizarTarefa(tarefa);
        servicoTarefa.editarTarefa(tarefa);
    }

    public boolean removerTarefa(int id) {
        permissaoService.exigirGestao();
        return servicoTarefa.removerTarefa(id);
    }

    public Optional<Tarefa> buscarPorId(int id) {
        permissaoService.exigirAutenticacao();
        return servicoTarefa.buscarPorId(id);
    }

    public List<Tarefa> buscarPorNome(String titulo) {
        permissaoService.exigirAutenticacao();
        return servicoTarefa.buscarPorNome(titulo);
    }

    public List<Tarefa> listarTarefas() {
        permissaoService.exigirAutenticacao();
        return servicoTarefa.listarTarefas();
    }

    public List<Tarefa> listarMinhasTarefas() {
        permissaoService.exigirAutenticacao();
        return servicoTarefa.listarTarefasDoResponsavel(sessao.getUsuarioLogado().getId());
    }

    public void atualizarStatus(Tarefa tarefa, StatusTarefa status) {
        permissaoService.exigirPodeAtualizarTarefa(tarefa);
        servicoTarefa.atualizarStatus(tarefa, status);
    }
}
