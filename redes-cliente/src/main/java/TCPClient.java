import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) throws Exception{

		String sentence;
		String modifiedSentence;
		//cria stream de entrada
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		//cria socket cliente, conecta ao servidor
		Socket clientSocket = new Socket("localhost", 6666);
		//cria stream de saida, ligada ao socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		//cria stream de entrada ligada ao socket
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		sentence = inFromUser.readLine();
		//envia linha para o servidor
		outToServer.writeBytes(sentence+'\n');
		modifiedSentence = inFromServer.readLine();
		//lê linha do servidor
		System.out.println("From server: "+modifiedSentence);		
		clientSocket.close();
	}

}