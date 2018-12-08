import com.google.gson.Gson;
import personagem.Personagem;
import requests.Requisicao;
import responses.Response;
import responses.ResponseTypes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ProcessarRequisicao implements Runnable{
    private Socket cliente;

    public ProcessarRequisicao(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        Personagem personagem = null;
        while(this.cliente != null) {
            BufferedReader inFromClient;
            try {
                inFromClient = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            } catch (IOException e) {
                System.out.println("Problemas para abrir buffered reader");
                return;
            }

            DataOutputStream outToClient;
            try {
                outToClient = new DataOutputStream(cliente.getOutputStream());
            } catch (IOException e) {
                System.out.println("Problemas para abrir output stream");
                return;
            }

            String clientSentence;
            try {
                clientSentence = inFromClient.readLine();
            } catch (IOException e) {
                System.out.println("Problemas para ler entrada");
                return;
            }

            System.out.println("Json recebido: " + clientSentence);

            Gson gson = new Gson();
            Requisicao requisicao = gson.fromJson(clientSentence, Requisicao.class);
            requisicao.acao = requisicao.acao.toLowerCase();

            Response response = new Response();
            String resposta;

            try {
                switch (requisicao.acao) {
                    case "registrar":
                        if (!requisicao.argumentos[1].equals(requisicao.argumentos[2])) {
                            resposta = response.createResponse(ResponseTypes.SENHAS_DIFERENTES);
                        } else {
                            //if(seUsuarioExiste){
                            resposta = response.createResponse(ResponseTypes.USUARIO_JA_EXISTE);
                            //}else{
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);
                            personagem = new Personagem(requisicao.argumentos[0]);
                            resposta = new Gson().toJson(personagem) + "\n";
                            //}
                        }
                        outToClient.writeBytes(resposta);
                        break;

                    case "login":
                        //if(usuarioExiste){
                        //  if(senhaIncorreta){
                        //      resposta = response.createResponse(ResponseTypes.SENHA_INCORRETA);
                        //  }else{
                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                        outToClient.writeBytes(resposta);
                        personagem = new Personagem(requisicao.argumentos[0]);//CARREGA DO BANCO
                        resposta = new Gson().toJson(personagem) + "\n";
                        // }else{
                        //  resposta = response.createResponse(ResponseTypes.USUARIO_NAO_EXISTE);
                        //}

                        outToClient.writeBytes(resposta);
                        break;
                    case "logoff":
                        this.cliente.close();
                        break;
                    case "mover":
                        //if(NaoExisteEssaDirecaoNesseMapa){
                        //resposta = response.createResponse(ResponseTypes.MOVER_INVALIDO);
                        //}else{
                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                        outToClient.writeBytes(resposta);
                        //movePersonagem
                        resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "atacar":
                        //if(!monstroExisteNaSala){
                        //resposta = response.createResponse(ResponseTypes.ATACAR_INVALIDO);

                        //}else{
                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                        outToClient.writeBytes(resposta);
                        //batalha
                        //if(ganhou){
                        //personagem.aumentaExp(expMonstro);
                        //ganhaLoot
                        //}else{
                        //personagem.morrer();
                        resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "ver":
                        //if(!existeOQueEleQuerVer){
                          resposta = response.createResponse(ResponseTypes.VER_INVALIDO);

                        //}else {
                        //    resposta = response.createResponse(ResponseTypes.SUCESSO);
                        //    outToClient.writeBytes(resposta);
                        //    resposta = oQueEleQuerVer;
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "usar":
                        //if(!personagemTemOItem(requisicao.argumentos[0])){
                        //    resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);

                        //}else {
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);
                            //usaItem
                            resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "conversar":
                        //if(NaoExisteNPC){
                        //    resposta = response.createResponse(ResponseTypes.CONVERSAR_INVALIDO);
                        //}else{
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);
                            resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "sair":
                        //if(NaoEstavaEmConversa){
                        //    resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                        //}else {
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);
                            resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "vender":
                        //if(NaoExisteItemInformado){
                        //    resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                        //}else{
                        //    if(naoTemAQuantidadeInformada){
                        //        resposta = response.createResponse(ResponseTypes.SEM_QUANTIDADE_PRA_VENDER);
                        //    }else{
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                resposta = new Gson().toJson(personagem) + "\n";
                        //    }
                        //}

                        outToClient.writeBytes(resposta);
                        break;
                    case "comprar":
                        //if(NaoExisteItemInformado){
                        //    resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                        //}else{
                        //    if(naoPossuiOuroSuficiente){
                        //        resposta = response.createResponse(ResponseTypes.SEM_OURO_SUFICIENTE);
                        //    }else{
                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                        outToClient.writeBytes(resposta);
                        resposta = new Gson().toJson(personagem) + "\n";
                        //    }
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    default:
                        resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                        outToClient.writeBytes(resposta);
                        resposta = new Gson().toJson(personagem) + "\n";
                        outToClient.writeBytes(resposta);
                        break;
                }
            } catch (IOException ex) {
                resposta = response.createResponse(ResponseTypes.ERRO_INTERNO);
                try {
                    outToClient.writeBytes(resposta);
                } catch (IOException e) {
                    System.out.println("Problemas para informar erro interno");
                    return;
                }
            }
        }
    }
}
