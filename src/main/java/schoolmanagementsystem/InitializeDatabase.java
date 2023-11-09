/*
    ATTENTION: DO NOT RUN IF DATABASE HAS ALREADY BEEN INITIALIZED!
    THIS ONLY NEEDS TO BE RAN ONCE.

    RUN THIS FILE TO INITIALIZE THE DATABASE TABLES AND ADMIN USER VIA SCRIPT.
    POSTGRESQL DATABASE MUST BE CREATED ALREADY BEFORE RUNNING.
*/

package schoolmanagementsystem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.ibatis.jdbc.ScriptRunner;

public class InitializeDatabase {
    public static void main(String[] args) {
        // PostgreSQL database connection details
        String url = "jdbc:postgresql://db.ngkqyysuhzwkiczqojsa.supabase.co:5432/postgres";
        String user = "postgres";
        String password = "mtSEfzZNMQDw2qpy";

        try {
            // Connect to database
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Initialize the script runner
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null); // Disable logging if not needed

            // Load the SQL script
            InputStream inputStream = InitializeDatabase.class.getClassLoader().getResourceAsStream("sms_database_initialization.sql");
            if (inputStream == null) {
                System.out.println("Resource not found: sms_database_initialization.sql");
                return;
            }
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Execute the script
            runner.runScript(reader);

            // Close the reader and connection
            reader.close();
            conn.close();

            System.out.println("Script executed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}