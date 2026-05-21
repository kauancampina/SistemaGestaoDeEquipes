package Servico;

import Modelo.PerfilUsuario;
import Modelo.Usuario;
import Repository.UsuarioRepository;
import Utilitario.Validacoes;

import java.util.List;
import java.util.Optional;

public class ServicoUsuario {

    private final UsuarioRepository repository;

    public ServicoUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        validarUsuario(usuario, true);
        repository.buscarPorLogin(usuario.getLogin()).ifPresent(u -> {
            throw new IllegalArgumentException("Ja existe usuario com este login.");
        });
        return repository.salvar(usuario);
    }

    public Usuario cadastrarPrimeiroAdministrador(Usuario usuario) {
        if (!repository.listarTodos().isEmpty()) {
            throw new IllegalStateException("O administrador inicial ja foi cadastrado.");
        }
        usuario.setPerfil(PerfilUsuario.ADMINISTRADOR);
        return cadastrarUsuario(usuario);
    }

    public void editarUsuario(Usuario usuario) {
        validarUsuario(usuario, false);
        Validacoes.idPositivo(usuario.getId(), "usuario");
        repository.atualizar(usuario);
    }

    public boolean removerUsuario(int id) {
        Validacoes.idPositivo(id, "usuario");
        return repository.remover(id);
    }

    public Optional<Usuario> buscarPorId(int id) {
        Validacoes.idPositivo(id, "usuario");
        return repository.buscarPorId(id);
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        Validacoes.campoObrigatorio(nome, "Nome");
        return repository.buscarPorNome(nome);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        Validacoes.campoObrigatorio(login, "Login");
        return repository.buscarPorLogin(login);
    }

    public List<Usuario> listarUsuarios() {
        return repository.listarTodos();
    }

    public Usuario autenticar(String login, String senha) {
        Validacoes.campoObrigatorio(login, "Login");
        Validacoes.campoObrigatorio(senha, "Senha");
        return repository.buscarPorLogin(login)
                .filter(usuario -> usuario.getSenha().equals(senha))
                .orElseThrow(() -> new SecurityException("Login ou senha invalidos."));
    }

    public boolean possuiUsuariosCadastrados() {
        return !repository.listarTodos().isEmpty();
    }

    private void validarUsuario(Usuario usuario, boolean validarSenha) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }
        Validacoes.campoObrigatorio(usuario.getNomeCompleto(), "Nome");
        Validacoes.cpf(usuario.getCpf());
        Validacoes.email(usuario.getEmail());
        Validacoes.campoObrigatorio(usuario.getCargo(), "Cargo");
        Validacoes.campoObrigatorio(usuario.getLogin(), "Login");
        if (validarSenha || usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            Validacoes.senha(usuario.getSenha());
        }
        if (usuario.getPerfil() == null) {
            throw new IllegalArgumentException("Perfil e obrigatorio.");
        }
    }
}
