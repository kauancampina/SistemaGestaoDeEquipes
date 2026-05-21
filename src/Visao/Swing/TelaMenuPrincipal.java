package Visao.Swing;

import Modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class TelaMenuPrincipal extends JFrame {

    private final AppContext context;
    private final CardLayout cards = new CardLayout();
    private final JPanel areaCentral = new JPanel(cards);

    public TelaMenuPrincipal(AppContext context, Usuario usuarioLogado) {
        this.context = context;
        setTitle("Sistema de Gestão de Projetos e Equipes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 720);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);
        montarTela(usuarioLogado);
    }

    private void montarTela(Usuario usuarioLogado) {
        JPanel lateral = new JPanel(new BorderLayout());
        lateral.setBackground(SwingUtil.LATERAL);
        lateral.setPreferredSize(new Dimension(230, 0));

        JLabel titulo = new JLabel("<html><b>Sistema de Gestão</b><br>" + usuarioLogado.getNomeCompleto() + "<br>" + usuarioLogado.getPerfil() + "</html>");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.PLAIN, 14f));
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 18, 22, 18));
        lateral.add(titulo, BorderLayout.NORTH);

        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 6));
        menu.setBackground(SwingUtil.LATERAL);
        menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        adicionarBotao(menu, "Usuários", "usuarios");
        adicionarBotao(menu, "Projetos", "projetos");
        adicionarBotao(menu, "Equipes", "equipes");
        adicionarBotao(menu, "Tarefas", "tarefas");
        adicionarBotao(menu, "Relatórios", "relatorios");
        lateral.add(menu, BorderLayout.CENTER);

        JButton sair = botaoMenu("Sair");
        sair.addActionListener(event -> {
            context.getControleUsuario().logout();
            new TelaLogin(new AppContext()).setVisible(true);
            dispose();
        });
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.setBackground(SwingUtil.LATERAL);
        rodape.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 18, 14));
        rodape.add(sair);
        lateral.add(rodape, BorderLayout.SOUTH);

        areaCentral.add(new TelaUsuario(context), "usuarios");
        areaCentral.add(new TelaProjeto(context), "projetos");
        areaCentral.add(new TelaEquipe(context), "equipes");
        areaCentral.add(new TelaTarefa(context), "tarefas");
        areaCentral.add(new TelaRelatorios(context), "relatorios");

        add(lateral, BorderLayout.WEST);
        add(areaCentral, BorderLayout.CENTER);
        cards.show(areaCentral, "usuarios");
    }

    private void adicionarBotao(JPanel menu, String texto, String card) {
        JButton botao = botaoMenu(texto);
        botao.addActionListener(event -> cards.show(areaCentral, card));
        menu.add(botao);
    }

    private JButton botaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(52, 66, 88));
        botao.setForeground(Color.WHITE);
        botao.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12));
        return botao;
    }
}
