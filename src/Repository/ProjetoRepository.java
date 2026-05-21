package Repository;

import Modelo.Projeto;

import java.util.List;
import java.util.stream.Collectors;

public class ProjetoRepository extends ArquivoRepository<Projeto> {

    public ProjetoRepository() {
        super("projetos.dat");
    }

    public List<Projeto> buscarPorNome(String nome) {
        return listarTodos().stream()
                .filter(projeto -> projeto.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
}
