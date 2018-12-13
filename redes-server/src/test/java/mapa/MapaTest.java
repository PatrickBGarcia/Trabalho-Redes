package mapa;

import entities.DAO.ItemDAO;
import entities.MysqlConnection;
import itens.Item;
import npcs.Comerciante;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MapaTest {

    @Test
    public void criaMapa(){
        Sala inicial = Mapa.iniciar();
        Assert.assertEquals(inicial.getNome(), "Portao do Anglo");
        Sala obraRU = inicial.getSalaAoRedor().get(Direcao.NORTE);
        Assert.assertEquals(obraRU.getNome(), "Obra do RU");
    }

    @Test
    public void criaNegociante() throws SQLException, IOException {
        Comerciante npc = new Comerciante("roberto");

        MysqlConnection mysqlConnection = new MysqlConnection();
        mysqlConnection.openConnection();
        ItemDAO itemDAO = new ItemDAO(mysqlConnection.connectionSource);
        List<Item> itens = itemDAO.queryForAll();
        npc.setItensAVenda(itens);
        mysqlConnection.closeConnection();

        Sala inicial = Mapa.iniciar();
        inicial.npc = npc;

        System.out.println("Olá, sou o " + npc.getNome() + " e possuo os seguintes itens a venda:");
        for(Item elemento: inicial.npc.getItensAVenda()){
            System.out.println(elemento.getNome());
        }
    }

}
