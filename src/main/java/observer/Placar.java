package observer;

import dominio.Evento;

// Observador que acompanha o resultado da partida.
public class Placar implements ObservadorEvento {
    private final String timeCasa;
    private final String timeVisitante;
    private int golsCasa = 0;
    private int golsVisitante = 0;

    public Placar(String timeCasa, String timeVisitante) {
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
    }

    @Override
    public void atualizar(Evento evento) {
        if (evento.getTipo().equals("GOL")) {
            if (evento.getTime().equals(timeCasa)) {
                golsCasa++;
            } else {
                golsVisitante++;
            }
        }
        if (evento.getTipo().equals("GOL") || evento.getTipo().equals("FIM_DE_JOGO")) {
            String prefixo = evento.getTipo().equals("FIM_DE_JOGO") ? "Fim de jogo: " : "";
            System.out.println("[Placar] " + prefixo + timeCasa + " " + golsCasa + " x " + golsVisitante + " " + timeVisitante);
        }
    }
}