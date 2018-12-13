import com.google.gson.Gson;
import entities.DAO.ItemDAO;
import entities.DAO.PersonagemDAO;
import entities.MysqlConnection;
import inimigos.Boss;
import inimigos.Monstro;
import itens.Item;
import itens.combate.*;
import itens.consumivel.Pot;
import mapa.Sala;
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
import java.util.Iterator;
import java.util.List;

public class ProcessarRequisicao implements Runnable{
    private Socket cliente;
    private Sala salaAtual;
    MysqlConnection mysqlConnection = new MysqlConnection();

    public ProcessarRequisicao(Socket cliente, Sala salaAtual) {
        this.cliente = cliente;
        this.salaAtual = salaAtual;
    }

    @Override
    public void run() {
        Personagem personagem = null;
        boolean falandoNPC = false;
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
            ItemDAO itemDAO = null;
            String resposta;


            try {
                if(falandoNPC){
                    switch (requisicao.acao) {
                        case "sair":
                            falandoNPC = false;
                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                            outToClient.writeBytes(resposta);
                            adResponse.personagem = personagem;
                            adResponse.mensagemAdicional = "Volte sempre!\n";
                            resposta = new Gson().toJson(adResponse) + "\n";
                            outToClient.writeBytes(resposta);
                            break;
                        case "vender":
                            if(personagem.getInventario().size()==0){
                                resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                            }else{
                                if(Integer.parseInt(requisicao.argumentos[1])>1){
                                    resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                                }
                                else {
                                    int contItens = 0;
                                    for (Item itensInventario : personagem.getInventario()) {
                                        if (requisicao.argumentos[0].equals(itensInventario.getNome())) {
                                            contItens++;
                                        }
                                    }
                                    if (contItens < Integer.parseInt(requisicao.argumentos[1])) {
                                        resposta = response.createResponse(ResponseTypes.SEM_QUANTIDADE_PRA_VENDER);
                                    } else {
                                        mysqlConnection.openConnection();
                                        for (Item itensInventario : personagem.getInventario()) {
                                            if (contItens > 0) {
                                                if (requisicao.argumentos[0].equals(itensInventario.getNome())) {
                                                    personagem.inventario.remove(itensInventario);
                                                    personagem.setOuro(personagem.getOuro() + itensInventario.valor);

                                                    List<Item> itemBanco = itemDAO.queryForEq("id",itensInventario.getId());
                                                    itemDAO.delete(itemBanco);
                                                    break;
                                                }
                                            } else break;
                                        }
                                        personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                                        personagemDAO.update(personagem);

                                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                                        outToClient.writeBytes(resposta);

                                        mysqlConnection.closeConnection();
                                        adResponse.personagem = personagem;
                                        adResponse.mensagemAdicional = "Item vendido!";
                                        resposta = new Gson().toJson(adResponse) + "\n";
                                    }
                                }
                            }
                            outToClient.writeBytes(resposta);
                            break;
                        case "comprar":
                            mysqlConnection.openConnection();
                            if(Integer.parseInt(requisicao.argumentos[1])>1){
                                resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                            }
                            else {
                                if (salaAtual.getNpc().getItensAVenda().size() == 0) {
                                    resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                                } else {
                                    for (Item itensVenda : salaAtual.npc.getItensAVenda()) {
                                        if (itensVenda.getNome().equals(requisicao.argumentos[0])) {
                                            if (personagem.getOuro() < itensVenda.getValor()) {
                                                resposta = response.createResponse(ResponseTypes.SEM_OURO_SUFICIENTE);
                                                break;
                                            } else {
                                                personagem.inventario.add(itensVenda);
                                                personagem.setOuro(personagem.getOuro() - itensVenda.valor);
                                                break;
                                            }
                                        } else {
                                            resposta = response.createResponse(ResponseTypes.COMPRA_VENDA_ITEM_NAO_ENCONTRADO);
                                        }
                                    }
                                    personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                                    personagemDAO.update(personagem);
                                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                                    outToClient.writeBytes(resposta);
                                    adResponse.personagem = personagem;
                                    adResponse.mensagemAdicional = "Item comprado!";
                                    resposta = new Gson().toJson(adResponse) + "\n";
                                    mysqlConnection.closeConnection();
                                }
                            }
                            outToClient.writeBytes(resposta);
                            break;
                        default:
                            resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                            outToClient.writeBytes(resposta);
                            break;
                    }
                }else {
                    switch (requisicao.acao) {
                        case "registrar":
                            if (!requisicao.argumentos[1].equals(requisicao.argumentos[2])) {
                                resposta = response.createResponse(ResponseTypes.SENHAS_DIFERENTES);
                            } else {
                                mysqlConnection.openConnection();
                                personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                                if (personagemDAO.usuarioExiste(requisicao.argumentos[0])) {
                                    resposta = response.createResponse(ResponseTypes.USUARIO_JA_EXISTE);
                                } else {
                                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                                    outToClient.writeBytes(resposta);
                                    personagem = new Personagem(requisicao.argumentos[0], requisicao.argumentos[1]);
                                    personagemDAO.create(personagem);
                                    adResponse.personagem = personagem;
                                    adResponse.mensagemAdicional = "Parabens pelo registro " + requisicao.argumentos[0] + "! Aproveite!!\n";

                                    adResponse.mensagemAdicional += "Voce esta em " + salaAtual.getNome() + "\n";
                                    adResponse.mensagemAdicional += "Ao redor existem as seguintes salas:\n" + salaAtual.verSalasAoRedor();

                                    if (salaAtual.monstros.size() > 0) {
                                        adResponse.mensagemAdicional += "Nesta sala existem os seguintes monstros:\n";
                                        for(Monstro monstro: salaAtual.monstros){
                                            adResponse.mensagemAdicional += monstro.getNome() + " - Level " + monstro.getNivel() + "\n";
                                        }
                                    }

                                    resposta = new Gson().toJson(adResponse) + "\n";
                                }
                                mysqlConnection.closeConnection();
                            }
                            outToClient.writeBytes(resposta);
                            break;

                        case "login":
                            mysqlConnection.openConnection();
                            personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                            if (personagemDAO.usuarioExiste(requisicao.argumentos[0])) {
                                personagem = personagemDAO.queryForEq("nome", requisicao.argumentos[0]).get(0);
                                if (!(personagem.getSenha().equals(requisicao.argumentos[1]))) {
                                    resposta = response.createResponse(ResponseTypes.SENHA_INCORRETA);
                                } else {
                                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                                    outToClient.writeBytes(resposta);
                                    adResponse.personagem = personagem;
                                    adResponse.mensagemAdicional = "Logado como: " + personagem.getNome() + "\n";
                                    adResponse.mensagemAdicional += "Voce esta em " + salaAtual.getNome() + "\n";
                                    adResponse.mensagemAdicional += "Ao redor existem as seguintes salas:\n" + salaAtual.verSalasAoRedor();

                                    if (salaAtual.monstros.size() > 0) {
                                        adResponse.mensagemAdicional += "Nesta sala existem os seguintes monstros:\n";
                                        for(Monstro monstro: salaAtual.monstros){
                                            adResponse.mensagemAdicional += monstro.getNome() + " - Level " + monstro.getNivel() + "\n";
                                        }
                                    }

                                    resposta = new Gson().toJson(adResponse) + "\n";
                                }
                            } else {
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
                            Sala novaSala = salaAtual.mover(requisicao.argumentos[0]);
                            if(novaSala == null){
                                resposta = response.createResponse(ResponseTypes.MOVER_INVALIDO);
                            }else {
                                salaAtual = novaSala;
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                adResponse.personagem = personagem;
                                adResponse.mensagemAdicional = "Voce esta em " + salaAtual.getNome() + "\n";
                                adResponse.mensagemAdicional += "Ao redor existem as seguintes salas:\n" + salaAtual.verSalasAoRedor();

                                if (salaAtual.monstros.size() > 0) {
                                    adResponse.mensagemAdicional += "Nesta sala existem os seguintes monstros:\n";
                                    for(Monstro monstro: salaAtual.monstros){
                                        adResponse.mensagemAdicional += monstro.getNome() + " - Level " + monstro.getNivel() + "\n";
                                    }
                                }else{
                                    adResponse.mensagemAdicional += "Aqui e a sala onde voce pode comprar e vender itens, mas primeiro use o comando 'conversar " +
                                            salaAtual.npc.getNome() + "'\n";
                                }
                                resposta = new Gson().toJson(adResponse) + "\n";
                            }
                            outToClient.writeBytes(resposta);
                            break;
                        case "atacar":
                            if(salaAtual.npc != null){
                                resposta = response.createResponse(ResponseTypes.ATACAR_INVALIDO);
                            }else{
                                boolean existeMonstro = false;
                                int index;
                                for(index = 0;  index < salaAtual.monstros.size(); index++){
                                    if(salaAtual.monstros.get(index).getNome().equals(requisicao.argumentos[0])){
                                        existeMonstro = true;
                                        break;
                                    }
                                }

                                if(!(existeMonstro)){
                                    resposta = response.createResponse(ResponseTypes.ATACAR_INVALIDO);
                                }else{
                                    if("pulla".equals(requisicao.argumentos[0])){
                                        Monstro pulla = salaAtual.getMonstros().get(index);
                                        if(pulla.getVidaAtual() <= 0){
                                            resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                                        }else{
                                            resposta = response.createResponse(ResponseTypes.SUCESSO);
                                            outToClient.writeBytes(resposta);
                                            adResponse.mensagemAdicional = "Atacando pulla\n";
                                            int danoCausado = personagem.getDano() - pulla.getDefesa();
                                            if (danoCausado > 0) {
                                                pulla.setVidaAtual(pulla.getVidaAtual() - danoCausado);
                                                adResponse.mensagemAdicional += "Voce atacou e infringiu " + danoCausado + " de dano ao pulla\n";
                                            } else {
                                                adResponse.mensagemAdicional += "Voce nao conseguiu causar dano ao pulla!\n";
                                            }

                                            if (pulla.getVidaAtual() > 0) {
                                                adResponse.mensagemAdicional += "pulla ainda possui " + pulla.getVidaAtual() + " de vida!\n";
                                            }else{
                                                adResponse.mensagemAdicional += "PARABENS, VOCE DERROTOU O PULLA";
                                                personagem.aumentaExp(5000);
                                                personagem.aumentarOuro(5000);
                                            }
                                            adResponse.personagem = personagem;
                                            resposta = new Gson().toJson(adResponse) + "\n";
                                        }
                                    }else {
                                        Monstro inimigo = salaAtual.monstros.get(index);
                                        inimigo.setVidaAtual(inimigo.getVidaMax());
                                        resposta = response.createResponse(ResponseTypes.SUCESSO);
                                        outToClient.writeBytes(resposta);

                                        adResponse.mensagemAdicional = "Comecando batalha...\n";
                                        while (inimigo.getVidaAtual() > 0 && personagem.getVidaAtual() > 0) {
                                            int danoCausado = personagem.getDano() - inimigo.getDefesa();
                                            if (danoCausado > 0) {
                                                inimigo.setVidaAtual(inimigo.getVidaAtual() - danoCausado);
                                                adResponse.mensagemAdicional += "Voce atacou e infringiu " + danoCausado + " de dano\n";
                                            } else {
                                                adResponse.mensagemAdicional += "Voce nao conseguiu causar dano ao inimigo!\n";
                                            }

                                            if (inimigo.getVidaAtual() > 0) {
                                                int danoRecebido = inimigo.getDano() - personagem.getDefesa();
                                                if (danoRecebido > 0) {
                                                    personagem.setVidaAtual(personagem.getVidaAtual() - danoRecebido);
                                                    adResponse.mensagemAdicional += "Voce sofreu um ataque e perdeu " + danoRecebido + " de vida\n";
                                                } else {
                                                    adResponse.mensagemAdicional += "Voce nao recebeu dano do inimigo!\n";
                                                }

                                                if (personagem.getVidaAtual() <= 0) {
                                                    adResponse.mensagemAdicional += "Oh nao, voce morreu!! Tera que comecar o jogo do zero...\n";
                                                    personagem.morrer();
                                                    break;
                                                }
                                            } else {
                                                adResponse.mensagemAdicional += inimigo.getNome() + " foi derrotado!\n";
                                                adResponse.mensagemAdicional += "Voce recebeu " + inimigo.getExpDada() + " de exp\n";
                                                personagem.aumentaExp(inimigo.getExpDada());
                                                adResponse.mensagemAdicional += "Voce ganhou " + inimigo.getOuroDado() + " pela vitoria\n";
                                                personagem.aumentarOuro(inimigo.getOuroDado());
                                                adResponse.mensagemAdicional += "Voce tambem recebeu o(s) seguinte(s) item(s):\n";
                                                List<Item> dropMonstro = inimigo.dropar();
                                                for (Item drop : dropMonstro) {
                                                    adResponse.mensagemAdicional += drop.getNome() + "\n";
                                                }
                                                personagem.inventario.addAll(dropMonstro);

                                                adResponse.mensagemAdicional += "Vida atual: " + personagem.getVidaAtual() + "/" + personagem.getVidaMax() + "\n";
                                            }
                                        }
                                        adResponse.personagem = personagem;
                                        mysqlConnection.openConnection();
                                        personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
                                        personagemDAO.update(personagem);
                                        mysqlConnection.closeConnection();
                                        resposta = new Gson().toJson(adResponse) + "\n";
                                    }
                                }
                            }
                            outToClient.writeBytes(resposta);
                            break;
                        case "ver":
                            if (!"inventario".equals(requisicao.argumentos[0]) &&
                                    !"equipamentos".equals(requisicao.argumentos[0]) &&
                                    !"vida".equals(requisicao.argumentos[0]) &&
                                    !"status".equals(requisicao.argumentos[0])) {
                                resposta = response.createResponse(ResponseTypes.VER_INVALIDO);
                            } else {
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);

                                adResponse.personagem = personagem;
                                if ("inventario".equals(requisicao.argumentos[0])) {
                                    if (personagem.inventario.size() > 0) {
                                        adResponse.mensagemAdicional = personagem.getNome() + " você possui os seguintes itens:\n";
                                        for (Item item : personagem.inventario) {
                                            adResponse.mensagemAdicional += item.getNome() + "\n";
                                        }
                                    } else {
                                        adResponse.mensagemAdicional = personagem.getNome() + ", infelizmente você não possui nenhum item :(\n";
                                    }
                                } else if ("equipamentos".equals(requisicao.argumentos[0])) {
                                    if (personagem.getCapacete() != null) {
                                        adResponse.mensagemAdicional += "Cabeca: " + personagem.getCapacete().getNome() +
                                                " - " + personagem.getCapacete().getDano() + "/" + personagem.getCapacete().getDefesa() + "\n";
                                    } else {
                                        adResponse.mensagemAdicional += "Cabeca: Nenhum\n";
                                    }
                                    if (personagem.getArmadura() != null) {
                                        adResponse.mensagemAdicional += "Armadura: " + personagem.getArmadura().getNome() +
                                                " - " + personagem.getArmadura().getDano() + "/" + personagem.getArmadura().getDefesa() + "\n";
                                    } else {
                                        adResponse.mensagemAdicional += "Armadura: Nenhum\n";
                                    }
                                    if (personagem.getEspada() != null) {
                                        adResponse.mensagemAdicional += "Espada: " + personagem.getEspada().getNome() +
                                                " - " + personagem.getEspada().getDano() + "/" + personagem.getEspada().getDefesa() + "\n";
                                    } else {
                                        adResponse.mensagemAdicional += "Espada: Nenhum\n";
                                    }
                                    if (personagem.getEscudo() != null) {
                                        adResponse.mensagemAdicional += "Escudo: " + personagem.getEscudo().getNome() +
                                                " - " + personagem.getEscudo().getDano() + "/" + personagem.getEscudo().getDefesa() + "\n";
                                    } else {
                                        adResponse.mensagemAdicional += "Escudo: Nenhum\n";
                                    }
                                    if (personagem.getPerneira() != null) {
                                        adResponse.mensagemAdicional += "Perneira: " + personagem.getPerneira().getNome() +
                                                " - " + personagem.getPerneira().getDano() + "/" + personagem.getPerneira().getDefesa() + "\n";
                                    } else {
                                        adResponse.mensagemAdicional += "Perneira: Nenhum\n";
                                    }
                                    if (personagem.getCalcado() != null) {
                                        adResponse.mensagemAdicional += "Calcado: " + personagem.getCalcado().getNome() +
                                                " - " + personagem.getCalcado().getDano() + "/" + personagem.getCalcado().getDefesa() + "\n";
                                    } else {
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
                            if (personagem.inventario.size() == 0) {
                                resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);
                            } else {
                                boolean possuiItem = false;
                                int i;
                                for (i = 0; i < personagem.inventario.size(); i++) {
                                    Item item = (Item) personagem.inventario.toArray()[i];
                                    if(item.getNome().equals(requisicao.argumentos[0])){
                                        possuiItem = true;
                                        break;
                                    }
                                }
                                if (!possuiItem) {
                                    resposta = response.createResponse(ResponseTypes.USAR_INVALIDO);
                                } else {
                                    resposta = response.createResponse(ResponseTypes.SUCESSO);
                                    outToClient.writeBytes(resposta);

                                    Item item = (Item) personagem.inventario.toArray()[i];

                                    String nomeItem = item.getNome();
                                    if(item.categoria.equals(Item.Categoria.EQUIPAMENTO)){
                                        adResponse.mensagemAdicional = "Equipando " + item.getNome() + "\n";
                                        if("bone".equals(nomeItem) || "capacete".equals(nomeItem) || "capacete_if".equals(nomeItem)){
                                            if(personagem.getCapacete() != null){
                                                personagem.inventario.add(personagem.getCapacete());
                                            }
                                            personagem.setCapacete(new Capacete(nomeItem, item.raridade));
                                        }else if("moletom_computacao".equals(nomeItem) || "armadura".equals(nomeItem) || "armadura_if".equals(nomeItem)){
                                            if(personagem.getArmadura() != null){
                                                personagem.inventario.add(personagem.getArmadura());
                                            }
                                            personagem.setArmadura(new Armadura(nomeItem, item.raridade));
                                        }else if("bambu".equals(nomeItem) || "espada".equals(nomeItem) || "espada_if".equals(nomeItem)){
                                            if(personagem.getEspada() != null){
                                                personagem.inventario.add(personagem.getEspada());
                                            }
                                            personagem.setEspada(new Espada(nomeItem, item.raridade));
                                        }else if("escudo_simples".equals(nomeItem) || "escudo_pesado".equals(nomeItem) || "escudo_if".equals(nomeItem)){
                                            if(personagem.getEscudo() != null){
                                                personagem.inventario.add(personagem.getEscudo());
                                            }
                                            personagem.setEscudo(new Escudo(nomeItem, item.raridade));
                                        }else if("bermuda".equals(nomeItem) || "perneira_pesada".equals(nomeItem) || "perneira_if".equals(nomeItem)){
                                            if(personagem.getPerneira() != null){
                                                personagem.inventario.add(personagem.getPerneira());
                                            }
                                            personagem.setPerneira(new Perneira(nomeItem, item.raridade));
                                        }else{
                                            if(personagem.getCalcado() != null){
                                                personagem.inventario.add(personagem.getCalcado());
                                            }
                                            personagem.setCalcado(new Calcado(nomeItem, item.raridade));
                                        }
                                        personagem.recalculaDano();
                                        personagem.recalculaDefesa();
                                        adResponse.mensagemAdicional += "Agora voce possui " + personagem.getDano() +
                                                " de dano e " + personagem.getDefesa() + " de defesa\n";
                                    }else{
                                        adResponse.mensagemAdicional = "Usando " + item.getNome() + "\n";
                                        personagem.usarPocao(new Pot(nomeItem, item.raridade));
                                        adResponse.mensagemAdicional += "Agora voce possui " + personagem.getVidaAtual() + "/" + personagem.getVidaMax() +" de vida\n";
                                    }
                                    for (Iterator<Item> it = personagem.inventario.iterator(); it.hasNext();) {
                                        if (it.next().getNome().equals(item.getNome())) {
                                            it.remove();
                                        }
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
                        case "conversar":
                            mysqlConnection.openConnection();
                            if (salaAtual.getNpc() == null) {
                                resposta = response.createResponse(ResponseTypes.CONVERSAR_INVALIDO);
                            } else {
                                falandoNPC = true;
                                resposta = response.createResponse(ResponseTypes.SUCESSO);
                                outToClient.writeBytes(resposta);
                                adResponse.personagem = personagem;
                                adResponse.mensagemAdicional = "Tia do Bar: Ola, "+personagem.getNome()+"!\nEssas sao nossas ofertas: \n";
                                for (Item i: salaAtual.getNpc().getItensAVenda()) {
                                    adResponse.mensagemAdicional +="Item: "+i.getNome()+"\t\tValor: "+ i.getValor()+"\n";
                                }
                                adResponse.mensagemAdicional += "Ou voce pode vender alguns de seus itens tambem.\n";
                                resposta = new Gson().toJson(adResponse) + "\n";
                            }
                            mysqlConnection.closeConnection();
                            outToClient.writeBytes(resposta);
                            break;
                        default:
                            resposta = response.createResponse(ResponseTypes.COMANDO_INVALIDO);
                            outToClient.writeBytes(resposta);
                            resposta = new Gson().toJson(personagem) + "\n";
                            outToClient.writeBytes(resposta);
                            break;
                    }
                }
            } catch (IOException | SQLException e) {
                if(e instanceof IOException){
                    System.out.println("Problema de leitura/escrita");
                }else{
                    System.out.println("Problemas com banco de dados");
                }
                resposta = response.createResponse(ResponseTypes.ERRO_INTERNO);
                try {
                    outToClient.writeBytes(resposta);
                } catch (IOException ex) {
                    System.out.println("Problemas para informar erro interno");
                    return;
                }
            } catch (Exception e){
                System.out.println("Erro interno: " + e.getMessage());
                resposta = response.createResponse(ResponseTypes.ERRO_INTERNO);
                try {
                    outToClient.writeBytes(resposta);
                } catch (IOException ex) {
                    System.out.println("Problemas para informar erro interno");
                    return;
                }
            }
        }
    }
}
