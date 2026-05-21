package Visao.Swing;

import Modelo.Usuario;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class TelaLogin extends JFrame {

    private final AppContext context;
    private final JTextField loginField = new JTextField(22);
    private final JPasswordField senhaField = new JPasswordField(22);

    public TelaLogin(AppContext context) {
        this.context = context;
        setTitle("Sistema de Gestão de Projetos e Equipes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        montarTela();
        SwingUtilities.invokeLater(this::verificarPrimeiroAcesso);
    }

    private void montarTela() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(24, 28, 12, 28));
        JLabel titulo = new JLabel("Acesso ao sistema");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        JLabel subtitulo = new JLabel("Informe suas credenciais para continuar.");
        header.add(titulo, BorderLayout.NORTH);
        header.add(subtitulo, BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(8, 28, 8, 28));
        adicionarCampo(form, 0, "Login", loginField);
        adicionarCampo(form, 1, "Senha", senhaField);

        JButton entrar = new JButton("Entrar");
        entrar.addActionListener(event -> autenticar());
        senhaField.addActionListener(event -> autenticar());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.setBorder(BorderFactory.createEmptyBorder(8, 28, 24, 28));
        botoes.add(entrar);

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private void adicionarCampo(JPanel form, int linha, String rotulo, JTextField campo) {
        GridBagConstraints label = new GridBagConstraints();
        label.gridx = 0;
        label.gridy = linha;
        label.anchor = GridBagConstraints.LINE_END;
        label.insets = new Insets(8, 8, 8, 8);
        form.add(new JLabel(rotulo), label);

        GridBagConstraints input = new GridBagConstraints();
        input.gridx = 1;
        input.gridy = linha;
        input.fill = GridBagConstraints.HORIZONTAL;
        input.weightx = 1;
        input.insets = new Insets(8, 0, 8, 8);
        form.add(campo, input);
    }

    private void verificarPrimeiroAcesso() {
        if (!context.getControleUsuario().possuiUsuariosCadastrados()) {
            CadastroPrimeiroAdministradorDialog dialog = new CadastroPrimeiroAdministradorDialog(this, context.getControleUsuario());
            dialog.setVisible(true);
        }
    }

    private void autenticar() {
        try {
            String login = loginField.getText().trim();
            String senha = new String(senhaField.getPassword());
            Usuario usuario = context.getControleUsuario().autenticar(login, senha);
            TelaMenuPrincipal menu = new TelaMenuPrincipal(context, usuario);
            menu.setVisible(true);
            dispose();
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    public static void abrir() {
        SwingUtil.aplicarTema();
        SwingUtilities.invokeLater(() -> new TelaLogin(new AppContext()).setVisible(true));
    }
}
