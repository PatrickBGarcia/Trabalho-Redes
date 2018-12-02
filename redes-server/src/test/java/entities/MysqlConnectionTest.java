package entities;

import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class MysqlConnectionTest {

    @Test
    public void testaComunicacao(){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "redes";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String dbpassword = "";
        try {
            // loading driver
            Class.forName(driver);
            // Connection set up with database named as user
            Connection c = DriverManager.getConnection(url+dbName,userName,dbpassword);
            // Creating statement for the connection to use sql queries
            Statement st = c.createStatement();
            // Executing sql query using the created statement over the table user_details located in the database pointing by the dsn
            ResultSet rs = st.executeQuery("SHOW TABLES");
            // Accessing the result of query execution
            while(rs.next()) {
                String username = rs.getString(1);
                System.out.println(username);
            }
            // Closing the statement and connection
            st.close();
            c.close();
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (SQLException e) {
            Assert.fail();
        }
    }


}
