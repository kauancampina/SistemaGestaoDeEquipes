package Visao.Swing;

import Controle.ControleUsuario;
import Modelo.PerfilUsuario;
import Modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

class CadastroPrimeiroAdministradorDialog extends JDialog {

    private final ControleUsuario controleUsuario;
    private final JTextField nome = new JTextField(28);
    private final JTextField cpf = new JTextField(28);
    private final JTextField email = new JTextField(28);
    private final JTextField cargo = new JTextField(28);
    private final JTextField login = new JTextField(28);
    private final JPasswordField senha = new JPasswordField(28);

    CadastroPrimeiroAdministradorDialog(Frame owner, ControleUsuario controleUsuario) {
        super(owner, "Primeiro acesso", true);
        this.controleUsuario = controleUsuario;
        setSize(520, 420);
        setLocationRelativeTo(owner);
        montarTela();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Primeiro acesso");
        JLabel texto = new JLabel("<html>Para iniciar o sistema, crie o primeiro administrador.<br><br>"
                + "Apenas administradores podem alterar as configurações do sistema.</html>");
        conteudo.add(texto, BorderLayout.NORTH);

        JPanel form = SwingUtil.formPanel();
        SwingUtil.addCampo(form, 0, "Nome:", nome);
        SwingUtil.addCampo(form, 1, "CPF:", cpf);
        SwingUtil.addCampo(form, 2, "Email:", email);
        SwingUtil.addCampo(form, 3, "Cargo:", cargo);
        SwingUtil.addCampo(form, 4, "Login:", login);
        SwingUtil.addCampo(form, 5, "Senha:", senha);
        conteudo.add(form, BorderLayout.CENTER);

        JButton salvar = new JButton("Criar administrador");
        salvar.addActionListener(event -> salvar());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(salvar);
        conteudo.add(botoes, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    private void salvar() {
        try {
            Usuario usuario = new Usuario(
                    SwingUtil.texto(nome),
                    SwingUtil.texto(cpf),
                    SwingUtil.texto(email),
                    SwingUtil.texto(cargo),
                    SwingUtil.texto(login),
                    new String(senha.getPassword()),
                    PerfilUsuario.ADMINISTRADOR
            );
            controleUsuario.cadastrarPrimeiroAdministrador(usuario);
            SwingUtil.info(this, "Administrador cadastrado. Agora realize o login.");
            dispose();
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }
}
