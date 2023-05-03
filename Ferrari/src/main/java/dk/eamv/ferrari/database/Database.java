package dk.eamv.ferrari.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dk.eamv.ferrari.ConnectionString;

public abstract class Database {
    private static Connection connection;

    // Default SQL Server init
    public static void init() {
        init(ConnectionString.getConnectionString());
    }

    public static void init(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
