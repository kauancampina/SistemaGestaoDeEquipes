package Visao.Swing;

import Modelo.PerfilUsuario;
import Modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Optional;

public class TelaUsuario extends JPanel {

    private final AppContext context;
    private final DefaultTableModel model = SwingUtil.modeloTabela("ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil");
    private final JTable tabela = SwingUtil.tabela(model);

    public TelaUsuario(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        montarTela();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Usuários");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton novo = new JButton("Cadastrar");
        JButton editar = new JButton("Editar");
        JButton remover = new JButton("Remover");
        JButton atualizar = new JButton("Atualizar");

        novo.addActionListener(event -> abrirFormulario(null));
        editar.addActionListener(event -> editarSelecionado());
        remover.addActionListener(event -> removerSelecionado());
        atualizar.addActionListener(event -> atualizarTabela());

        botoes.add(novo);
        botoes.add(editar);
        botoes.add(remover);
        botoes.add(atualizar);
        conteudo.add(botoes, BorderLayout.SOUTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        try {
            model.setRowCount(0);
            for (Usuario usuario : context.getControleUsuario().listarUsuarios()) {
                model.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNomeCompleto(),
                        usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getCargo(),
                        usuario.getLogin(),
                        usuario.getPerfil()
                });
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void editarSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            Optional<Usuario> usuario = context.getControleUsuario().buscarPorId(id);
            usuario.ifPresent(this::abrirFormulario);
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void removerSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            if (javax.swing.JOptionPane.showConfirmDialog(this, "Remover o usuário selecionado?", "Confirmação",
                    javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
                context.getControleUsuario().removerUsuario(id);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void abrirFormulario(Usuario usuario) {
        UsuarioDialog dialog = new UsuarioDialog((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), usuario);
        dialog.setVisible(true);
        if (dialog.salvou()) {
            atualizarTabela();
        }
    }

    private class UsuarioDialog extends JDialog {

        private final Usuario usuario;
        private boolean salvou;
        private final JTextField nome = new JTextField(26);
        private final JTextField cpf = new JTextField(26);
        private final JTextField email = new JTextField(26);
        private final JTextField cargo = new JTextField(26);
        private final JTextField login = new JTextField(26);
        private final JPasswordField senha = new JPasswordField(26);
        private final JComboBox<PerfilUsuario> perfil = new JComboBox<>(PerfilUsuario.values());

        UsuarioDialog(Frame owner, Usuario usuario) {
            super(owner, usuario == null ? "Cadastrar usuário" : "Editar usuário", true);
            this.usuario = usuario;
            setSize(520, 420);
            setLocationRelativeTo(owner);
            montar();
            preencher();
        }

        private void montar() {
            JPanel conteudo = SwingUtil.painelConteudo(usuario == null ? "Cadastrar usuário" : "Editar usuário");
            JPanel form = SwingUtil.formPanel();
            SwingUtil.addCampo(form, 0, "Nome:", nome);
            SwingUtil.addCampo(form, 1, "CPF:", cpf);
            SwingUtil.addCampo(form, 2, "Email:", email);
            SwingUtil.addCampo(form, 3, "Cargo:", cargo);
            SwingUtil.addCampo(form, 4, "Login:", login);
            SwingUtil.addCampo(form, 5, "Senha:", senha);
            SwingUtil.addCampo(form, 6, "Perfil:", perfil);
            conteudo.add(form, BorderLayout.CENTER);

            JButton salvar = new JButton("Salvar");
            JButton cancelar = new JButton("Cancelar");
            salvar.addActionListener(event -> salvar());
            cancelar.addActionListener(event -> dispose());
            JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            botoes.add(cancelar);
            botoes.add(salvar);
            conteudo.add(botoes, BorderLayout.SOUTH);
            setContentPane(conteudo);
        }

        private void preencher() {
            if (usuario == null) {
                return;
            }
            nome.setText(usuario.getNomeCompleto());
            cpf.setText(usuario.getCpf());
            email.setText(usuario.getEmail());
            cargo.setText(usuario.getCargo());
            login.setText(usuario.getLogin());
            perfil.setSelectedItem(usuario.getPerfil());
        }

        private void salvar() {
            try {
                String senhaDigitada = new String(senha.getPassword());
                String senhaFinal = senhaDigitada.isEmpty() && usuario != null ? usuario.getSenha() : senhaDigitada;
                Usuario novo = new Usuario(
                        usuario == null ? 0 : usuario.getId(),
                        SwingUtil.texto(nome),
                        SwingUtil.texto(cpf),
                        SwingUtil.texto(email),
                        SwingUtil.texto(cargo),
                        SwingUtil.texto(login),
                        senhaFinal,
                        (PerfilUsuario) perfil.getSelectedItem()
                );
                if (usuario == null) {
                    context.getControleUsuario().cadastrarUsuario(novo);
                } else {
                    context.getControleUsuario().editarUsuario(novo);
                }
                salvou = true;
                dispose();
            } catch (RuntimeException ex) {
                SwingUtil.erro(this, ex);
            }
        }

        boolean salvou() {
            return salvou;
        }
    }
}
