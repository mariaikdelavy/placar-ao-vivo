package singleton;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// PADRÃO SINGLETON: garante que existe UMA ÚNICA conexão com o RabbitMQ
// em todo o programa, não importa quantas vezes alguém peça.
public class RabbitConexao {

    private static RabbitConexao instancia;   // a única instância
    private final Connection conexao;
    private final Channel canal;

    // Construtor privado: ninguém de fora consegue fazer "new RabbitConexao()"
    private RabbitConexao() {
        try {
            ConnectionFactory fabrica = new ConnectionFactory();
            fabrica.setHost("localhost");
            fabrica.setUsername("guest");
            fabrica.setPassword("guest");
            this.conexao = fabrica.newConnection();
            this.canal = conexao.createChannel();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível conectar ao RabbitMQ. O Docker está rodando?", e);
        }
    }

    // Único jeito de obter a conexão: cria na primeira vez, reaproveita nas próximas.
    public static synchronized RabbitConexao getInstancia() {
        if (instancia == null) {
            instancia = new RabbitConexao();
        }
        return instancia;
    }

    public Channel getCanal() {
        return canal;
    }

    public void fechar() {
        try {
            canal.close();
            conexao.close();
        } catch (Exception e) {
            // ignora erro ao fechar
        }
    }
}