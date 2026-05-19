package Controle;

import Modelo.Usuario;
import Servico.ServicoUsuario;

public class ControleUsuario {

    private ServicoUsuario servicoUsuario;

    public ControleUsuario(ServicoUsuario servicoUsuario) {
        this.servicoUsuario = servicoUsuario;
    }

    public void cadastrarUsuario(Usuario usuario) {
        servicoUsuario.cadastrarUsuario(usuario);
    }

    public void listarUsuarios() {

        for (Usuario usuario : servicoUsuario.listarUsuarios()) {
            System.out.println(usuario);
        }
    }
}