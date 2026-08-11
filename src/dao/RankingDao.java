package dao;

import model.Jogada;
import model.Jogador;
import service.JogoDaVelha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RankingDao {

    public void salvarResultado(JogoDaVelha jogo) throws Exception {

        String sqlJogador
                = "INSERT INTO jogadores (nome, tipo) VALUES (?, ?)";

        String sqlPartida
                = "INSERT INTO partidas "
                + "(id_jogador, pontuacao, resultado, total_jogadas, tempo_segundos) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlJogada
                = "INSERT INTO jogadas "
                + "(id_partida, linha, coluna, simbolo, numero_jogada) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);
            Jogador jogador = jogo.getJogador();
            
            int idJogador;
            
            try (PreparedStatement stmt = conn.prepareStatement(
                    sqlJogador,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                stmt.setString(1, jogador.getNome());
                stmt.setString(2, jogador.getTipo());
                
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                
                idJogador = rs.getInt(1);
            }
            
            int idPartida;

            try (PreparedStatement stmt = conn.prepareStatement(
                    sqlPartida,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                stmt.setInt(1, idJogador);
                stmt.setInt(2, jogador.getPontuacao());
                stmt.setString(3, jogo.getResultado());
                stmt.setInt(4, jogo.getTotalJogadas());
                stmt.setInt(5, jogo.getTempoSegundos());
                
                stmt.executeUpdate();
                
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                
                idPartida = rs.getInt(1);
            }
         
            try (PreparedStatement stmt = conn.prepareStatement(sqlJogada)) {
                
                for (Jogada jogada : jogo.getHistoricoJogadas()) {
                    stmt.setInt(1, idPartida);
                    stmt.setInt(2, jogada.getLinha());
                    stmt.setInt(3, jogada.getColuna());
                    stmt.setString(4, String.valueOf(jogada.getSimbolo()));
                    stmt.setInt(5, jogada.getNumeroJogada());
              
                    stmt.addBatch();
                }
                
                stmt.executeBatch();
            }
            
            conn.commit();
            
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String listarRanking() throws Exception {

        String sql
                = "SELECT "
                + "j.nome, "
                + "j.tipo, "
                + "p.pontuacao, "
                + "p.resultado, "
                + "p.total_jogadas, "
                + "p.tempo_segundos, "
                + "DATE_FORMAT(p.data_partida, '%d/%m/%Y %H:%i') AS data_partida "
                + "FROM partidas p "
                + "INNER JOIN jogadores j "
                + "ON p.id_jogador = j.id_jogador "
                + "ORDER BY p.pontuacao DESC, p.data_partida DESC "
                + "LIMIT 10;";

        StringBuilder ranking = new StringBuilder();

        try (Connection conn = Conexao.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            ranking.append("===== TOP 10 RANKING =====\n\n");

            int posicao = 1;

            while (rs.next()) {
                ranking.append(posicao).append("º - ")
                        .append(rs.getString("nome"))
                        .append(" | Tipo: ")
                        .append(rs.getString("tipo"))
                        .append(" | Pontos: ")
                        .append(rs.getInt("pontuacao"))
                        .append(" | Resultado: ")
                        .append(rs.getString("resultado"))
                        .append(" | Jogadas: ")
                        .append(rs.getInt("total_jogadas"))
                        .append(" | Tempo: ")
                        .append(rs.getInt("tempo_segundos"))
                        .append("s")
                        .append(" | Data: ")
                        .append(rs.getString("data_partida"))
                        .append("\n");

                posicao++;
            }

            if (posicao == 1) {
                ranking.append("Nenhuma partida salva ainda.");
            }
        }

        return ranking.toString();
    }
}
