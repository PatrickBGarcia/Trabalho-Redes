import com.google.gson.Gson;
import entities.DAO.PersonagemDAO;
import entities.MysqlConnection;
import itens.Item;
import itens.combate.*;
import itens.consumivel.Pot;
import personagem.Personagem;
import requests.Requisicao;
import responses.AdditionalResponse;
import responses.Response;
import responses.ResponseTypes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;

public class ProcessarRequisicao implements Runnable{
    private Socket cliente;
    MysqlConnection mysqlConnection = new MysqlConnection();

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
            AdditionalResponse adResponse = new AdditionalResponse();
            PersonagemDAO personagemDAO = null;
            String resposta;

            try {
                switch (requisicao.acao) {
                    case "registrar":
                        if (!requisicao.argumentos[1].equals(requisicao.argumentos[2])) {
                            resposta = response.createResponse(ResponseTypes.SENHAS_DIFERENTES);
                        } else {
                            mysqlConnection.openConnection();
                            personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                            if(personagemDAO.usuarioExiste(requisicao.argumentos[0])){
                                resposta = response.createResponse(ResponseTypes.USUARIO_JA_EXISTE);
                            }else{
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                personagem = new Personagem(requisicao.argumentos[0],requisicao.argumentos[1]);
                                personagemDAO.create(personagem);
                                adResponse.personagem = personagem;
                                adResponse.mensagemAdicional = "Parabens pelo registro " + requisicao.argumentos[0] + "! Aproveite!!\n";
                                resposta = new Gson().toJson(adResponse) + "\n";
                            }
                            mysqlConnection.closeConnection();
                        }
                        outToClient.writeBytes(resposta);
                        break;

                    case "login":
                        mysqlConnection.openConnection();
                        personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                        if(personagemDAO.usuarioExiste(requisicao.argumentos[0])){
                            personagem = personagemDAO.queryForEq("nome", requisicao.argumentos[0]).get(0);
                            if(!(personagem.getSenha().equals(requisicao.argumentos[1]))){
                                resposta = response.createResponse(ResponseTypes.SENHA_INCORRETA);
                            }else{
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                adResponse.personagem = personagem;
                                adResponse.mensagemAdicional = "Logado como: " + personagem.getNome() + "\n";
                                resposta = new Gson().toJson(adResponse) + "\n";
                            }
                        }else{
                            resposta = response.createResponse(ResponseTypes.USUARIO_NAO_EXISTE);
                        }
                        mysqlConnection.closeConnection();
                        outToClient.writeBytes(resposta);
                        break;
                    case "logoff":
                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                        outToClient.writeBytes(resposta);
                        adResponse.personagem = personagem;
                        adResponse.mensagemAdicional = "Conexao encerrada, ate mais!\n";
                        resposta = new Gson().toJson(adResponse) + "\n";
                        outToClient.writeBytes(resposta);
                        this.cliente.close();
                        this.cliente = null;
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
                        personagem.aumentaExp(20);
                        //ganhaLoot
                        //}else{
                        //personagem.morrer();
                        resposta = new Gson().toJson(personagem) + "\n";
                        //}
                        outToClient.writeBytes(resposta);
                        break;
                    case "ver":
                        if(!"inventario".equals(requisicao.argumentos[0]) &&
                           !"equipamentos".equals(requisicao.argumentos[0]) &&
                           !"vida".equals(requisicao.argumentos[0]) &&
                           !"status".equals(requisicao.argumentos[0])){
                            resposta = response.createResponse(ResponseTypes.VER_INVALIDO);
                        } else {
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);

                            adResponse.personagem = personagem;
                            if ("inventario".equals(requisicao.argumentos[0])) {
                                if(personagem.inventario.size() > 0){
                                    adResponse.mensagemAdicional = personagem.getNome() + " você possui os seguintes itens:\n";
                                    for(Item item: personagem.inventario){
                                        adResponse.mensagemAdicional += item.getNome() + "\n";
                                    }
                                }else{
                                    adResponse.mensagemAdicional = personagem.getNome() + ", infelizmente você não possui nenhum item :(\n";
                                }
                            } else if ("equipamentos".equals(requisicao.argumentos[0])) {
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Cabeca: " + personagem.getCapacete().getNome() +
                                            " - " + personagem.getCapacete().getDano() + "/" + personagem.getCapacete().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Cabeca: Nenhum\n";
                                }
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Armadura: " + personagem.getArmadura().getNome() +
                                            " - " + personagem.getArmadura().getDano() + "/" + personagem.getArmadura().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Armadura: Nenhum\n";
                                }
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Espada: " + personagem.getEspada().getNome() +
                                            " - " + personagem.getEspada().getDano() + "/" + personagem.getEspada().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Espada: Nenhum\n";
                                }
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Escudo: " + personagem.getEscudo().getNome() +
                                            " - " + personagem.getEscudo().getDano() + "/" + personagem.getEscudo().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Escudo: Nenhum\n";
                                }
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Perneira: " + personagem.getPerneira().getNome() +
                                            " - " + personagem.getPerneira().getDano() + "/" + personagem.getPerneira().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Perneira: Nenhum\n";
                                }
                                if(personagem.getCapacete() != null) {
                                    adResponse.mensagemAdicional += "Calcado: " + personagem.getCalcado().getNome() +
                                            " - " + personagem.getCalcado().getDano() + "/" + personagem.getCalcado().getDefesa() + "\n";
                                }else{
                                    adResponse.mensagemAdicional += "Calcado: Nenhum\n";
                                }
                            } else if ("vida".equals(requisicao.argumentos[0])) {
                                adResponse.mensagemAdicional = "Voce possui " + personagem.getVidaAtual() + "/" + personagem.getVidaMax() + " de vida.\n";
                            } else {
                                adResponse.mensagemAdicional = "Você possui:\n";
                                adResponse.mensagemAdicional += "Nivel: " + personagem.getNivel() + "\n";
                                adResponse.mensagemAdicional += personagem.getForca() + " de forca\n";
                                adResponse.mensagemAdicional += personagem.getDano() + " de dano\n";
                                adResponse.mensagemAdicional += personagem.getDefesa() + " de defesa\n";
                                adResponse.mensagemAdicional += personagem.getExp() + "/" + personagem.getExpProxLevel() + " de exp\n";
                                adResponse.mensagemAdicional += personagem.getOuro() + " de ouro\n";
                            }
                            resposta = new Gson().toJson(adResponse) + "\n";
                        }
                        outToClient.writeBytes(resposta);
                        break;
                    case "usar":
                        if(personagem.inventario.size() == 0) {
                            resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);
                        }else{
                            boolean possuiItem = false;
                            int i;
                            for (i=0; i < personagem.inventario.size(); i++){
                                if(personagem.inventario.get(i).getNome().equals(requisicao.argumentos[0])){
                                    possuiItem = true;
                                    break;
                                }
                            }
                            if(!possuiItem){
                                resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);
                            }else{
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                Item item = personagem.inventario.get(i);
                                personagem.inventario.remove(i);

                                if(item.categoria.equals(Item.Categoria.EQUIPAMENTO)){
                                    adResponse.mensagemAdicional = "Colocando " + item.getNome() + "\n";
                                    if(item.getClass().equals(Capacete.class)){
                                        if(personagem.getCapacete() != null){
                                            personagem.inventario.add(personagem.getCapacete());
                                        }
                                        personagem.setCapacete((Capacete)item);
                                    }else if(item.getClass().equals(Armadura.class)){
                                        if(personagem.getArmadura() != null){
                                            personagem.inventario.add(personagem.getArmadura());
                                        }
                                        personagem.setArmadura((Armadura) item);
                                    }else if(item.getClass().equals(Espada.class)){
                                        if(personagem.getEspada() != null){
                                            personagem.inventario.add(personagem.getEspada());
                                        }
                                        personagem.setEspada((Espada) item);
                                    }else if(item.getClass().equals(Escudo.class)){
                                        if(personagem.getEscudo() != null){
                                            personagem.inventario.add(personagem.getEscudo());
                                        }
                                        personagem.setEscudo((Escudo) item);
                                    }else if(item.getClass().equals(Perneira.class)){
                                        if(personagem.getPerneira() != null){
                                            personagem.inventario.add(personagem.getPerneira());
                                        }
                                        personagem.setPerneira((Perneira) item);
                                    }else{
                                        if(personagem.getCalcado() != null){
                                            personagem.inventario.add(personagem.getCalcado());
                                        }
                                        personagem.setCalcado((Calcado) item);
                                    }
                                    personagem.recalculaDano();
                                    personagem.recalculaDefesa();
                                    adResponse.mensagemAdicional += "Agora voce possui " + personagem.getDano() +
                                            " de dano e " + personagem.getDefesa() + " de defesa\n";
                                }else{
                                    adResponse.mensagemAdicional = "Usando " + item.getNome() + "\n";
                                    personagem.usarPocao((Pot) item);
                                    adResponse.mensagemAdicional += "Agora voce possui " + personagem.getVidaAtual() + "/" + personagem.getVidaMax() +" de vida\n";
                                }
                                mysqlConnection.openConnection();
                                personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                                personagemDAO.update(personagem);
                                mysqlConnection.closeConnection();
                                adResponse.personagem = personagem;
                                resposta = new Gson().toJson(adResponse) + "\n";
                            }
                        }
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
            } catch (SQLException e) {
                System.out.println("Problemas com banco de dados");
            }
        }
    }
}
