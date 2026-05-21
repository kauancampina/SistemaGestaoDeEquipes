package Visao;

import Controle.ControleUsuario;
import Modelo.PerfilUsuario;
import Modelo.Usuario;
import Utilitario.ConsoleHelper;

import java.util.List;
import java.util.Optional;

public class TelaUsuario {

    private final ControleUsuario controleUsuario;
    private final ConsoleHelper console;

    public TelaUsuario(ControleUsuario controleUsuario, ConsoleHelper console) {
        this.controleUsuario = controleUsuario;
        this.console = console;
    }

    public void cadastrarUsuario() {
        try {
            System.out.println("===== CADASTRO DE USUARIO =====");
            Usuario usuario = lerUsuario(0, true);
            controleUsuario.cadastrarUsuario(usuario);
            System.out.println("Usuario cadastrado com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editarUsuario() {
        try {
            int id = console.lerInteiro("ID do usuario: ");
            Optional<Usuario> usuarioExistente = controleUsuario.buscarPorId(id);
            if (usuarioExistente.isEmpty()) {
                System.out.println("Usuario nao encontrado.");
                return;
            }
            Usuario usuario = lerUsuario(id, true);
            controleUsuario.editarUsuario(usuario);
            System.out.println("Usuario atualizado com sucesso.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removerUsuario() {
        try {
            int id = console.lerInteiro("ID do usuario: ");
            System.out.println(controleUsuario.removerUsuario(id) ? "Usuario removido." : "Usuario nao encontrado.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorId() {
        try {
            int id = console.lerInteiro("ID do usuario: ");
            controleUsuario.buscarPorId(id).ifPresentOrElse(System.out::println, () -> System.out.println("Usuario nao encontrado."));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarPorNome() {
        try {
            String nome = console.lerTexto("Nome: ");
            controleUsuario.buscarPorNome(nome).ifPresentOrElse(System.out::println, () -> System.out.println("Usuario nao encontrado."));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listarUsuarios() {
        try {
            List<Usuario> usuarios = controleUsuario.listarUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuario cadastrado.");
                return;
            }
            usuarios.forEach(System.out::println);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Usuario lerUsuario(int id, boolean lerSenha) {
        String nome = console.lerTexto("Nome: ");
        String cpf = console.lerTexto("CPF (11 digitos, sem pontos, tracos, acentos ou pontuacoes): ");
        String email = console.lerTexto("Email: ");
        String cargo = console.lerTexto("Cargo: ");
        String login = console.lerTexto("Login: ");
        String senha = lerSenha ? console.lerTexto("Senha: ") : "";
        PerfilUsuario perfil = console.lerEnum("Perfil: ", PerfilUsuario.class);
        return new Usuario(id, nome, cpf, email, cargo, login, senha, perfil);
    }
}
