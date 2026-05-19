package Visao;

import Controle.ControleUsuario;
import Modelo.Usuario;

import java.util.Scanner;

public class TelaUsuario {

    private ControleUsuario controleUsuario;
    private Scanner scanner;

    public TelaUsuario(ControleUsuario controleUsuario) {
        this.controleUsuario = controleUsuario;
        this.scanner = new Scanner(System.in);
    }

    public void cadastrarUsuario() {

        System.out.println("===== CADASTRO DE USUÁRIO =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Perfil (Administrador/Gerente/Colaborador): ");
        String perfil = scanner.nextLine();

        Usuario usuario = new Usuario(nome, cpf, email, cargo, login, senha, perfil);

        controleUsuario.cadastrarUsuario(usuario);

        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void listarUsuarios() {
        controleUsuario.listarUsuarios();
    }
}