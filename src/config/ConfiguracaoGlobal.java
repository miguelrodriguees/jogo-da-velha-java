
package config;

public class ConfiguracaoGlobal {

    public static final String NOME_SISTEMA = "Jogo da velha UC12";

    public static final int TAMANHO_TABULEIRO = 3;

    public static final int MAXIMO_JOGADAS = 9;

    public static final String BANCO_URL
            = "jdbc:mysql://localhost:3306/db_jogo_velha_uc12"
            + "?useSSL=false"
            + "&serverTimezone=America/Sao_Paulo"
            + "&allowPublicKeyRetrieval=true";

    public static final String BANCO_USUARIO = "root";

    public static final String BANCO_SENHA = "";

    private ConfiguracaoGlobal() {

    }
}
