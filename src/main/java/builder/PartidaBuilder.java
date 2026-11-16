package builder;

import dominio.Partida;

// PADRÃO BUILDER: monta uma Partida passo a passo, sem um construtor gigante.
public class PartidaBuilder {
    private String timeCasa;
    private String timeVisitante;
    private String estadio = "Não informado";
    private String data = "Não informada";
    private String arbitro = "Não informado";

    public PartidaBuilder timeCasa(String timeCasa) {
        this.timeCasa = timeCasa;
        return this;   // retorna o próprio builder para encadear as chamadas
    }

    public PartidaBuilder timeVisitante(String timeVisitante) {
        this.timeVisitante = timeVisitante;
        return this;
    }

    public PartidaBuilder estadio(String estadio) {
        this.estadio = estadio;
        return this;
    }

    public PartidaBuilder data(String data) {
        this.data = data;
        return this;
    }

    public PartidaBuilder arbitro(String arbitro) {
        this.arbitro = arbitro;
        return this;
    }

    // Cria a Partida final, validando o que é obrigatório.
    public Partida build() {
        if (timeCasa == null || timeVisitante == null) {
            throw new IllegalStateException("A partida precisa ter os dois times.");
        }
        return new Partida(timeCasa, timeVisitante, estadio, data, arbitro);
    }
}