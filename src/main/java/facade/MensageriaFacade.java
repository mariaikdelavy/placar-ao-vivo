package facade;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import dominio.Evento;
import observer.ObservadorEvento;
import singleton.RabbitConexao;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// PADRÃO FACADE: esconde a complexidade do RabbitMQ (exchange, fila, binding,
// publicar, consumir) atrás de poucos métodos simples.
public class MensageriaFacade {
    private static final String EXCHANGE = "partida.eventos";
    private static final String FILA = "fila.placar";

    private final Channel canal;
    private final List<ObservadorEvento> observadores = new ArrayList<>();

    public MensageriaFacade() {
        try {
            // Usa a conexão ÚNICA do Singleton
            this.canal = RabbitConexao.getInstancia().getCanal();
            canal.exchangeDeclare(EXCHANGE, "topic", true);
            canal.queueDeclare(FILA, true, false, false, null);
            canal.queueBind(FILA, EXCHANGE, "partida.#");
            canal.queuePurge(FILA);   // limpa mensagens antigas de execuções anteriores
        } catch (Exception e) {
            throw new RuntimeException("Erro ao configurar o RabbitMQ", e);
        }
    }

    // Quem quiser ouvir os eventos se inscreve aqui (Observer).
    public void adicionarObservador(ObservadorEvento observador) {
        observadores.add(observador);
    }

    // Começa a receber mensagens do RabbitMQ e avisa todos os observadores.
    public void iniciarConsumo() {
        try {
            DeliverCallback aoReceber = (tag, entrega) -> {
                String texto = new String(entrega.getBody(), StandardCharsets.UTF_8);
                Evento evento = Evento.deMensagem(texto);
                for (ObservadorEvento o : observadores) {
                    o.atualizar(evento);
                }
            };
            canal.basicConsume(FILA, true, aoReceber, tag -> { });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consumir mensagens", e);
        }
    }

    // Métodos simples que a Main usa, sem saber nada de RabbitMQ.
    public void publicarGol(String time, String jogador, int minuto) {
        publicar("partida.gol", new Evento("GOL", time, jogador, minuto));
    }

    public void publicarCartaoAmarelo(String time, String jogador, int minuto) {
        publicar("partida.cartao.amarelo", new Evento("CARTAO_AMARELO", time, jogador, minuto));
    }

    public void publicarCartaoVermelho(String time, String jogador, int minuto) {
        publicar("partida.cartao.vermelho", new Evento("CARTAO_VERMELHO", time, jogador, minuto));
    }

    public void publicarFimDeJogo(int minuto) {
        publicar("partida.fim", new Evento("FIM_DE_JOGO", "-", "-", minuto));
    }

    private void publicar(String chave, Evento evento) {
        try {
            canal.basicPublish(EXCHANGE, chave, null, evento.paraMensagem().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar evento", e);
        }
    }
}