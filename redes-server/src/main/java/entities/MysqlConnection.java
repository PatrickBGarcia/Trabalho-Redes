package entities;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.sql.*;

public class MysqlConnection {
    private String url = "jdbc:mysql://localhost:3306/ds";
    private String dbName = "ds";
    private String userName = "root";
    private String dbpassword = "";
    public ConnectionSource connectionSource;


    public <T> Boolean createTable(T objeto) throws SQLException, IOException {
        openConnection();
        TableUtils.createTable(connectionSource, objeto.getClass());
        closeConnection();
        return true;
    }

    public void openConnection() throws SQLException {
        connectionSource = new JdbcConnectionSource(url, userName, dbpassword);
    }

    public void closeConnection() throws IOException {
        connectionSource.close();
    }



}
