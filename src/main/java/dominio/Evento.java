package dominio;

// Representa algo que aconteceu no jogo: gol, cartão ou fim de jogo.
public class Evento {
    private final String tipo;     // "GOL", "CARTAO_AMARELO", "CARTAO_VERMELHO", "FIM_DE_JOGO"
    private final String time;
    private final String jogador;
    private final int minuto;

    public Evento(String tipo, String time, String jogador, int minuto) {
        this.tipo = tipo;
        this.time = time;
        this.jogador = jogador;
        this.minuto = minuto;
    }

    public String getTipo() { return tipo; }
    public String getTime() { return time; }
    public String getJogador() { return jogador; }
    public int getMinuto() { return minuto; }

    // Transforma o evento em texto simples para enviar pelo RabbitMQ: TIPO;time;jogador;minuto
    public String paraMensagem() {
        return tipo + ";" + time + ";" + jogador + ";" + minuto;
    }

    // Faz o caminho contrário: lê o texto recebido e recria o Evento.
    public static Evento deMensagem(String mensagem) {
        String[] partes = mensagem.split(";");
        return new Evento(partes[0], partes[1], partes[2], Integer.parseInt(partes[3]));
    }
}