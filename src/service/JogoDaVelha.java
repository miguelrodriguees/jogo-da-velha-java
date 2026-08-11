
package service;

import model.Jogada;
import model.Jogador;
import model.Tabuleiro;

import java.util.ArrayList;

public class JogoDaVelha {
    private Tabuleiro tabuleiro;
    private Jogador jogador;
    
    private ArrayList<Jogada> historicoJogadas;

    private int totalJogadas;
    private int tempoSegundos;
    private String resultado;

    public JogoDaVelha(Jogador jogador) {
        this.jogador = jogador;
        this.tabuleiro = new Tabuleiro();
        this.historicoJogadas = new ArrayList<>();
        this.totalJogadas = 0;
        this.tempoSegundos = 0;
        this.resultado = "EM ANDAMENTO";
    }

    public boolean realizarJogadaJogador(int linha, int coluna) {

        if (!resultado.equals("EM ANDAMENTO")) {
            return false;
        }

        boolean marcou = tabuleiro.marcarPosicao(linha, coluna, 'X');

        if (marcou) {
            registrarJogada(linha, coluna, 'X');
            verificarFimDoJogo('X');
        }

        return marcou;
    }

    public void realizarJogadaComputador() {

        if (!resultado.equals("EM ANDAMENTO")) {
            return;
        }

        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {

                if (tabuleiro.marcarPosicao(linha, coluna, 'O')) {
                    registrarJogada(linha, coluna, 'O');
                    verificarFimDoJogo('O');
                    return;
                }
            }
        }
    }

    private void registrarJogada(int linha, int coluna, char simbolo) {

        totalJogadas++;

        Jogada jogada = new Jogada(linha, coluna, simbolo, totalJogadas);

        historicoJogadas.add(jogada);
    }

    private void verificarFimDoJogo(char simbolo) {

        if (tabuleiro.verificarVencedor(simbolo)) {

            if (simbolo == 'X') {
                resultado = "VITORIA";
            } else {
                resultado = "DERROTA";
            }

            jogador.setPontuacao(
                    jogador.calcularPontuacao(resultado, totalJogadas, tempoSegundos)
            );

        } else if (tabuleiro.estaCheio()) {

            resultado = "EMPATE";

            jogador.setPontuacao(
                    jogador.calcularPontuacao(resultado, totalJogadas, tempoSegundos)
            );
        }
    }

    public String obterHistoricoTexto() {

        StringBuilder texto = new StringBuilder();

        for (Jogada jogada : historicoJogadas) {
            texto.append(jogada.toString()).append("\n");
        }

        return texto.toString();
    }

    public boolean jogoEmAndamento() {
        return resultado.equals("EM ANDAMENTO");
    }

    public void setTempoSegundos(int tempoSegundos) {
        this.tempoSegundos = tempoSegundos;

        if (!resultado.equals("EM ANDAMENTO")) {
            jogador.setPontuacao(
                    jogador.calcularPontuacao(resultado, totalJogadas, tempoSegundos)
            );
        }
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public ArrayList<Jogada> getHistoricoJogadas() {
        return historicoJogadas;
    }

    public int getTotalJogadas() {
        return totalJogadas;
    }

    public int getTempoSegundos() {
        return tempoSegundos;
    }

    public String getResultado() {
        return resultado;
    }
}

    