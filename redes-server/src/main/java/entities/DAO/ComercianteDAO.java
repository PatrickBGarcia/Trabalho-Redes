package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import npcs.Comerciante;

import java.sql.SQLException;

public class ComercianteDAO extends BaseDaoImpl<Comerciante, Integer> {

    public ComercianteDAO(ConnectionSource cs) throws SQLException {
        super(cs, Comerciante.class);
    }
}
