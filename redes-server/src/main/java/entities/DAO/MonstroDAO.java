package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import inimigos.Monstro;

import java.sql.SQLException;

public class MonstroDAO extends BaseDaoImpl<Monstro, Integer> {

    public MonstroDAO(ConnectionSource cs) throws SQLException {
        super(cs,Monstro.class);
    }
}
