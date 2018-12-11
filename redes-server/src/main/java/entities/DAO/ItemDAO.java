package entities.DAO;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import itens.Item;


import java.sql.SQLException;

public class ItemDAO extends BaseDaoImpl<Item, Integer> {

    public ItemDAO(ConnectionSource cs) throws SQLException {
        super(cs, Item.class);
    }
}