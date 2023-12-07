/**
    ATTENTION: DO NOT RUN IF DATABASE HAS ALREADY BEEN POPULATED!
    THIS ONLY NEEDS TO BE RAN ONCE.

    RUN THIS FILE TO POPULATE THE DATABASE TABLES.
    POSTGRESQL DATABASE AND RESPECTIVE TABLES MUST ALREADY BE CREATED BEFORE RUNNING.
*/

package schoolmanagementsystem.Database;

import schoolmanagementsystem.Utilities.DatabaseConnection;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PopulateDatabase {
	
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        Connection conn = null;
        PreparedStatement ptstmt = null;

        try {
            // Connect to database
            conn = DatabaseConnection.getDBConnection();

            // Prepare the SQL statement
            String sql = "INSERT INTO PERSONS (prefix_name, first_name, middle_name, last_name, suffix_name, " +
                    "birthdate, address, phone_number, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ptstmt = conn.prepareStatement(sql);

            // Read values from file
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/sms_database_population.txt"))) {
                String line;
                List<String> personData = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        // Add non-empty lines to the list
                        personData.add(line);
                    } else if (!personData.isEmpty()) {
                        // Process the accumulated data when an empty line is encountered
                        processPersonData(ptstmt, personData);
                        // Clear the list for the next record
                        personData.clear();
                    }
                }

                // Process the last record if the list is not empty
                if (!personData.isEmpty()) {
                    processPersonData(ptstmt, personData);
                }
            }

            System.out.println("Database population executed successfully!");

        } catch(SQLException e){    // Handle SQL related errors
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } catch(ClassNotFoundException e){
            // Handle missing JDBC driver
            JOptionPane.showMessageDialog(null, "Driver Error: " + e.getMessage());
        } catch(IllegalStateException e){
            // Handle database connection errors
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
        } catch (IOException e) {
            // Handle file reading errors
            JOptionPane.showMessageDialog(null, "File Reading Error: " + e.getMessage());
        } finally{
            try {
                if (ptstmt != null)
                    ptstmt.close();
                if (conn != null) 
                    conn.close();              
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void processPersonData(PreparedStatement ptstmt, List<String> personData) throws SQLException {
        if (personData.size() == 9) {
            for (int i = 0; i < personData.size(); i++) {
                if (i == 5) { // Birth date field
                    if(personData.get(i).equals("null"))
                        ptstmt.setNull(i + 1, Types.DATE);
                    else {
                        java.sql.Date sqlDate = java.sql.Date.valueOf(personData.get(i));
                        ptstmt.setDate(i + 1, sqlDate);
                    }
                }
                else if (personData.get(i).equals("null")) {
                    ptstmt.setString(i + 1, null);
                }
                else {
                    ptstmt.setString(i + 1, personData.get(i));
                }
            }
            ptstmt.execute(); // Execute once all parameters are set
        }
    }


}