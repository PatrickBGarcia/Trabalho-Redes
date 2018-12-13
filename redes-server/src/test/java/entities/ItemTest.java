package entities;

import entities.DAO.ItemDAO;
import itens.Item;
import itens.combate.*;
import itens.consumivel.Pot;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class ItemTest {

    @Test
    public void criarTabelaItem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        mysqlConnection.createTable(new Item());

    }
    @Test
    public void criarItem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        Item item = new Item();
        item.setNome("Capacete Teste");

        mysqlConnection.openConnection();
        ItemDAO itemDAO = new ItemDAO(mysqlConnection.connectionSource);
        itemDAO.create(item);
        mysqlConnection.closeConnection();

    }

    @Test
    public void populaBanco() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();
        mysqlConnection.openConnection();
        ItemDAO itemDAO = new ItemDAO(mysqlConnection.connectionSource);

        Item item = new Capacete("bone", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Capacete("capacete", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Capacete("capacete_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Armadura("moletom_computacao", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Armadura("armadura", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Armadura("armadura_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Espada("bambu", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Espada("espada", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Espada("espada_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Escudo("escudo_simples", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Escudo("escudo_pesado", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Escudo("escudo_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Perneira("bermuda", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Perneira("perneira_pesada", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Perneira("perneira_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Calcado("chinelo", Item.Raridade.NORMAL);
        itemDAO.create(item);
        item = new Calcado("grevas", Item.Raridade.RARO);
        itemDAO.create(item);
        item = new Calcado("grevas_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        item = new Pot("pocao_simples", Item.Raridade.NORMAL);
        itemDAO.create(item);

        item = new Pot("pocao_media", Item.Raridade.RARO);
        itemDAO.create(item);

        item = new Pot("pocao_if", Item.Raridade.MUITO_RARO);
        itemDAO.create(item);

        mysqlConnection.closeConnection();
    }
}
