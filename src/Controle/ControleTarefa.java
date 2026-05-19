package Controle;

import Modelo.Tarefa;
import Servico.ServicoTarefa;

public class ControleTarefa {

    private ServicoTarefa servicoTarefa;

    public ControleTarefa(ServicoTarefa servicoTarefa) {
        this.servicoTarefa = servicoTarefa;
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        servicoTarefa.cadastrarTarefa(tarefa);
    }

    public void listarTarefas() {

        for (Tarefa tarefa : servicoTarefa.listarTarefas()) {
            System.out.println(tarefa);
        }
    }
}