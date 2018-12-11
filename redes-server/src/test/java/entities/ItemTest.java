package entities;

import entities.DAO.ItemDAO;
import itens.Item;
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
}
