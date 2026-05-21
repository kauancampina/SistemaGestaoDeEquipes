package Repository;

import Modelo.Entidade;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entidade> {

    T salvar(T entidade);

    void atualizar(T entidade);

    boolean remover(int id);

    Optional<T> buscarPorId(int id);

    List<T> listarTodos();
}
