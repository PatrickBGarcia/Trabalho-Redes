package entities;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import entities.DAO.ComercianteDAO;
import entities.DAO.ItemDAO;
import itens.Item;
import npcs.Comerciante;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComercianteTest {

    @Test
    public void criarTabelaComerciante() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        mysqlConnection.createTable(new Comerciante());

    }
    @Test
    public void criarComerciante() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        Comerciante comerciante = new Comerciante();
        comerciante.setNome("Comerciante Teste");

        mysqlConnection.openConnection();
        ItemDAO itemDAO = new ItemDAO(mysqlConnection.connectionSource);
        List<Item> listItens = itemDAO.queryForEq("nome", "Capacete Teste");

        comerciante.setItensAVenda(Arrays.asList(new Item()));
        //comerciante.setItensAVenda(listItens);
        //for (Item i: listItens) {
          //  comerciante.setItensAVenda(i);
        //}


        ComercianteDAO comercianteDAO = new ComercianteDAO(mysqlConnection.connectionSource);
        comercianteDAO.create(comerciante);

        List<Comerciante> t = comercianteDAO.queryForEq("id", 4);

        mysqlConnection.closeConnection();

    }
}