
package dao;

import config.ConfiguracaoGlobal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                ConfiguracaoGlobal.BANCO_URL,
                ConfiguracaoGlobal.BANCO_USUARIO,
                ConfiguracaoGlobal.BANCO_SENHA
        );
    }
}
