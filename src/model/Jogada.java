
package model;

public class Jogada {

    private final int linha;
    private final int coluna;
    private final char simbolo;
    private final int numeroJogada;

    public Jogada(int linha, int coluna, char simbolo, int numeroJogada) {
        this.linha = linha;
        this.coluna = coluna;
        this.simbolo = simbolo;
        this.numeroJogada = numeroJogada;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getNumeroJogada() {
        return numeroJogada;
    }

    @Override
    public String toString() {
        return "jogada" + numeroJogada
                + " - linha" + linha
                + ", coluna" + coluna
                + ", simbolo" + simbolo;
    }
}
