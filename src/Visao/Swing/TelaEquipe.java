package Visao.Swing;

import Modelo.Equipe;
import Modelo.Projeto;
import Modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;

public class TelaEquipe extends JPanel {

    private final AppContext context;
    private final DefaultTableModel model = SwingUtil.modeloTabela("ID", "Nome", "Descrição", "Ativa", "Membros", "Projetos");
    private final JTable tabela = SwingUtil.tabela(model);

    public TelaEquipe(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        montarTela();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Equipes");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton novo = new JButton("Cadastrar");
        JButton editar = new JButton("Editar");
        JButton remover = new JButton("Remover");
        JButton membro = new JButton("Adicionar membro");
        JButton projeto = new JButton("Associar projeto");
        JButton atualizar = new JButton("Atualizar");

        novo.addActionListener(event -> abrirFormulario(null));
        editar.addActionListener(event -> editarSelecionado());
        remover.addActionListener(event -> removerSelecionado());
        membro.addActionListener(event -> adicionarMembro());
        projeto.addActionListener(event -> associarProjeto());
        atualizar.addActionListener(event -> atualizarTabela());

        botoes.add(novo);
        botoes.add(editar);
        botoes.add(remover);
        botoes.add(membro);
        botoes.add(projeto);
        botoes.add(atualizar);
        conteudo.add(botoes, BorderLayout.SOUTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        try {
            model.setRowCount(0);
            for (Equipe equipe : context.getControleEquipe().listarEquipes()) {
                model.addRow(new Object[]{
                        equipe.getId(),
                        equipe.getNome(),
                        equipe.getDescricao(),
                        equipe.isAtiva() ? "Sim" : "Não",
                        equipe.getMembros().size(),
                        equipe.getProjetos().size()
                });
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void editarSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            context.getControleEquipe().buscarPorId(id).ifPresent(this::abrirFormulario);
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void removerSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            if (javax.swing.JOptionPane.showConfirmDialog(this, "Remover a equipe selecionada?", "Confirmação",
                    javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
                context.getControleEquipe().removerEquipe(id);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private Equipe equipeSelecionada() {
        int id = SwingUtil.idSelecionado(tabela, 0);
        return context.getControleEquipe().buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada."));
    }

    private void adicionarMembro() {
        try {
            Equipe equipe = equipeSelecionada();
            List<Usuario> usuarios = context.getControleUsuario().listarUsuarios();
            JComboBox<Usuario> combo = new JComboBox<>(usuarios.toArray(new Usuario[0]));
            int opcao = javax.swing.JOptionPane.showConfirmDialog(this, combo, "Selecione o membro",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (opcao == javax.swing.JOptionPane.OK_OPTION && combo.getSelectedItem() != null) {
                context.getControleEquipe().adicionarMembro(equipe, (Usuario) combo.getSelectedItem());
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void associarProjeto() {
        try {
            Equipe equipe = equipeSelecionada();
            List<Projeto> projetos = context.getControleProjeto().listarProjetos();
            JComboBox<Projeto> combo = new JComboBox<>(projetos.toArray(new Projeto[0]));
            int opcao = javax.swing.JOptionPane.showConfirmDialog(this, combo, "Selecione o projeto",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (opcao == javax.swing.JOptionPane.OK_OPTION && combo.getSelectedItem() != null) {
                Projeto projeto = (Projeto) combo.getSelectedItem();
                context.getControleProjeto().adicionarEquipeAoProjeto(projeto, equipe);
                context.getControleEquipe().adicionarProjeto(equipe, projeto);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void abrirFormulario(Equipe equipe) {
        EquipeDialog dialog = new EquipeDialog((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), equipe);
        dialog.setVisible(true);
        if (dialog.salvou()) {
            atualizarTabela();
        }
    }

    private class EquipeDialog extends JDialog {

        private final Equipe equipe;
        private boolean salvou;
        private final JTextField nome = new JTextField(28);
        private final JTextField descricao = new JTextField(28);
        private final JCheckBox ativa = new JCheckBox("Equipe ativa");

        EquipeDialog(Frame owner, Equipe equipe) {
            super(owner, equipe == null ? "Cadastrar equipe" : "Editar equipe", true);
            this.equipe = equipe;
            setSize(520, 340);
            setLocationRelativeTo(owner);
            montar();
            preencher();
        }

        private void montar() {
            JPanel conteudo = SwingUtil.painelConteudo(equipe == null ? "Cadastrar equipe" : "Editar equipe");
            JPanel form = SwingUtil.formPanel();
            SwingUtil.addCampo(form, 0, "Nome:", nome);
            SwingUtil.addCampo(form, 1, "Descrição:", descricao);
            SwingUtil.addCampo(form, 2, "Status:", ativa);
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
            ativa.setSelected(true);
            if (equipe == null) {
                return;
            }
            nome.setText(equipe.getNome());
            descricao.setText(equipe.getDescricao());
            ativa.setSelected(equipe.isAtiva());
        }

        private void salvar() {
            try {
                Equipe nova = new Equipe(equipe == null ? 0 : equipe.getId(), SwingUtil.texto(nome), SwingUtil.texto(descricao), ativa.isSelected());
                if (equipe == null) {
                    context.getControleEquipe().cadastrarEquipe(nova);
                } else {
                    for (Usuario membro : equipe.getMembros()) {
                        nova.adicionarMembro(membro);
                    }
                    for (Projeto projeto : equipe.getProjetos()) {
                        nova.adicionarProjeto(projeto);
                    }
                    context.getControleEquipe().editarEquipe(nova);
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
