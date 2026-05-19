package Servico;

import Modelo.Tarefa;

import java.util.ArrayList;

public class ServicoTarefa {

    private ArrayList<Tarefa> tarefas = new ArrayList<>();

    public void cadastrarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }

    public ArrayList<Tarefa> listarTarefas() {
        return tarefas;
    }
}