package Repository;

import Modelo.Usuario;

import java.util.Optional;

public class UsuarioRepository extends ArquivoRepository<Usuario> {

    public UsuarioRepository() {
        super("usuarios.dat");
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getNomeCompleto().toLowerCase().contains(nome.toLowerCase()))
                .findFirst();
    }
}
