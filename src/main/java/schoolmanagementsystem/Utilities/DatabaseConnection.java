package schoolmanagementsystem.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection is a utility class providing a method to create and establish the database connection to
 * allow database queries to be executed.
 */
public class DatabaseConnection {
    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        // Retrieve database credentials from environment variables
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        // Check if any of the environment variables are not set
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalStateException("Database credentials are not set in the environment variables.");
        }

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
