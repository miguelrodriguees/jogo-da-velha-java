
package model;

import interfaces.Pontuavel;

public class Jogador extends Pessoa implements Pontuavel {

    private int id;
    private int pontuacao;
    private static int totalJogadoresCriados = 0;

    public Jogador(String nome) {
        super(nome);
        this.pontuacao = 0;
        totalJogadoresCriados++;
    }

    @Override
    public int calcularPontuacao(String resultado, int totalJogadas, int tempoSegundos) {
        if (resultado.equalsIgnoreCase("VITÓRIA")) {
            int pontos = 120 - (totalJogadas * 5) - tempoSegundos;

            return Math.max(10, pontos);
        } else if (resultado.equalsIgnoreCase("EMPATE")) {
            return 30;
        } else {
            return 0;
        }
    }

    @Override
    public String getTipo() {
        return "COMUM";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        if (pontuacao >= 0) {
            this.pontuacao = pontuacao;
        }
    }

    public static int getTotalJogadoresCriados() {
        return totalJogadoresCriados;
    }
}
