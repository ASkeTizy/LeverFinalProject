package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getDatabaseConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://172.19.192.2:5432/gamestore", "user", "secret"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
