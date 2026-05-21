package Visao.Swing;

import Modelo.PrioridadeTarefa;
import Modelo.Projeto;
import Modelo.StatusTarefa;
import Modelo.Tarefa;
import Modelo.Usuario;

import javax.swing.JButton;
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

public class TelaTarefa extends JPanel {

    private final AppContext context;
    private final DefaultTableModel model = SwingUtil.modeloTabela("ID", "Título", "Prioridade", "Status", "Responsável", "Projeto");
    private final JTable tabela = SwingUtil.tabela(model);

    public TelaTarefa(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        montarTela();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Tarefas");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton nova = new JButton("Cadastrar");
        JButton editar = new JButton("Editar");
        JButton remover = new JButton("Remover");
        JButton status = new JButton("Atualizar status");
        JButton minhas = new JButton("Minhas tarefas");
        JButton todas = new JButton("Todas");

        nova.addActionListener(event -> abrirFormulario(null));
        editar.addActionListener(event -> editarSelecionada());
        remover.addActionListener(event -> removerSelecionada());
        status.addActionListener(event -> atualizarStatus());
        minhas.addActionListener(event -> listarMinhas());
        todas.addActionListener(event -> atualizarTabela());

        botoes.add(nova);
        botoes.add(editar);
        botoes.add(remover);
        botoes.add(status);
        botoes.add(minhas);
        botoes.add(todas);
        conteudo.add(botoes, BorderLayout.SOUTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        try {
            preencherTabela(context.getControleTarefa().listarTarefas());
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void listarMinhas() {
        try {
            preencherTabela(context.getControleTarefa().listarMinhasTarefas());
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void preencherTabela(List<Tarefa> tarefas) {
        model.setRowCount(0);
        for (Tarefa tarefa : tarefas) {
            model.addRow(new Object[]{
                    tarefa.getId(),
                    tarefa.getTitulo(),
                    tarefa.getPrioridade(),
                    tarefa.getStatus(),
                    tarefa.getResponsavel() == null ? "" : tarefa.getResponsavel().getNomeCompleto(),
                    tarefa.getProjeto() == null ? "" : tarefa.getProjeto().getNome()
            });
        }
    }

    private Tarefa tarefaSelecionada() {
        int id = SwingUtil.idSelecionado(tabela, 0);
        return context.getControleTarefa().buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada."));
    }

    private void editarSelecionada() {
        try {
            abrirFormulario(tarefaSelecionada());
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void removerSelecionada() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            if (javax.swing.JOptionPane.showConfirmDialog(this, "Remover a tarefa selecionada?", "Confirmação",
                    javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
                context.getControleTarefa().removerTarefa(id);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void atualizarStatus() {
        try {
            Tarefa tarefa = tarefaSelecionada();
            JComboBox<StatusTarefa> combo = new JComboBox<>(StatusTarefa.values());
            combo.setSelectedItem(tarefa.getStatus());
            int opcao = javax.swing.JOptionPane.showConfirmDialog(this, combo, "Novo status",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (opcao == javax.swing.JOptionPane.OK_OPTION) {
                context.getControleTarefa().atualizarStatus(tarefa, (StatusTarefa) combo.getSelectedItem());
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void abrirFormulario(Tarefa tarefa) {
        TarefaDialog dialog = new TarefaDialog((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), tarefa);
        dialog.setVisible(true);
        if (dialog.salvou()) {
            atualizarTabela();
        }
    }

    private class TarefaDialog extends JDialog {

        private final Tarefa tarefa;
        private boolean salvou;
        private final JTextField titulo = new JTextField(28);
        private final JTextField descricao = new JTextField(28);
        private final JComboBox<PrioridadeTarefa> prioridade = new JComboBox<>(PrioridadeTarefa.values());
        private final JComboBox<StatusTarefa> status = new JComboBox<>(StatusTarefa.values());
        private final JComboBox<Usuario> responsavel = new JComboBox<>();
        private final JComboBox<Projeto> projeto = new JComboBox<>();

        TarefaDialog(Frame owner, Tarefa tarefa) {
            super(owner, tarefa == null ? "Cadastrar tarefa" : "Editar tarefa", true);
            this.tarefa = tarefa;
            setSize(580, 430);
            setLocationRelativeTo(owner);
            montar();
            preencherCombos();
            preencher();
        }

        private void montar() {
            JPanel conteudo = SwingUtil.painelConteudo(tarefa == null ? "Cadastrar tarefa" : "Editar tarefa");
            JPanel form = SwingUtil.formPanel();
            SwingUtil.addCampo(form, 0, "Título:", titulo);
            SwingUtil.addCampo(form, 1, "Descrição:", descricao);
            SwingUtil.addCampo(form, 2, "Prioridade:", prioridade);
            SwingUtil.addCampo(form, 3, "Status:", status);
            SwingUtil.addCampo(form, 4, "Responsável:", responsavel);
            SwingUtil.addCampo(form, 5, "Projeto:", projeto);
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

        private void preencherCombos() {
            SwingUtil.preencherCombo(responsavel, context.getControleUsuario().listarUsuarios());
            SwingUtil.preencherCombo(projeto, context.getControleProjeto().listarProjetos());
        }

        private void preencher() {
            if (tarefa == null) {
                return;
            }
            titulo.setText(tarefa.getTitulo());
            descricao.setText(tarefa.getDescricao());
            prioridade.setSelectedItem(tarefa.getPrioridade());
            status.setSelectedItem(tarefa.getStatus());
            selecionarUsuario(tarefa.getResponsavel());
            selecionarProjeto(tarefa.getProjeto());
        }

        private void selecionarUsuario(Usuario usuario) {
            if (usuario == null) {
                return;
            }
            for (int i = 0; i < responsavel.getItemCount(); i++) {
                if (responsavel.getItemAt(i).getId() == usuario.getId()) {
                    responsavel.setSelectedIndex(i);
                    return;
                }
            }
        }

        private void selecionarProjeto(Projeto projetoSelecionado) {
            if (projetoSelecionado == null) {
                return;
            }
            for (int i = 0; i < projeto.getItemCount(); i++) {
                if (projeto.getItemAt(i).getId() == projetoSelecionado.getId()) {
                    projeto.setSelectedIndex(i);
                    return;
                }
            }
        }

        private void salvar() {
            try {
                Tarefa nova = new Tarefa(
                        tarefa == null ? 0 : tarefa.getId(),
                        SwingUtil.texto(titulo),
                        SwingUtil.texto(descricao),
                        (PrioridadeTarefa) prioridade.getSelectedItem(),
                        (StatusTarefa) status.getSelectedItem(),
                        (Usuario) responsavel.getSelectedItem(),
                        (Projeto) projeto.getSelectedItem()
                );
                if (tarefa == null) {
                    context.getControleTarefa().cadastrarTarefa(nova);
                } else {
                    context.getControleTarefa().editarTarefa(nova);
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
