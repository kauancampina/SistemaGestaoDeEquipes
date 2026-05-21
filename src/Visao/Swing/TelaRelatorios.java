package Visao.Swing;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class TelaRelatorios extends JPanel {

    private final AppContext context;
    private final JTextArea resumo = new JTextArea();

    public TelaRelatorios(AppContext context) {
        super(new BorderLayout());
        this.context = context;
        montarTela();
        atualizar();
    }

    private void montarTela() {
        JPanel conteudo = SwingUtil.painelConteudo("Relatórios");
        resumo.setEditable(false);
        resumo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        resumo.setLineWrap(true);
        resumo.setWrapStyleWord(true);

        JButton atualizar = new JButton("Atualizar relatório");
        atualizar.addActionListener(event -> atualizar());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(atualizar);

        conteudo.add(botoes, BorderLayout.SOUTH);
        conteudo.add(new JScrollPane(resumo), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void atualizar() {
        try {
            resumo.setText(context.getControleRelatorio().gerarResumoGeral());
            resumo.setCaretPosition(0);
        } catch (RuntimeException ex) {
            SwingUtil.erro(this, ex);
        }
    }
}
