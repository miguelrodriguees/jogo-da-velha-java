CREATE DATABASE db_jogo_velha_uc12;

USE db_jogo_velha_uc12;

CREATE TABLE jogadores (
    id_jogador INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE partidas (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    id_jogador INT NOT NULL,
    pontuacao INT NOT NULL,
    resultado VARCHAR(30) NOT NULL,
    total_jogadas INT NOT NULL,
    tempo_segundos INT NOT NULL,
    data_partida DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_partidas_jogadores
        FOREIGN KEY (id_jogador)
        REFERENCES jogadores(id_jogador)
);

CREATE TABLE jogadas (
    id_jogada INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    linha INT NOT NULL,
    coluna INT NOT NULL,
    simbolo CHAR(1) NOT NULL,
    numero_jogada INT NOT NULL,

    CONSTRAINT fk_jogadas_partidas
        FOREIGN KEY (id_partida)
        REFERENCES partidas(id_partida)
);
