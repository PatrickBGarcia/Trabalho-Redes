package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import personagem.Personagem;

import java.sql.SQLException;

public class PersonagemDAO extends BaseDaoImpl<Personagem, Integer> {

    public PersonagemDAO(ConnectionSource cs) throws SQLException {
        super(cs,Personagem.class);
    }
}
