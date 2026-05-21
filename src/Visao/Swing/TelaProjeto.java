package Visao.Swing;

import Modelo.Equipe;
import Modelo.PerfilUsuario;
import Modelo.Projeto;
import Modelo.StatusProjeto;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaProjeto extends JPanel {

    private final AppContext context;
    private final DefaultTableModel model = SwingUtil.modeloTabela("ID", "Nome", "Início", "Término", "Status", "Gerente", "Equipes", "Tarefas");
    private final JTable tabela = SwingUtil.tabela(model);

    public TelaProjeto(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        montarTela();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Projetos");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton novo = new JButton("Cadastrar");
        JButton editar = new JButton("Editar");
        JButton remover = new JButton("Remover");
        JButton associarEquipe = new JButton("Associar equipe");
        JButton atualizar = new JButton("Atualizar");

        novo.addActionListener(event -> abrirFormulario(null));
        editar.addActionListener(event -> editarSelecionado());
        remover.addActionListener(event -> removerSelecionado());
        associarEquipe.addActionListener(event -> associarEquipe());
        atualizar.addActionListener(event -> atualizarTabela());

        botoes.add(novo);
        botoes.add(editar);
        botoes.add(remover);
        botoes.add(associarEquipe);
        botoes.add(atualizar);
        conteudo.add(botoes, BorderLayout.SOUTH);
        conteudo.add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        try {
            model.setRowCount(0);
            DateTimeFormatter formatter = SwingUtil.DATA;
            for (Projeto projeto : context.getControleProjeto().listarProjetos()) {
                model.addRow(new Object[]{
                        projeto.getId(),
                        projeto.getNome(),
                        projeto.getDataInicio().format(formatter),
                        projeto.getDataTermino().format(formatter),
                        projeto.getStatus(),
                        projeto.getGerente() == null ? "" : projeto.getGerente().getNomeCompleto(),
                        projeto.getEquipes().size(),
                        projeto.getTarefas().size()
                });
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void editarSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            context.getControleProjeto().buscarPorId(id).ifPresent(this::abrirFormulario);
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void removerSelecionado() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            if (javax.swing.JOptionPane.showConfirmDialog(this, "Remover o projeto selecionado?", "Confirmação",
                    javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION) {
                context.getControleProjeto().removerProjeto(id);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void associarEquipe() {
        try {
            int id = SwingUtil.idSelecionado(tabela, 0);
            Projeto projeto = context.getControleProjeto().buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado."));
            List<Equipe> equipes = context.getControleEquipe().listarEquipes();
            JComboBox<Equipe> combo = new JComboBox<>(equipes.toArray(new Equipe[0]));
            int opcao = javax.swing.JOptionPane.showConfirmDialog(this, combo, "Selecione a equipe",
                    javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (opcao == javax.swing.JOptionPane.OK_OPTION && combo.getSelectedItem() != null) {
                Equipe equipe = (Equipe) combo.getSelectedItem();
                context.getControleProjeto().adicionarEquipeAoProjeto(projeto, equipe);
                context.getControleEquipe().adicionarProjeto(equipe, projeto);
                atualizarTabela();
            }
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }

    private void abrirFormulario(Projeto projeto) {
        ProjetoDialog dialog = new ProjetoDialog((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), projeto);
        dialog.setVisible(true);
        if (dialog.salvou()) {
            atualizarTabela();
        }
    }

    private class ProjetoDialog extends JDialog {

        private final Projeto projeto;
        private boolean salvou;
        private final JTextField nome = new JTextField(26);
        private final JTextField descricao = new JTextField(26);
        private final JTextField inicio = new JTextField(26);
        private final JTextField termino = new JTextField(26);
        private final JComboBox<StatusProjeto> status = new JComboBox<>(StatusProjeto.values());
        private final JComboBox<Usuario> gerente = new JComboBox<>();

        ProjetoDialog(Frame owner, Projeto projeto) {
            super(owner, projeto == null ? "Cadastrar projeto" : "Editar projeto", true);
            this.projeto = projeto;
            setSize(560, 430);
            setLocationRelativeTo(owner);
            montar();
            preencherCombos();
            preencher();
        }

        private void montar() {
            JPanel conteudo = SwingUtil.painelConteudo(projeto == null ? "Cadastrar projeto" : "Editar projeto");
            JPanel form = SwingUtil.formPanel();
            SwingUtil.addCampo(form, 0, "Nome:", nome);
            SwingUtil.addCampo(form, 1, "Descrição:", descricao);
            SwingUtil.addCampo(form, 2, "Início:", inicio);
            SwingUtil.addCampo(form, 3, "Término:", termino);
            SwingUtil.addCampo(form, 4, "Status:", status);
            SwingUtil.addCampo(form, 5, "Gerente:", gerente);
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
            gerente.removeAllItems();
            for (Usuario usuario : context.getControleUsuario().listarUsuarios()) {
                if (usuario.getPerfil() == PerfilUsuario.ADMINISTRADOR || usuario.getPerfil() == PerfilUsuario.GERENTE) {
                    gerente.addItem(usuario);
                }
            }
        }

        private void preencher() {
            if (projeto == null) {
                inicio.setText("01-01-2026");
                termino.setText("31-12-2026");
                return;
            }
            nome.setText(projeto.getNome());
            descricao.setText(projeto.getDescricao());
            inicio.setText(projeto.getDataInicio().format(SwingUtil.DATA));
            termino.setText(projeto.getDataTermino().format(SwingUtil.DATA));
            status.setSelectedItem(projeto.getStatus());
            selecionarGerente(projeto.getGerente());
        }

        private void selecionarGerente(Usuario usuario) {
            if (usuario == null) {
                return;
            }
            for (int i = 0; i < gerente.getItemCount(); i++) {
                if (gerente.getItemAt(i).getId() == usuario.getId()) {
                    gerente.setSelectedIndex(i);
                    return;
                }
            }
        }

        private void salvar() {
            try {
                Projeto novo = new Projeto(
                        projeto == null ? 0 : projeto.getId(),
                        SwingUtil.texto(nome),
                        SwingUtil.texto(descricao),
                        SwingUtil.data(SwingUtil.texto(inicio)),
                        SwingUtil.data(SwingUtil.texto(termino)),
                        (StatusProjeto) status.getSelectedItem(),
                        (Usuario) gerente.getSelectedItem()
                );
                if (projeto == null) {
                    context.getControleProjeto().cadastrarProjeto(novo);
                } else {
                    for (Equipe equipe : projeto.getEquipes()) {
                        novo.adicionarEquipe(equipe);
                    }
                    for (Modelo.Tarefa tarefa : projeto.getTarefas()) {
                        novo.adicionarTarefa(tarefa);
                    }
                    context.getControleProjeto().editarProjeto(novo);
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
