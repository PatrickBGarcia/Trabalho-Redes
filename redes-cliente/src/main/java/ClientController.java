import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private final String ip = "localhost";
    private final int porta = 6666;

    public Stage stage;

    //PERSONAGEM
    @FXML
    public Label nomePersonagem;
    @FXML
    public Label nivel;
    @FXML
    public Label exp;
    @FXML
    public Label ouro;
    @FXML
    public Label vida;
    @FXML
    public Label forca;

    //SET
    @FXML
    public Label capacete;
    @FXML
    public Label armadura;
    @FXML
    public Label espada;
    @FXML
    public Label escudo;
    @FXML
    public Label perneiras;
    @FXML
    public Label botas;

    //INVENTARIO
    @FXML
    public Label inventario;

    //HISTORICO
    @FXML
    public TextArea txtHistorico;

    //COMANDO
    @FXML
    public TextField txtComando;
    @FXML
    public Button btnEnviar;


    public void initialize(URL location, ResourceBundle resources) {}


    public String sendContent(String json){
        Socket clientSocket;
        try {
            clientSocket = new Socket(this.ip, this.porta);
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS DE COMUNICAÇÃO COM " + this.ip + " porta " + this.porta + "\n");
            return null;
        }

        DataOutputStream outToServer;
        try {
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA VINCULAR O OUTPUT STREAM\n");
            return null;
        }

        BufferedReader inFromServer;
        try {
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA VINCULAR O INPUT STREAM\n");
            return null;
        }

        try {
            outToServer.writeBytes(json+"\n");
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA ENVIAR CONTEUDO\n");
            return null;
        }

        try {
            return inFromServer.readLine();
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA LER RETORNO\n");
            return null;
        }
    }

    @FXML
    private void btnEnviarComando(ActionEvent event) {
        String mensagem = this.txtComando.getText().toLowerCase();
        if("\\help".equals(mensagem)) {
            showHelp();
            return;

        }
        if(!(mensagem.contains(" "))){
            if(!("sair".equals(mensagem) || "logoff".equals(mensagem))){
                this.txtHistorico.appendText("Comando não reconhecido\n");
                return;
            }
        }

        String[] parsedText = mensagem.split(" ");
        try {
            selecionarComandoEEnviar(parsedText);
        } catch (IOException e) {
            this.txtHistorico.appendText("Erro ao enviar comando\n");
        }
    }

    private void selecionarComandoEEnviar(String[] parsedText) throws IOException {
        switch(parsedText[0]){
            case "registrar":
                if (parsedText.length != 4){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 4\n" +
                            "> registrar usuario senha senha\n");
                    return;
                }
                break;
            case "login":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "> login usuario senha\n");
                    return;
                }
                break;
            case "logoff":
                if (parsedText.length != 1){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 1\n" +
                            "> logoff\n");
                    return;
                }
                break;
            case "mover":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "> mover norte/sul/leste/oeste/acima/abaixo\n");
                    return;
                }
                break;
            case "atacar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "> atacar nome_oponente\n");
                    return;
                }
                break;
            case "ver":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "> ver inventario/equipamentos/vida/status\n");
                    return;
                }
                break;
            case "usar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "> usar nome_item\n");
                    return;
                }
                break;
            case "conversar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "> conversar nome_npc\n");
                    return;
                }
                break;
            case "sair":
                if (parsedText.length != 1){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 1\n" +
                            "> sair\n");
                    return;
                }
                break;
            case "vender":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "> vender nome_item quantidade\n");
                    return;
                }
                break;
            case "comprar":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "> comprar nome_item quantidade\n");
                    return;
                }
                break;
            default:
                String json = "{\"acao\":\"registraarr\",\"argumentos\":[\"matheus\",\"1234\",\"1234\"]}";
                String retorno = sendContent(json);
                this.txtHistorico.appendText(retorno + "\n");
                //this.txtHistorico.appendText("Comando não reconhecido\n");
                break;
        }
    }

    private void showHelp(){

    }

}
