package entities;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import entities.DAO.MonstroDAO;
import inimigos.Boss;
import inimigos.Minichefe;
import inimigos.Monstro;
import inimigos.TipoMonstro;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class MonstroTest {

    @Test
    public void criarTabelaMonstro() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        mysqlConnection.createTable(new Monstro());

    }
    @Test
    public void criarMonstro() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();
        //mysqlConnection.createTable(new Monstro());
        Monstro monstro = new Monstro();
        monstro.setNome("Monstro Test");
        monstro.setVidaAtual(1000);
        monstro.setVidaMax(1000);
        monstro.setExpDada(666);
        monstro.setOuroDado(1000);
        monstro.setNivel(10);
        monstro.setDefesa(10);
        monstro.setDano(10000);
        monstro.setTipoMonstro(TipoMonstro.MINICHEFE);

        mysqlConnection.openConnection();
        MonstroDAO monstroDAO = new MonstroDAO(mysqlConnection.connectionSource);
        monstroDAO.create(monstro);
        mysqlConnection.closeConnection();

    }


    @Test
    public void criaBoss() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();
        //mysqlConnection.createTable(new Monstro());
        Boss monstro = new Boss(10, "Billy");
        //monstro.setNome("Billy");
        monstro.setVidaAtual(1000);
        monstro.setVidaMax(1000);
        monstro.setExpDada(666);
        monstro.setOuroDado(1000);
        monstro.setNivel(10);
        monstro.setDefesa(10);
        monstro.setDano(10000);

        System.out.println(monstro.getTipoMonstro().toString());
        mysqlConnection.openConnection();
        MonstroDAO monstroDAO = new MonstroDAO(mysqlConnection.connectionSource);
        monstroDAO.create(monstro);

        mysqlConnection.closeConnection();

    }

    @Test
    public void populaBanco() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();
        mysqlConnection.openConnection();
        MonstroDAO monstroDAO = new MonstroDAO(mysqlConnection.connectionSource);

        Monstro monstro = new Monstro(1, "calouro");
        monstroDAO.create(monstro);

        monstro = new Monstro(2, "veterano");
        monstroDAO.create(monstro);

        monstro = new Monstro(3, "troll");
        monstroDAO.create(monstro);

        monstro = new Monstro(4, "chato");
        monstroDAO.create(monstro);

        monstro = new Monstro(5, "voluntario");
        monstroDAO.create(monstro);

        monstro = new Monstro(6, "bolsista");
        monstroDAO.create(monstro);

        monstro = new Monstro(7, "petiano");
        monstroDAO.create(monstro);

        monstro = new Minichefe(8, "professor");
        monstroDAO.create(monstro);

        monstro = new Minichefe(9, "goncalinho");
        monstroDAO.create(monstro);

        monstro = new Boss(10, "pulla");
        monstroDAO.create(monstro);

        mysqlConnection.closeConnection();
    }
}
