# Jogo da Velha em Java

Projeto desenvolvido em Java no NetBeans durante meus estudos de programação.

O jogo possui interface gráfica com Swing, dois jogadores, cronômetro, histórico de jogadas e ranking salvo em banco de dados MySQL.

Durante o desenvolvimento pratiquei conteúdos como:

- orientação a objetos;
- herança e interfaces;
- listas e organização em camadas;
- JDBC para conexão com MySQL;
- criação de tabelas, chaves primárias e estrangeiras;
- relacionamento entre jogadores, partidas e jogadas.

## Organização do projeto

```text
src/
├── app/
├── config/
├── dao/
├── interfaces/
├── model/
├── service/
└── view/

database/
└── schema.sql
```

## Banco de dados

O arquivo `database/schema.sql` contém a estrutura utilizada pelo projeto.

O banco possui três tabelas principais:

```text
JOGADORES
    ↓
PARTIDAS
    ↓
JOGADAS
```

- `jogadores`: armazena os jogadores cadastrados;
- `partidas`: registra o resultado, pontuação, quantidade de jogadas e tempo da partida;
- `jogadas`: guarda o histórico de cada movimento realizado durante uma partida.

Para executar o projeto é necessário ter o Java instalado, adicionar o MySQL Connector/J ao projeto e configurar a conexão com o banco de dados.

Este repositório também faz parte do meu processo de aprendizagem em Java e MySQL.
