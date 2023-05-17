package dk.eamv.ferrari.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public abstract class Database {
    private static Connection connection;

    // Default SQL Server init
    public static void init() {
        String connectionString = System.getenv("SQL_SERVER_CONNECTION");
        if (connectionString == null) {
            throw new RuntimeException("Environment variable SQL_SERVER_CONNECTION not set");
        }

        init(connectionString);
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

    public static boolean execute(String query) {
        try {
            return getConnection().createStatement().execute(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public static ResultSet query(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
