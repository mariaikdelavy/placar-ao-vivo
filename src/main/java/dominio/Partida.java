package dominio;

// Representa uma partida de futebol. Os campos são montados pelo Builder.
public class Partida {
    private final String timeCasa;
    private final String timeVisitante;
    private final String estadio;
    private final String data;
    private final String arbitro;

    public Partida(String timeCasa, String timeVisitante, String estadio, String data, String arbitro) {
        this.timeCasa = timeCasa;
        this.timeVisitante = timeVisitante;
        this.estadio = estadio;
        this.data = data;
        this.arbitro = arbitro;
    }

    public String getTimeCasa() { return timeCasa; }
    public String getTimeVisitante() { return timeVisitante; }
    public String getEstadio() { return estadio; }
    public String getData() { return data; }
    public String getArbitro() { return arbitro; }

    @Override
    public String toString() {
        return timeCasa + " x " + timeVisitante + " - " + estadio + " (" + data + ", árbitro: " + arbitro + ")";
    }
}