
package model;

public class JogadorVIP extends Jogador {

    public JogadorVIP(String nome) {
        super(nome);
    }

    @Override
    public int calcularPontuacao(String resultado, int totalJogadas, int tempoSegundos) {
        int pontuacaoBase = super.calcularPontuacao(resultado, totalJogadas, tempoSegundos);

        if (resultado.equalsIgnoreCase("VITORIA")) {
            return pontuacaoBase + 20;
        }

        return pontuacaoBase;
    }

    @Override
    public String getTipo() {
        return "VIP";
    }
}
