/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

/** Entry point/bootstrap class for the School Management System application.
 * @author dell
 */
public class SchoolManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new LoginPage().showWindow();
        } catch (Exception e) {
            // Log the exception or show an error dialog
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();

            // Exit the application
            System.exit(1);
        }
    }
}
