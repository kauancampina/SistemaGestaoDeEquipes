package Controle;

import Modelo.Usuario;
import Servico.PermissaoService;
import Servico.ServicoUsuario;
import Servico.Sessao;

import java.util.List;
import java.util.Optional;

public class ControleUsuario {

    private final ServicoUsuario servicoUsuario;
    private final Sessao sessao;
    private final PermissaoService permissaoService;

    public ControleUsuario(ServicoUsuario servicoUsuario, Sessao sessao, PermissaoService permissaoService) {
        this.servicoUsuario = servicoUsuario;
        this.sessao = sessao;
        this.permissaoService = permissaoService;
    }

    public void cadastrarUsuario(Usuario usuario) {
        permissaoService.exigirAdministrador();
        servicoUsuario.cadastrarUsuario(usuario);
    }

    public void cadastrarPrimeiroAdministrador(Usuario usuario) {
        servicoUsuario.cadastrarPrimeiroAdministrador(usuario);
    }

    public void editarUsuario(Usuario usuario) {
        permissaoService.exigirAdministrador();
        servicoUsuario.editarUsuario(usuario);
    }

    public boolean removerUsuario(int id) {
        permissaoService.exigirAdministrador();
        return servicoUsuario.removerUsuario(id);
    }

    public Optional<Usuario> buscarPorId(int id) {
        permissaoService.exigirAutenticacao();
        return servicoUsuario.buscarPorId(id);
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        permissaoService.exigirAutenticacao();
        return servicoUsuario.buscarPorNome(nome);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        permissaoService.exigirAutenticacao();
        return servicoUsuario.buscarPorLogin(login);
    }

    public List<Usuario> listarUsuarios() {
        permissaoService.exigirAutenticacao();
        return servicoUsuario.listarUsuarios();
    }

    public Usuario autenticar(String login, String senha) {
        Usuario usuario = servicoUsuario.autenticar(login, senha);
        sessao.login(usuario);
        return usuario;
    }

    public void logout() {
        sessao.logout();
    }

    public Usuario getUsuarioLogado() {
        return sessao.getUsuarioLogado();
    }

    public boolean possuiUsuariosCadastrados() {
        return servicoUsuario.possuiUsuariosCadastrados();
    }
}
