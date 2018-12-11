package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import entities.MysqlConnection;
import personagem.Personagem;

import java.sql.SQLException;
import java.util.List;

public class PersonagemDAO extends BaseDaoImpl<Personagem, Integer> {

    public PersonagemDAO(ConnectionSource cs) throws SQLException {
        super(cs,Personagem.class);
    }

    public boolean usuarioExiste(String nome) throws SQLException {
        return this.queryForEq("nome", nome).size() > 0;
    }
}
