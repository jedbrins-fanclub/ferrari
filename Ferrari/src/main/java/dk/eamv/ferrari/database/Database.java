package dk.eamv.ferrari.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database {
    private static Connection connection;

    // Default SQL Server init
    public static void init() {
        init("jdbc:sqlserver://localhost:1433;database=master;integratedSecurity=true;encrypt=true;trustServerCertificate=true;loginTimeout=15;");
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
