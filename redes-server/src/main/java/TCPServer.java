import com.google.gson.Gson;
import requests.Requisicao;
import responses.Response;
import responses.ResponseTypes;

import java.io.*;
import java.net.*;

public class TCPServer {


	public static void main(String[] args) throws Exception{
        System.out.println("INICIANDO SERVIDOR");

		String clientSentence;
		int porta = 6666;

		ServerSocket welcomeSocket = new ServerSocket(porta);
        System.out.println("UTILIZANDO PORTA: " + porta);
		while(true){

			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			clientSentence = inFromClient.readLine();

            System.out.println("MSG RECEBIDA: " + clientSentence);

            Gson gson = new Gson();
            Requisicao requisicao = gson.fromJson(clientSentence, Requisicao.class);
            requisicao.acao = requisicao.acao.toLowerCase();

            Response response = new Response();
            switch(requisicao.acao){
                case "registrar":
                    break;
                case "login":
                    break;
                case "logoff":
                    break;
                case "mover":
                    break;
                case "atacar":
                    break;
                case "ver":
                    break;
                case "usar":
                    break;
                case "conversar":
                    break;
                case "sair":
                    break;
                case "vender":
                    break;
                case "comprar":
                    break;
                default:
                    String resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                    outToClient.writeBytes(resposta);
                    break;
            }
		}

	}
}