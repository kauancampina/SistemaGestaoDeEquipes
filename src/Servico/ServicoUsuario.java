package Servico;

import Modelo.Usuario;

import java.util.ArrayList;

public class ServicoUsuario {

    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public ArrayList<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorLogin(String login) {

        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equalsIgnoreCase(login)) {
                return usuario;
            }
        }
        return null;
    }
}