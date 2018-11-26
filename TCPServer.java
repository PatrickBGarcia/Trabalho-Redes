import java.io.*;
import java.net.*;

public class TCPServer {

	public static void main(String[] args) throws Exception{

		String clientSentence;
		String capitalizedSentence;

		ServerSocket welcomeSocket = new ServerSocket(6666);
		while(true){

			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			clientSentence = inFromClient.readLine();
			capitalizedSentence = "resultado > " + clientSentence.toUpperCase() + '\n';

			outToClient.writeBytes(capitalizedSentence);
		}

	}
}