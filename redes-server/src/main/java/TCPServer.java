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
            if("registrar".equals(requisicao.acao)){

            }else if("login".equals(requisicao.acao)){

            }else if("logoff".equals(requisicao.acao)){

            }else if("mover".equals(requisicao.acao)){

            }else if("atacar".equals(requisicao.acao)){

            }else if("ver".equals(requisicao.acao)){

            }else if("usar".equals(requisicao.acao)){

            }else if("conversar".equals(requisicao.acao)){

            }else if("sair".equals(requisicao.acao)){

            }else if("vender".equals(requisicao.acao)){

            }else if("comprar".equals(requisicao.acao)){

            }else{
                String resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                outToClient.writeBytes(resposta);
            }

		}

	}
}