package observer;

import dominio.Evento;
import java.util.HashMap;
import java.util.Map;

// Observador que conta gols e cartões de cada jogador.
public class Estatisticas implements ObservadorEvento {
    private final Map<String, Integer> gols = new HashMap<>();
    private final Map<String, Integer> cartoes = new HashMap<>();

    @Override
    public void atualizar(Evento evento) {
        String jogador = evento.getJogador();
        if (evento.getTipo().equals("GOL")) {
            gols.merge(jogador, 1, Integer::sum);
            System.out.println("[Estatísticas] " + jogador + ": " + gols.get(jogador) + " gol(s)");
        } else if (evento.getTipo().startsWith("CARTAO")) {
            cartoes.merge(jogador, 1, Integer::sum);
            System.out.println("[Estatísticas] " + jogador + ": " + cartoes.get(jogador) + " cartão(ões)");
        }
    }
}