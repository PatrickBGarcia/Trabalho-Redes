package mapa;

import entities.DAO.ItemDAO;
import entities.MysqlConnection;
import itens.Item;
import npcs.Comerciante;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Mapa {

    public static Sala iniciar(){
        Sala portao = new Sala("Portao do Anglo");
        Sala biblioteca = new Sala("Biblioteca");
        Sala obraRU = new Sala("Obra do RU");
        Sala estacionamento = new Sala("Estacionamento");
        Sala corredorPrimeiroAndar = new Sala("Corredor 1 Andar");
        Sala cafeteria = new Sala("Cafe Universitario");
        Sala corredorSegundoAndar = new Sala("Corredor 2 Andar");
        Sala corredorTerceiroAndar = new Sala("Corredor 3 Andar");
        Sala corredorQuartoAndar = new Sala("Corredor 4 Andar");

        Comerciante npc = new Comerciante("roberto");

        MysqlConnection mysqlConnection = new MysqlConnection();
        ItemDAO itemDAO;
        try {
            mysqlConnection.openConnection();
            itemDAO = new ItemDAO(mysqlConnection.connectionSource);
            List<Item> itens = itemDAO.queryForAll();
            npc.setItensAVenda(itens);
        } catch (SQLException e) {
            System.out.println("Problemas para abrir conexão com mysql");
            return null;
        }
        try {
            mysqlConnection.closeConnection();
        } catch (IOException e) {
            System.out.println("Problemas para fechar conexão com mysql");
        }

        cafeteria.npc = npc;

        corredorQuartoAndar.addAdjacente(corredorTerceiroAndar, Direcao.ABAIXO);
        corredorTerceiroAndar.addAdjacente(corredorQuartoAndar, Direcao.A_CIMA);
        corredorTerceiroAndar.addAdjacente(corredorSegundoAndar, Direcao.ABAIXO);
        corredorSegundoAndar.addAdjacente(corredorTerceiroAndar, Direcao.A_CIMA);
        corredorSegundoAndar.addAdjacente(corredorPrimeiroAndar, Direcao.ABAIXO);
        corredorPrimeiroAndar.addAdjacente(corredorSegundoAndar, Direcao.A_CIMA);
        corredorPrimeiroAndar.addAdjacente(cafeteria, Direcao.NORTE);
        cafeteria.addAdjacente(corredorPrimeiroAndar, Direcao.SUL);
        corredorPrimeiroAndar.addAdjacente(estacionamento, Direcao.SUL);
        estacionamento.addAdjacente(corredorPrimeiroAndar, Direcao.NORTE);
        estacionamento.addAdjacente(obraRU, Direcao.SUL);
        obraRU.addAdjacente(estacionamento, Direcao.NORTE);
        obraRU.addAdjacente(portao, Direcao.SUL);
        portao.addAdjacente(obraRU, Direcao.NORTE);
        biblioteca.addAdjacente(portao, Direcao.LESTE);
        portao.addAdjacente(biblioteca, Direcao.OESTE);

        return portao;
    }
}
