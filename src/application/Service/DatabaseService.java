package application.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final String SUPABASE_DB_URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
    private static final String SUPABASE_USER = "postgres.gjgvodaqvjfdujvkgzye";
    private static final String SUPABASE_PASSWORD = "FinanceSystemOODProject";

    // Remove static connection
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                SUPABASE_DB_URL,
                SUPABASE_USER,
                SUPABASE_PASSWORD
            );
            System.out.println("Connected to Supabase PostgreSQL database!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            return null;
        }
    }

    // Close connection method
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection: " + e.getMessage());
            }
        }
    }
}
