package dk.eamv.ferrari.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

// Made by Benjamin
public abstract class Database {
    private static Connection connection;

    /** 
     * Initialize connection to the database.
    */
    public static void init() {
        String connectionString = System.getenv("SQL_SERVER_CONNECTION");
        if (connectionString == null) {
            throw new RuntimeException("Environment variable SQL_SERVER_CONNECTION not set");
        }

        init(connectionString);
    }

    /**
     * Initialize connection to the database using a connection string
     * @param connectionString the SQL Server connection URL
     */
    public static void init(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Get the current database connection
     * @return the active connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Execute a query, crash on failure
     * @param query database query string
     * @return SQL.Statement.execute() boolean result
     */
    public static boolean execute(String query) {
        try {
            return getConnection().createStatement().execute(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * Run a query, crash on failure
     * @param query database query string
     * @return SQL.ResultSet
     */
    public static ResultSet query(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
