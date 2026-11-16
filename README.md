# Placar ao Vivo

Projeto da Avaliação Prática de Padrões de Projeto de Software.

- **Aluna:** Maria Isabel Klein Delavy
- **Matrícula:** 202410004686
- **Curso:** Análise e Desenvolvimento de Sistemas, IFSC Câmpus Gaspar
- **Professor:** Dr. Renato Simões Moreira

## Sobre o projeto

Simulador de uma partida de futebol. Os eventos do jogo (gol, cartão, fim de jogo) são publicados no RabbitMQ e consumidos por observadores (placar, estatísticas e notificações).

## Tecnologias

- Java 25 e Maven
- RabbitMQ (imagem `rabbitmq:management`) via Docker

## Padrões de projeto utilizados

| Padrão | Tipo | Classe |
|---|---|---|
| Singleton | Criacional | `RabbitConexao` |
| Builder | Criacional | `PartidaBuilder` |
| Facade | Estrutural | `MensageriaFacade` |
| Observer | Comportamental | `ObservadorEvento`, `Placar`, `Estatisticas`, `Notificacao` |

## Como executar

### 1. Pré-requisitos

- JDK 17 ou superior
- Docker Desktop instalado e aberto
- IntelliJ IDEA (ou outra IDE com suporte a Maven)

### 2. Subir o RabbitMQ

Na raiz do projeto, rode:

    docker compose up -d

O painel do RabbitMQ fica em http://localhost:15672 (usuário `guest`, senha `guest`).

### 3. Executar o projeto

Abra a classe `Main` e execute o método `main`. O programa faz pausas, e em cada uma é preciso apertar ENTER no console:

1. Conexão aberta: no painel, veja a exchange `partida.eventos` e a fila `fila.placar`.
2. Eventos publicados: a fila mostra 5 mensagens prontas (Ready = 5).
3. Consumo: os observadores (Notificação, Placar e Estatísticas) reagem a cada evento.
4. Encerramento da conexão.

### 4. Encerrar o RabbitMQ

    docker compose down

## Documentação

O documento sobre os padrões de projeto está na pasta [`doc/`](doc/).