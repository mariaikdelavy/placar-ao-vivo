package observer;

import dominio.Evento;

// Observador que "avisa o torcedor" sempre que algo acontece.
public class Notificacao implements ObservadorEvento {
    @Override
    public void atualizar(Evento evento) {
        switch (evento.getTipo()) {
            case "GOL" -> System.out.println("[Notificação] GOL! " + evento.getJogador()
                    + " (" + evento.getTime() + ") aos " + evento.getMinuto() + "'");
            case "CARTAO_AMARELO" -> System.out.println("[Notificação] Cartão amarelo para "
                    + evento.getJogador() + " (" + evento.getTime() + ") aos " + evento.getMinuto() + "'");
            case "CARTAO_VERMELHO" -> System.out.println("[Notificação] Cartão vermelho para "
                    + evento.getJogador() + " (" + evento.getTime() + ") aos " + evento.getMinuto() + "'");
            case "FIM_DE_JOGO" -> System.out.println("[Notificação] Apita o árbitro: fim de jogo!");
        }
    }
}