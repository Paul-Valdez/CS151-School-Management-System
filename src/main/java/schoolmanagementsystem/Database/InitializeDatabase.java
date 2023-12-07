/**
    ATTENTION: DO NOT RUN IF DATABASE HAS ALREADY BEEN INITIALIZED!
    THIS ONLY NEEDS TO BE RAN ONCE.

    RUN THIS FILE TO INITIALIZE THE DATABASE TABLES AND ADMIN USER VIA SCRIPT.
    POSTGRESQL DATABASE MUST ALREADY BE CREATED BEFORE RUNNING.
*/

package schoolmanagementsystem.Database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;
import schoolmanagementsystem.Utilities.DatabaseConnection;

public class InitializeDatabase {
	public static void main(String[] args) {
		init();
	}

	public static void init() {
		Connection conn = null;
		Reader reader = null;

		try {
			// Connect to database
			conn = DatabaseConnection.getDBConnection();

			// Initialize the script runner
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null); // Disable logging if not needed
			runner.setStopOnError(true); // Stop execution on SQL error

			// Load the SQL script
			InputStream inputStream = InitializeDatabase.class.getClassLoader()
					.getResourceAsStream("sms_database_initialization.sql");

			if (inputStream == null) {
				System.out.println("Resource not found: sms_database_initialization.sql");
				return;
			}

			reader = new BufferedReader(new InputStreamReader(inputStream));

			// Execute the script
			runner.runScript(reader);

			System.out.println("Database initialization script executed successfully!");
		} catch (SQLException ex) {
			System.out.println("SQL Error: " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}