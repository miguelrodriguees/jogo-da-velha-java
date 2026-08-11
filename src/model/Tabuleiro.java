
package model;

import config.ConfiguracaoGlobal;

public class Tabuleiro {

    private final char[][] matriz;

    public Tabuleiro() {
        matriz = new char[ConfiguracaoGlobal.TAMANHO_TABULEIRO][ConfiguracaoGlobal.TAMANHO_TABULEIRO];

        inicializar();

    }

    public void inicializar() {
        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                matriz[linha][coluna] = ' ';
            }
        }
    }

    public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0
                && linha < ConfiguracaoGlobal.TAMANHO_TABULEIRO
                && coluna >= 0
                && coluna < ConfiguracaoGlobal.TAMANHO_TABULEIRO
                && matriz[linha][coluna] == ' ';
    }

    public boolean marcarPosicao(int linha, int coluna, char simbolo){
        if(posicaoValida(linha,coluna)){
            matriz [linha] [coluna] = simbolo;
            return true;
        }
        return false;
    }
    
    public boolean verificarVencedor (char simbolo){
        for(int i = 0; i < ConfiguracaoGlobal.TAMANHO_TABULEIRO; i++){
            
            if(matriz [i][0] == simbolo
                &&matriz[i][1] == simbolo
                &&matriz[1][2] == simbolo){
                return true;
            }
            
            if (matriz[0][1] == simbolo
                    &&matriz[1][1] == simbolo
                    &&matriz[2][i] == simbolo){
                return true;
            }
        }
        
        if (matriz [0][0] ==simbolo
                && matriz [1][1] == simbolo
                && matriz [2][2] == simbolo){
            return true;
        }
        if ((matriz[0][2] == simbolo
        && matriz[1][1] == simbolo
        && matriz[2][0] == simbolo)) {
    return true;
}

return false;
}

public boolean estaCheio() {
    for (int linha = 0; linha < matriz.length; linha++) {
        for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
            if (matriz[linha][coluna] == ' ') {
                return false;
            }
        }
    }

    return true;
}

public char getValor(int linha, int coluna) {
    return matriz[linha][coluna];
}
    }

