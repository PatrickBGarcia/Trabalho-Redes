import com.google.gson.Gson;
import itens.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import personagem.Personagem;
import requests.Requisicao;
import responses.AdditionalResponse;
import responses.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public Stage stage;

    private final String ip = "localhost";
    private final int porta = 666;

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
    public TextArea inventario;

    //HISTORICO
    @FXML
    public TextArea txtHistorico;

    //COMANDO
    @FXML
    public TextField txtComando;
    @FXML
    public Button btnEnviar;


    Socket clientSocket = null;


    public void initialize(URL location, ResourceBundle resources) {
        txtComando.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    btnEnviarComando();
                }
            }
        });

        txtHistorico.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                txtHistorico.setScrollTop(Double.MAX_VALUE);
            }
        });

        inventario.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                txtHistorico.setScrollTop(Double.MAX_VALUE);
            }
        });


        txtHistorico.appendText(
                "Bem vindx ao Anglo RPG!!\n" +
                "Para começar, registre-se ou faça login.\n" +
                "Utilize \\help caso precise de ajuda.\n" +
                "Bom jogo!\n");
    }


    public Response sendContent(String json){

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

        String retorno;
        try {
            retorno = inFromServer.readLine();
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA LER RETORNO\n");
            return null;
        }

        Response response = new Gson().fromJson(retorno, Response.class);
        if(response.codigo != 1){
            return response;
        }

        String respostaAdicional;
        try {
            respostaAdicional = inFromServer.readLine();
        } catch (IOException e) {
            this.txtHistorico.appendText("PROBLEMAS PARA LER ALTERAÇÕES NO PERSONAGEM\n");
            return response;
        }

        response.adicional = new Gson().fromJson(respostaAdicional, AdditionalResponse.class);
        return response;
    }

    @FXML
    private void btnEnviarComando() {
        String mensagem = this.txtComando.getText().toLowerCase();
        this.txtHistorico.appendText("> " + mensagem + "\n");
        this.txtComando.setText("");
        if("\\help".equals(mensagem)) {
            showHelp();
            return;

        }
        if(!(mensagem.contains(" "))){
            if(!("sair".equals(mensagem) || "logoff".equals(mensagem))){
                this.txtHistorico.appendText("Comando não reconhecido\nPara a lista completa de comandos, utilize \\help\n");
                return;
            }
        }

        String[] parsedText = mensagem.split(" ");

        if(this.clientSocket == null){
            if( !("registrar".equals(parsedText[0])) && !("login".equals(parsedText[0]))) {
                this.txtHistorico.appendText("Para começar, registre-se ou faça login\n");
                return;
            }
        }

        selecionarComandoEEnviar(parsedText);
    }

    private void selecionarComandoEEnviar(String[] parsedText) {
        Gson gson = new Gson();
        Requisicao requisicao = new Requisicao();
        Response response;

        switch(parsedText[0]){
            case "registrar":
                if (parsedText.length != 4){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 4\n" +
                            "-> registrar usuario senha senha\n");
                    return;
                }

                if(this.clientSocket != null){
                    try {
                        this.clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        this.clientSocket = new Socket(this.ip, this.porta);
                    } catch (IOException e) {
                        this.txtHistorico.appendText("Problemas para estabelecer conexão com " + this.ip + " porta " + this.porta + "\n");
                        return;
                    }
                }else {
                    try {
                        this.clientSocket = new Socket(this.ip, this.porta);
                    } catch (IOException e) {
                        this.txtHistorico.appendText("Problemas para estabelecer conexão com " + this.ip + " porta " + this.porta + "\n");
                        return;
                    }
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[3];
                requisicao.argumentos[0] = parsedText[1];
                requisicao.argumentos[1] = parsedText[2];
                requisicao.argumentos[2] = parsedText[3];

                break;
            case "login":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "-> login usuario senha\n");
                    return;
                }

                if(this.clientSocket != null){
                    try {
                        this.clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        this.clientSocket = new Socket(this.ip, this.porta);
                    } catch (IOException e) {
                        this.txtHistorico.appendText("Problemas para estabelecer conexão com " + this.ip + " porta " + this.porta + "\n");
                        return;
                    }
                }else {
                    try {
                        this.clientSocket = new Socket(this.ip, this.porta);
                    } catch (IOException e) {
                        this.txtHistorico.appendText("Problemas para estabelecer conexão com " + this.ip + " porta " + this.porta + "\n");
                        return;
                    }
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[2];
                requisicao.argumentos[0] = parsedText[1];
                requisicao.argumentos[1] = parsedText[2];

                break;
            case "logoff":
                if (parsedText.length != 1){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 1\n" +
                            "-> logoff\n");
                    return;
                }
                requisicao.acao = parsedText[0];

                response = sendContent(gson.toJson(requisicao));
                if(response == null){
                    return;
                }
                if(response.codigo != 1){
                    this.txtHistorico.appendText(response.descricao + "\n");
                    return;
                }

                this.txtHistorico.appendText(response.adicional.mensagemAdicional);
                limpaPersonagem();
                return;
            case "mover":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "-> mover norte/sul/leste/oeste/acima/abaixo\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[1];
                requisicao.argumentos[0] = parsedText[1];

                break;
            case "atacar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "-> atacar nome_oponente\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[1];
                requisicao.argumentos[0] = parsedText[1];

                break;
            case "ver":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "-> ver inventario/equipamentos/vida/status\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[1];
                requisicao.argumentos[0] = parsedText[1];

                break;
            case "usar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "-> usar nome_item\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[1];
                requisicao.argumentos[0] = parsedText[1];

                break;
            case "conversar":
                if (parsedText.length != 2){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 2\n" +
                            "-> conversar nome_npc\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[1];
                requisicao.argumentos[0] = parsedText[1];

                break;
            case "sair":
                if (parsedText.length != 1){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 1\n" +
                            "-> sair\n");
                    return;
                }

                requisicao.acao = parsedText[0];

                break;
            case "vender":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "-> vender nome_item quantidade\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[2];
                requisicao.argumentos[0] = parsedText[1];
                requisicao.argumentos[1] = parsedText[2];

                break;
            case "comprar":
                if (parsedText.length != 3){
                    this.txtHistorico.appendText("Quantidade de parâmetros inválida, devem ser 3\n" +
                            "-> comprar nome_item quantidade\n");
                    return;
                }

                requisicao.acao = parsedText[0];
                requisicao.argumentos = new String[2];
                requisicao.argumentos[0] = parsedText[1];
                requisicao.argumentos[1] = parsedText[2];

                break;
            default:
                this.txtHistorico.appendText("Comando não reconhecido\nPara a lista completa de comandos, utilize \\help\n");
                return;
        }
        response = sendContent(gson.toJson(requisicao));
        if(response == null){
            return;
        }
        if(response.codigo != 1){
            this.txtHistorico.appendText(response.descricao + "\n");
            return;
        }

        this.txtHistorico.appendText(response.adicional.mensagemAdicional);
        atualizaPersonagem(response.adicional.personagem);
    }

    private void showHelp(){
        this.txtHistorico.appendText("## HELP ##\n");
        this.txtHistorico.appendText("Anglo RPG é um text-based game, logo é necessário escrever a ação que você deseja realizar.\n");
        this.txtHistorico.appendText("Segue uma lista de possíveis ações e seus argumentos:\n");
        this.txtHistorico.appendText("-> registrar usuario senha senha\n");
        this.txtHistorico.appendText("-> login usuario senha\n");
        this.txtHistorico.appendText("-> logoff\n");
        this.txtHistorico.appendText("-> mover norte/sul/leste/oeste/acima/abaixo\n");
        this.txtHistorico.appendText("-> atacar nome_oponente\n");
        this.txtHistorico.appendText("-> ver inventario/equipamentos/vida/status\n");
        this.txtHistorico.appendText("-> usar nome_item\n");
        this.txtHistorico.appendText("-> conversar nome_npc\n");
        this.txtHistorico.appendText("-> sair\n");
        this.txtHistorico.appendText("-> vender nome_item quantidade\n");
        this.txtHistorico.appendText("-> comprar nome_item quantidade\n");
    }

    private void atualizaPersonagem(Personagem personagem){
        this.nomePersonagem.setText(personagem.nome);
        this.nivel.setText(personagem.nivel + "");
        this.exp.setText(personagem.exp + "/" + personagem.expProxLevel);
        this.ouro.setText(personagem.ouro + "");
        this.vida.setText(personagem.vidaAtual + "/" + personagem.vidaMax);
        this.forca.setText(personagem.forca + "");

        if (personagem.capacete != null) {
            this.capacete.setText(personagem.capacete.nome);
        }else{
            this.capacete.setText("");
        }

        if (personagem.armadura != null) {
            this.armadura.setText(personagem.armadura.nome);
        }else{
            this.armadura.setText("");
        }

        if (personagem.espada != null) {
            this.espada.setText(personagem.espada.nome);
        }else{
            this.espada.setText("");
        }

        if (personagem.escudo != null) {
            this.escudo.setText(personagem.escudo.nome);
        }else{
            this.escudo.setText("");
        }

        if (personagem.perneira != null) {
            this.perneiras.setText(personagem.perneira.nome);
        }else{
            this.perneiras.setText("");
        }

        if (personagem.calcado != null) {
            this.botas.setText(personagem.calcado.nome);
        }else{
            this.botas.setText("");
        }

        if(personagem.inventario.size() > 0){
            String itensInventario = "";
            for(Item item: personagem.inventario){
                itensInventario += item.nome + ", ";
            }
            this.inventario.setText(itensInventario.substring(0, itensInventario.length()-1));
        }else {
            this.inventario.setText("");
        }
    }

    private void limpaPersonagem(){
        this.nomePersonagem.setText("");
        this.nivel.setText("");
        this.exp.setText("");
        this.ouro.setText("");
        this.vida.setText("");
        this.forca.setText("");
        this.capacete.setText("");
        this.armadura.setText("");
        this.espada.setText("");
        this.escudo.setText("");
        this.perneiras.setText("");
        this.botas.setText("");
        this.inventario.setText("");
    }

}
