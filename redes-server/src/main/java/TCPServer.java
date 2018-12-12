import mapa.Sala;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception{
        int porta = 666;

        ServerSocket welcomeSocket = new ServerSocket(porta);

        System.out.println("Iniciando listener do servidor na porta " + porta);
        while(true) {
            Socket cliente = welcomeSocket.accept();

            ProcessarRequisicao processar = new ProcessarRequisicao(cliente);
            Thread thread = new Thread(processar);
            thread.start();
        }
	}
}