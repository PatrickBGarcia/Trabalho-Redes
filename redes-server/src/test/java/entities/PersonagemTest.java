package entities;

import entities.DAO.PersonagemDAO;
import org.junit.Test;
import personagem.Personagem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        personagem.setNome("patrick");
        personagem.setSenha("1234");

        mysqlConnection.openConnection();
        PersonagemDAO personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
        personagemDAO.create(personagem);
        mysqlConnection.closeConnection();

    }
    @Test
    public void selecionaPersonagem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        mysqlConnection.openConnection();
        PersonagemDAO personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
        List<Personagem> listPersonagem = personagemDAO.queryForEq("nome", "patrick");
        mysqlConnection.closeConnection();

        if(listPersonagem.size() > 0){
            assertEquals("patrick", listPersonagem.get(0).getNome());
        }else{
            fail();
        }

    }

    @Test
    public void atualizaPersonagem() throws SQLException, IOException {
        MysqlConnection mysqlConnection = new MysqlConnection();

        mysqlConnection.openConnection();
        PersonagemDAO personagemDAO = new PersonagemDAO(mysqlConnection.connectionSource);
        Personagem personagem = personagemDAO.queryForEq("nome", "patrick").get(0);
        personagem.setNome("cleyton");
        personagemDAO.update(personagem);
        mysqlConnection.closeConnection();

    }
}
