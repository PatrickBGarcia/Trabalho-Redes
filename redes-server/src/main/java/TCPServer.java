import com.j256.ormlite.logger.LocalLog;
import mapa.Mapa;
import mapa.Sala;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception{
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
	    Sala salaInicial = Mapa.iniciar();

	    if(salaInicial == null){
            System.out.println("Problemas para criar o mapa, servidor não iniciará");
            return;
        }

        int porta = 666;
        ServerSocket welcomeSocket = new ServerSocket(porta);

        System.out.println("Iniciado listener do servidor na porta " + porta);
        while(true) {
            System.out.println("Aguardando conexões");
            Socket cliente = welcomeSocket.accept();

            ProcessarRequisicao processar = new ProcessarRequisicao(cliente, salaInicial);
            Thread thread = new Thread(processar);
            thread.start();
        }
	}
}