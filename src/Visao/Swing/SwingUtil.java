package Visao.Swing;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

final class SwingUtil {

    static final Color FUNDO = new Color(245, 247, 250);
    static final Color LATERAL = new Color(37, 48, 65);
    static final Color PRIMARIA = new Color(35, 101, 171);
    static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private SwingUtil() {
    }

    static void aplicarTema() {
        UIManager.put("Panel.background", FUNDO);
        UIManager.put("Button.focusPainted", false);
        UIManager.put("Table.gridColor", new Color(220, 225, 232));
        UIManager.put("Table.selectionBackground", new Color(210, 227, 247));
        UIManager.put("Table.selectionForeground", Color.BLACK);
    }

    static JPanel painelConteudo(String titulo) {
        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel label = new JLabel(titulo);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 22f));
        painel.add(label, BorderLayout.NORTH);
        return painel;
    }

    static JPanel formPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    static void addCampo(JPanel panel, int linha, String rotulo, JComponent campo) {
        GridBagConstraints label = new GridBagConstraints();
        label.gridx = 0;
        label.gridy = linha;
        label.anchor = GridBagConstraints.LINE_END;
        label.insets = new Insets(6, 6, 6, 8);
        panel.add(new JLabel(rotulo), label);

        GridBagConstraints input = new GridBagConstraints();
        input.gridx = 1;
        input.gridy = linha;
        input.weightx = 1;
        input.fill = GridBagConstraints.HORIZONTAL;
        input.insets = new Insets(6, 0, 6, 6);
        panel.add(campo, input);
    }

    static JTable tabela(DefaultTableModel model) {
        JTable tabela = new JTable(model);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(28);
        tabela.setAutoCreateRowSorter(true);
        JTableHeader header = tabela.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        return tabela;
    }

    static DefaultTableModel modeloTabela(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    static void erro(Component parent, RuntimeException ex) {
        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    static void info(Component parent, String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    static int idSelecionado(JTable tabela, int colunaId) {
        int viewRow = tabela.getSelectedRow();
        if (viewRow < 0) {
            throw new IllegalArgumentException("Selecione um registro na tabela.");
        }
        int modelRow = tabela.convertRowIndexToModel(viewRow);
        return (Integer) tabela.getModel().getValueAt(modelRow, colunaId);
    }

    static LocalDate data(String valor) {
        try {
            return LocalDate.parse(valor.trim(), DATA);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Informe a data no formato DD-MM-AAAA.");
        }
    }

    static String texto(JTextField campo) {
        return campo.getText().trim();
    }

    static <T> void preencherCombo(JComboBox<T> combo, Iterable<T> itens) {
        combo.removeAllItems();
        for (T item : itens) {
            combo.addItem(item);
        }
    }
}
