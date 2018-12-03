import com.google.gson.Gson;
import personagem.Personagem;
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
            String resposta;
            switch(requisicao.acao){
                case "registrar":
                    resposta = response.createResponse(ResponseTypes.USUARIO_JA_EXISTE);
                    resposta = response.createResponse(ResponseTypes.SENHAS_DIFERENTES);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "login":
                    resposta = response.createResponse(ResponseTypes.USUARIO_NAO_EXISTE);
                    resposta = response.createResponse(ResponseTypes.SENHA_INCORRETA);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "logoff":
                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "mover":
                    resposta = response.createResponse(ResponseTypes.MOVER_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "atacar":
                    resposta = response.createResponse(ResponseTypes.ATACAR_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "ver":
                    resposta = response.createResponse(ResponseTypes.VER_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "usar":
                    resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "conversar":
                    resposta = response.createResponse(ResponseTypes.CONVERSAR_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "sair":
                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "vender":
                    resposta = response.createResponse(ResponseTypes.SEM_QUANTIDADE_PRA_VENDER);
                    resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                case "comprar":
                    resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                    resposta = response.createResponse(ResponseTypes.SEM_OURO_SUFICIENTE);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
                default:
                    resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                    outToClient.writeBytes(resposta);
                    resposta = new Gson().toJson(new Personagem("Matheus")) + "\n";
                    outToClient.writeBytes(resposta);
                    break;
            }
		}

	}
}