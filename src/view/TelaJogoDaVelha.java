package view;

import config.ConfiguracaoGlobal;
import dao.RankingDao;
import interfaces.AtualizavelTela;
import model.Jogador;
import model.JogadorVIP;
import model.Pessoa;
import service.CronometroJogo;
import service.JogoDaVelha;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class TelaJogoDaVelha extends JFrame implements AtualizavelTela {

    private JTextField txtNome;
    private JComboBox<String> cbTipoJogador;
    private JButton[][] botoes;
    private JButton btnNovoJogo;
    private JButton btnSalvar;
    private JButton btnRanking;
    private JLabel lblStatus;
    private JLabel lblTempo;
    private JTextArea areaHistorico;

    private JogoDaVelha jogo;
    private RankingDao rankingDAO;
    private CronometroJogo cronometro;

    public TelaJogoDaVelha() {
        rankingDAO = new RankingDao();

        configurarJanela();
        criarComponentes();
        montarLayout();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle(ConfiguracaoGlobal.NOME_SISTEMA + " - Java Swing + MySQL");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        txtNome = new JTextField(18);

        cbTipoJogador = new JComboBox<String>();
        cbTipoJogador.addItem("Comum");
        cbTipoJogador.addItem("VIP");

        botoes = new JButton[3][3];

        Font fonteBotao = new Font("Arial", Font.BOLD, 48);

        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                botoes[linha][coluna] = new JButton("");
                botoes[linha][coluna].setFont(fonteBotao);
                botoes[linha][coluna].setFocusPainted(false);
                botoes[linha][coluna].setEnabled(false);
            }
        }

        btnNovoJogo = new JButton("Novo Jogo");
        btnSalvar = new JButton("Salvar no Banco");
        btnRanking = new JButton("Ver Ranking");

        btnSalvar.setEnabled(false);

        lblStatus = new JLabel("Informe o nome do jogador e clique em Novo Jogo.");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));

        lblTempo = new JLabel("Tempo: 0s");
        lblTempo.setFont(new Font("Arial", Font.BOLD, 14));

        areaHistorico = new JTextArea();
        areaHistorico.setEditable(false);
        areaHistorico.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void montarLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout());

        painelTopo.add(new JLabel("Nome:"));
        painelTopo.add(txtNome);
        painelTopo.add(new JLabel("Tipo:"));
        painelTopo.add(cbTipoJogador);
        painelTopo.add(btnNovoJogo);
        painelTopo.add(btnSalvar);
        painelTopo.add(btnRanking);
        painelTopo.add(lblTempo);

        JPanel painelTabuleiro = new JPanel(new GridLayout(3, 3, 5, 5));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                painelTabuleiro.add(botoes[linha][coluna]);
            }
        }

        JPanel painelDireita = new JPanel(new BorderLayout());
        painelDireita.setBorder(BorderFactory.createTitledBorder("Histórico / Ranking"));
        painelDireita.add(new JScrollPane(areaHistorico), BorderLayout.CENTER);
        painelDireita.setPreferredSize(new Dimension(380, 0));

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelRodape.add(lblStatus);

        add(painelTopo, BorderLayout.NORTH);
        add(painelTabuleiro, BorderLayout.CENTER);
        add(painelDireita, BorderLayout.EAST);
        add(painelRodape, BorderLayout.SOUTH);
    }

    private void configurarEventos() {

        btnNovoJogo.addActionListener(e -> iniciarNovoJogo());

        btnSalvar.addActionListener(e -> salvarPartida());

        btnRanking.addActionListener(e -> carregarRanking());

        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                final int l = linha;
                final int c = coluna;

                botoes[linha][coluna].addActionListener(e -> realizarJogada(l, c));
            }
        }
    }

    private void iniciarNovoJogo() {
        String nome = txtNome.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome do jogador.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Jogador jogador;

        if (cbTipoJogador.getSelectedItem().toString().equals("VIP")) {
            jogador = new JogadorVIP(nome);
        } else {
            jogador = new Jogador(nome);
        }

        jogo = new JogoDaVelha(jogador);

        limparTabuleiro();
        habilitarTabuleiro(true);

        areaHistorico.setText("");
        btnSalvar.setEnabled(false);

        lblStatus.setText("Jogo iniciado. Vez do jogador " + nome + " [X].");
        lblTempo.setText("Tempo: 0s");

        if (cronometro != null) {
            cronometro.parar();
        }

        cronometro = new CronometroJogo(this);
        cronometro.start();
    }

    private void realizarJogada(int linha, int coluna) {
        if (jogo == null) {
            return;
        }

        boolean jogadaValida = jogo.realizarJogadaJogador(linha, coluna);

        if (!jogadaValida) {
            JOptionPane.showMessageDialog(
                    this,
                    "Jogada inválida. Escolha uma posição vazia.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        atualizarTabuleiro();

        if (jogo.jogoEmAndamento()) {
            jogo.realizarJogadaComputador();
            atualizarTabuleiro();
        }

        areaHistorico.setText(jogo.obterHistoricoTexto());

        if (!jogo.jogoEmAndamento()) {
            finalizarJogo();
        }
    }

    private void atualizarTabuleiro() {
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                char valor = jogo.getTabuleiro().getValor(linha, coluna);

                if (valor == ' ') {
                    botoes[linha][coluna].setText("");
                } else {
                    botoes[linha][coluna].setText(String.valueOf(valor));
                }
            }
        }
    }

    private void finalizarJogo() {
        habilitarTabuleiro(false);
        btnSalvar.setEnabled(true);

        if (cronometro != null) {
            cronometro.parar();
            jogo.setTempoSegundos(cronometro.getSegundos());
        }

        String mensagem = "Resultado: " + jogo.getResultado()
                + " | Pontuação: " + jogo.getJogador().getPontuacao()
                + " | Tempo: " + jogo.getTempoSegundos() + "s";

        lblStatus.setText(mensagem);

        areaHistorico.append("\n");
        areaHistorico.append("Total de pessoas criadas: "
                + Pessoa.getTotalPessoasCriadas() + "\n");
        areaHistorico.append("Total de jogadores criados: "
                + Jogador.getTotalJogadoresCriados() + "\n");

        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Fim da partida",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void salvarPartida() {
        if (jogo == null || jogo.jogoEmAndamento()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Finalize a partida antes de salvar.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            rankingDAO.salvarResultado(jogo);

            JOptionPane.showMessageDialog(
                    this,
                    "Partida salva com sucesso no MySQL!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            btnSalvar.setEnabled(false);

            carregarRanking();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar no banco: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void carregarRanking() {
        try {
            String ranking = rankingDAO.listarRanking();
            areaHistorico.setText(ranking);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar ranking: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limparTabuleiro() {
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                botoes[linha][coluna].setText("");
            }
        }
    }

    private void habilitarTabuleiro(boolean habilitar) {
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                botoes[linha][coluna].setEnabled(habilitar);
            }
        }
    }

    @Override
    public void atualizarTempo(int segundos) {

        SwingUtilities.invokeLater(() -> {
            lblTempo.setText("Tempo: " + segundos + "s");

            if (jogo != null && jogo.jogoEmAndamento()) {
                jogo.setTempoSegundos(segundos);
            }
        });
    }
}