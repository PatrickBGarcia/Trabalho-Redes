package entities;

import entities.DAO.PersonagemDAO;
import org.junit.Test;
import personagem.Personagem;

import java.io.IOException;
import java.sql.SQLException;

public class PersonagemTest {

    @Test
    public void criarTabelaPersonagem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();
        //TESTAR DEPOIS DE CRIAR A TABELA ITEM
        mysqlConnection.createTable(new Personagem());

    }
    @Test
    public void criarPersonagem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        Personagem personagem = new Personagem();
        personagem.setNome("Patrick");
        personagem.setSenha("1234");

        mysqlConnection.openConnection();
        PersonagemDAO personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
        personagemDAO.create(personagem);
        mysqlConnection.closeConnection();

    }
}
