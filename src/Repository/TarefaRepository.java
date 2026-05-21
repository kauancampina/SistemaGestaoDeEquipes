package Repository;

import Modelo.Tarefa;

import java.util.List;
import java.util.stream.Collectors;

public class TarefaRepository extends ArquivoRepository<Tarefa> {

    public TarefaRepository() {
        super("tarefas.dat");
    }

    public List<Tarefa> buscarPorNome(String titulo) {
        return listarTodos().stream()
                .filter(tarefa -> tarefa.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
}
