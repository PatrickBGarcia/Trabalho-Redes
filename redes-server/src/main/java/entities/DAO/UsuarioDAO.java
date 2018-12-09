package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import entities.Usuario;

import java.sql.SQLException;

public class UsuarioDAO extends BaseDaoImpl<Usuario, Integer> {

    public UsuarioDAO(ConnectionSource cs) throws SQLException {
        super(cs,Usuario.class);
    }
}
