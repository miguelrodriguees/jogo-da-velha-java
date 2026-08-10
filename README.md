# Jogo da Velha em Java

Jogo da velha com interface gráfica, histórico de jogadas e ranking persistido em banco de dados.

## Recursos

- Interface gráfica construída com Java Swing
- Partidas para dois jogadores
- Validação das jogadas e identificação do vencedor
- Cronômetro de partida
- Histórico de jogadas
- Jogadores comuns e VIP
- Persistência de partidas e ranking com JDBC

## Estrutura

```text
src/
├── app/          # inicialização da aplicação
├── config/       # configuração global
├── dao/          # conexão e operações no banco
├── interfaces/   # contratos compartilhados
├── model/        # jogadores, jogadas e tabuleiro
├── service/      # regras do jogo e cronômetro
└── view/         # interface Swing
```

## Tecnologias

- Java
- Java Swing
- JDBC
- Apache Ant e NetBeans

## Como executar

1. Instale um JDK e configure o projeto no NetBeans.
2. Prepare um banco de dados compatível com as consultas presentes em `RankingDao`.
3. Confira as configurações locais em `ConfiguracaoGlobal`.
4. Adicione o driver JDBC necessário ao projeto.
5. Execute `app.Main`.

> Não publique senhas reais nas configurações do banco de dados.

## Próximas melhorias

- Adicionar testes automatizados para as regras do tabuleiro
- Incluir um script documentado para criação das tabelas
- Criar uma configuração externa para o banco de dados
- Adicionar imagens da interface ao README

