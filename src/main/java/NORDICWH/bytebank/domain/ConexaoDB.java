package NORDICWH.bytebank.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bytebank?user=root&password=root");

            System.out.println("Recuperei a conexÃ£o");

            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}