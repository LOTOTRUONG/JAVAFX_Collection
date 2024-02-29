package main.collection.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection;

    public DatabaseConnection() {

    }

    public static Connection getConnection() {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1434; database = SML";
            String user = "sa";
            String password = "sml@123456";
            connection = DriverManager.getConnection(dbURL, user, password);
            return connection;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }

    }
}
