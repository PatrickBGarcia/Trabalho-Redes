package entities;

import java.sql.*;

public class MysqlConnection {
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String dbName = "redes";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String userName = "root";
    private static String dbpassword = "";


    public static String executeQuery(String sql){
        try {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url+dbName,userName,dbpassword);
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            // Closing the statement and connection
            st.close();
            c.close();
            return rs.toString();
        } catch (ClassNotFoundException e) {
            System.out.println("Nao encontrado drive de comunicacao com mysql");
            return null;
        } catch (SQLException e) {
            System.out.printf("Erro na execucao da query");
            return null;
        } catch (Exception e){
            System.out.println("Erro inesperado: " + e.getStackTrace());
            return null;
        }
    }

}
