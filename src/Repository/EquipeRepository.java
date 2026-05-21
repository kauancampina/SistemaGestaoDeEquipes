package Repository;

import Modelo.Equipe;

import java.util.List;
import java.util.stream.Collectors;

public class EquipeRepository extends ArquivoRepository<Equipe> {

    public EquipeRepository() {
        super("equipes.dat");
    }

    public List<Equipe> buscarPorNome(String nome) {
        return listarTodos().stream()
                .filter(equipe -> equipe.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
}
