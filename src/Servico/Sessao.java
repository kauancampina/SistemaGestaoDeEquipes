package Servico;

import Modelo.Usuario;

public class Sessao {

    private Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void login(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void logout() {
        this.usuarioLogado = null;
    }

    public boolean estaAutenticado() {
        return usuarioLogado != null;
    }
}
