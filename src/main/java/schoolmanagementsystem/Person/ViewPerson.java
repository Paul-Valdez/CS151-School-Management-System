package schoolmanagementsystem.Person;

import schoolmanagementsystem.ApplicationWindow;
import schoolmanagementsystem.Utilities.DatabaseConnection;
import static schoolmanagementsystem.Person.PersonModule.COLUMN_NAMES;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/** Class for inserting a person into PERSONS table. */
public class ViewPerson extends ApplicationWindow {

    private JTextField[] personInfo;
    private final Font headerFont = new Font("SansSerif", Font.BOLD, 16); // Example font, adjust as needed

    /** Default constructor for AddPerson class. */
    public ViewPerson() {
        this.initComponents();
        this.pack(); // Adjusts the window size based on the preferred sizes of its components
        this.setLocationRelativeTo(null); // This will center the window

        // Add window listener to request focus for the frame
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
    }

    public ViewPerson(int personID) {
        this.initComponents();
        this.viewPerson(personID);
        this.pack(); // Adjusts the window size based on the preferred sizes of its components
        this.setLocationRelativeTo(null); // This will center the window

        // Add window listener to request focus for the frame
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
    }

    /** Helper method for focusing on window */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        this.requestFocusInWindow();
    }

    /** Initializes form components. */
    private void initComponents() {
        // Labels
        JLabel[] personInfoLabel = new JLabel[9];

        for (int i = 0; i < personInfoLabel.length; i++)
            personInfoLabel[i] = new JLabel(COLUMN_NAMES[i]);

        this.personInfo = new JTextField[personInfoLabel.length];

        for (int i = 0; i < this.personInfo.length; i++) {
            this.personInfo[i] = new JTextField();
            this.personInfo[i].setEditable(false);
            this.personInfo[i].setBackground(Color.WHITE);
        }

        JPanel textFields = new JPanel();
        JLabel label = new JLabel("View Person");
        label.setFont(headerFont);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        GroupLayout layout = new GroupLayout(textFields);
        textFields.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup labelGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        GroupLayout.ParallelGroup fieldGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

        // Add existing labels and text fields to the layout
        for (int i = 0; i < personInfoLabel.length; i++) {
            labelGroup.addComponent(personInfoLabel[i]);
            fieldGroup.addComponent(this.personInfo[i]);
        }

        // Add the label and field groups to the horizontal group
        hGroup.addGroup(labelGroup);
        hGroup.addGroup(fieldGroup);

        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        for (int i = 0; i < personInfoLabel.length; i++) {
            verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(personInfoLabel[i]).addComponent(this.personInfo[i]));
        }

        layout.setVerticalGroup(verticalGroup);

        // Add components to the content pane
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(textFields, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Establishes database connection, prepares and performs the database query to insert person into PERSONS table.
     *
     * @return null
     */
    private String[] viewPerson(int personID) {
        // Establish database connection
        Connection conn = null;
        PreparedStatement prepStmt = null;

        try {
            // Get the database connection
            conn = DatabaseConnection.getDBConnection();

            // Prepare the SQL statement
            String sql = "SELECT * FROM PERSONS WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, personID); // Replace someId with the actual ID value you want to query
            ResultSet resultSet = prepStmt.executeQuery(); // Execute the query

            // Check if ResultSet has at least one row
            if (resultSet.next()) {
                // Iterate over each column and retrieve values
                for (int i = 0; i < personInfo.length; i++) {
                    String columnValue = String.valueOf(resultSet.getObject(i + 1));

                    if (columnValue.equals("null"))
                        personInfo[i].setText(null);
                    else
                        personInfo[i].setText(columnValue);
                }
            }

            PersonModule.refreshTable();

        } catch (SQLException e) {
            // Handle SQL related errors
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Handle missing JDBC driver
            JOptionPane.showMessageDialog(null, "Driver Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Handle database connection errors
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new String[0];
    }
}