import builder.PartidaBuilder;
import dominio.Partida;
import facade.MensageriaFacade;
import observer.Estatisticas;
import observer.Notificacao;
import observer.Placar;
import singleton.RabbitConexao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // 1. BUILDER
        Partida partida = new PartidaBuilder()
                .timeCasa("Brasil")
                .timeVisitante("Argentina")
                .estadio("Maracanã")
                .data("01/12/2026")
                .arbitro("Wilton Sampaio")
                .build();
        System.out.println("Partida: " + partida);

        // 2. FACADE (usa o SINGLETON por dentro) - aqui a conexão abre
        MensageriaFacade mensageria = new MensageriaFacade();

        // 3. OBSERVER
        mensageria.adicionarObservador(new Notificacao());
        mensageria.adicionarObservador(new Placar(partida.getTimeCasa(), partida.getTimeVisitante()));
        mensageria.adicionarObservador(new Estatisticas());

        System.out.println("\nConexão aberta. Veja no painel: Connections, Exchanges e Queues.");
        System.out.println("Aperte ENTER para publicar os eventos...");
        teclado.nextLine();

        // 4. Publica os eventos (ainda SEM ninguém consumindo)
        mensageria.publicarGol("Brasil", "Vinícius Jr", 23);
        mensageria.publicarCartaoAmarelo("Argentina", "Otamendi", 35);
        mensageria.publicarGol("Argentina", "Julián Álvarez", 58);
        mensageria.publicarGol("Brasil", "Rodrygo", 77);
        mensageria.publicarFimDeJogo(90);

        System.out.println("\n5 mensagens publicadas. Veja a fila 'fila.placar' no painel (Ready = 5).");
        System.out.println("Aperte ENTER para os observadores consumirem...\n");
        teclado.nextLine();

        // 5. Agora sim os observadores consomem
        mensageria.iniciarConsumo();
        try { Thread.sleep(2000); } catch (InterruptedException e) { }

        System.out.println("\nAperte ENTER para encerrar a conexão...");
        teclado.nextLine();
        RabbitConexao.getInstancia().fechar();
    }
}