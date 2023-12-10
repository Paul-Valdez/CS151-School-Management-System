package schoolmanagementsystem.Person;

import schoolmanagementsystem.ApplicationWindow;
import schoolmanagementsystem.Utilities.DatabaseConnection;
import schoolmanagementsystem.Utilities.InputValidation;
import static schoolmanagementsystem.Person.PersonModule.COLUMN_NAMES;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import javax.swing.text.AbstractDocument;


/** Class for inserting a person into PERSONS table. */
public class EditPerson extends ApplicationWindow {
    private JTextField[] personInfoTextFields;
    private JButton delete, clear, submit;
    private String[] personInfoValues;
    private final int personID;

    public EditPerson(int personID) {
        this.personID = personID;
        personInfoValues = new String[12];
        personInfoTextFields = new JTextField[12];

        getPersonInfoFromDB(personID);
        initComponents();
        pack(); // Adjusts the window size based on the preferred sizes of its components
        setLocationRelativeTo(null); // This will center the window

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

    /** Trims textfield from leading and trailing space characters */
    class TrimmingDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            text = (text == null) ? "" : text.trim();
            super.insertString(fb, offset, text, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            text = (text == null) ? "" : text.trim();
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /** Initializes form components */
    private void initComponents() {
        // Max lengths for textFields
        int[] fieldMaxCharLengths = {9, 50, 100, 100, 100, 50, 4, 2, 2, 255, 20, 254};

        // Labels
        JLabel label = new JLabel("Edit Person");
        label.setFont(headerFont);  // Ensure headerFont is initialized
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Create Person Info Labels
        JLabel[] personInfoLabels = new JLabel[10];
        for (int i = 0; i < personInfoLabels.length; i++) {
            personInfoLabels[i] = new JLabel(COLUMN_NAMES[i]);
        }

        // Define the preferred size for the non-birthdate text fields
        int textFieldWidth = 150; // Width in pixels
        int textFieldHeight = new JTextField().getPreferredSize().height; // Default height

        // Text Fields Initialization
        for (int i = 0; i < personInfoTextFields.length; i++) {
            personInfoTextFields[i] = new JTextField();
            ((AbstractDocument) personInfoTextFields[i].getDocument()).setDocumentFilter(new TrimmingDocumentFilter());
            setMaxLength(personInfoTextFields[i], fieldMaxCharLengths[i]);

            // Birthdate fields are handled with specific sizes
            if (i == 6) { // Year field
                personInfoTextFields[i].setPreferredSize(new Dimension(30, textFieldHeight));
            } else if (i == 7 || i == 8) { // Month and Day fields
                personInfoTextFields[i].setPreferredSize(new Dimension(20, textFieldHeight));
            }
        }

        // Calculate the maximum width needed for non-birthdate text fields
        int maxTextFieldWidth = textFieldWidth; // Initialize with the default width
        FontMetrics metrics = getFontMetrics(personInfoTextFields[0].getFont());
        for (int i = 0; i < personInfoTextFields.length; i++) {
            if (i < 6 || i > 8) { // Exclude birthdate fields
                String text = personInfoValues[i]; // Use the initial text values for calculation
                if(text != null) {
                    int textWidth = metrics.stringWidth(text) + 10; // Add some padding
                    maxTextFieldWidth = Math.max(maxTextFieldWidth, textWidth);
                }
            }
        }

        // Set the calculated width for non-birthdate text fields
        for (int i = 0; i < personInfoTextFields.length; i++) {
            if (i < 6 || i > 8) { // Exclude birthdate fields
                personInfoTextFields[i].setPreferredSize(new Dimension(maxTextFieldWidth, textFieldHeight));
            }
        }

        // Setup DocumentListener to change foreground color of textfield
        for(int i = 0; i < personInfoTextFields.length; i++) {
            final int index = i; // Need a final or effectively final variable for use in the inner class

            personInfoTextFields[i].getDocument().addDocumentListener(new DocumentListener() {
                private void updateTextAndColor() {
                    try {
                        String currentText = personInfoTextFields[index].getText().trim();
//                            personInfoTextFields[index].setText(currentText); // Set trimmed text

                        if (currentText.equals(personInfoValues[index])) {
                            personInfoTextFields[index].setForeground(Color.GRAY);
                        } else {
                            personInfoTextFields[index].setForeground(Color.BLACK);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    });
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTextAndColor();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTextAndColor();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Plain text components don't fire these events
                }
            });
        }

        // Text Fields set text
        for (int i = 0; i < personInfoTextFields.length; i++) {
            // Year
            if(i == 6){
                personInfoTextFields[i].setText(personInfoValues[i]);
                addFocusListenerToTextField(personInfoTextFields[i], personInfoValues[i], false);

            }
            // Month or Day
            else if(i == 7 || i == 8){
                personInfoTextFields[i].setText(personInfoValues[i]);
                addFocusListenerToTextField(personInfoTextFields[i], personInfoValues[i], true);
            }
            // Not a birthdate field
            else {
                personInfoTextFields[i].setText(personInfoValues[i]);
                addFocusListenerToTextField(personInfoTextFields[i], personInfoValues[i], false);
            }

            // Set text color to gray to signify it has not been edited
            personInfoTextFields[i].setForeground(Color.GRAY);
        }

        // Make ID field uneditable
        personInfoTextFields[0].setEditable(false);
        personInfoTextFields[0].setForeground(Color.BLACK);

        // Text Fields Panel
        JPanel textFieldsPanel = new JPanel();
        GroupLayout textFieldsPanelLayout = new GroupLayout(textFieldsPanel);
        textFieldsPanel.setLayout(textFieldsPanelLayout);
        textFieldsPanelLayout.setAutoCreateGaps(true);
        textFieldsPanelLayout.setAutoCreateContainerGaps(true);

        // Horizontal group
        GroupLayout.SequentialGroup horizontalGroup = textFieldsPanelLayout.createSequentialGroup();
        GroupLayout.ParallelGroup labelGroup = textFieldsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        GroupLayout.ParallelGroup textFieldGroup = textFieldsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);

        // Add all labels to the label group
        for (JLabel personLabel : personInfoLabels) {
            labelGroup.addComponent(personLabel);
        }

        // Add fields for labels before the Birthdate
        for (int i = 0; i < 6; i++) { // Add components before Birthdate
            textFieldGroup.addComponent(personInfoTextFields[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        // Birthdate horizontal group
        GroupLayout.SequentialGroup birthdateGroup = textFieldsPanelLayout.createSequentialGroup()
                .addComponent(personInfoTextFields[6], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(personInfoTextFields[7], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(personInfoTextFields[8], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

        textFieldGroup.addGroup(birthdateGroup);

        // Add fields for labels after Birthdate
        for (int i = 9; i < personInfoTextFields.length; i++) { // Start from index 9, after Birthdate
            textFieldGroup.addComponent(personInfoTextFields[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        horizontalGroup.addGroup(labelGroup);
        horizontalGroup.addGroup(textFieldGroup);
        textFieldsPanelLayout.setHorizontalGroup(horizontalGroup);

        // Vertical group
        GroupLayout.SequentialGroup verticalGroup = textFieldsPanelLayout.createSequentialGroup();

        // Add all labels and fields to the vertical group
        for (int i = 0; i < 6; i++) { // Add components before Birthdate
            verticalGroup.addGroup(textFieldsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(personInfoLabels[i])
                    .addComponent(personInfoTextFields[i]));
        }

        // Birthdate vertical group
        verticalGroup.addGroup(textFieldsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(personInfoLabels[6])
                .addComponent(personInfoTextFields[6])
                .addComponent(personInfoTextFields[7])
                .addComponent(personInfoTextFields[8]));

        // Add all labels and fields after Birthdate
        for (int i = 7; i < personInfoLabels.length; i++) { // Start from index 7, after Birthdate label
            verticalGroup.addGroup(textFieldsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(personInfoLabels[i])
                    .addComponent(personInfoTextFields[i+2])); // Adjust index by +2 to account for additional birthdate textfields
        }

        textFieldsPanelLayout.setVerticalGroup(verticalGroup);

        // Buttons
        delete = new JButton("Delete Person");
        clear = new JButton("Clear All Changes");
        submit = new JButton("Submit");
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(delete);
        buttonsPanel.add(clear);
        buttonsPanel.add(submit);
        setupActionListeners();

        // Add components to the content pane
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(textFieldsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        // Pack the frame to apply the new sizes
        pack();
    }


    /**
     * Establishes database connection, prepares and performs the database query to insert person into PERSONS table.
     */
    private void getPersonInfoFromDB(int personID) {
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
                for (int i = 0, j = 0; i < 10; i++, j++) {
                    String columnValue = String.valueOf(resultSet.getObject(i + 1));

                    // Check if birthdate column
                    if(j == 6) {
                        if (columnValue.equals("null")) {
                            for(; j < 8; j++) {
                                personInfoValues[j] = null;
                            }
                        }
                        else {
                            // Directly constructing year, month, and day
                            personInfoValues[6] = columnValue.substring(0, 4);
                            personInfoValues[7] = columnValue.substring(5, 7);
                            personInfoValues[8] = columnValue.substring(8, 10);
                        }

                        j = 8;
                    }
                    else{ // Not birthdate column
                        if (columnValue.equals("null"))
                            personInfoValues[j] = null;
                        else
                            personInfoValues[j] = columnValue;
                    }
                }
            }

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
    } // end addPerson()

    /** Checks if the ID exists within the table */
    private boolean userExists(){
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet = null;

        try {
            conn = DatabaseConnection.getDBConnection();

            String checkSql = "SELECT COUNT(*) FROM persons WHERE id = ?";
            prepStmt = conn.prepareStatement(checkSql);
            prepStmt.setInt(1, personID);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0)
                return true; // ID found within table
        }
        catch (SQLException e) {
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

        return false; // ID not found within table
    }


    /** Establishes database connection, prepares and performs the database query to insert person into PERSONS table. */
    private void updatePerson() throws ClassNotFoundException, SQLException {
        // Return if no changes have been made
        if(!hasChanged()){
            JOptionPane.showMessageDialog(null, "No updates have been entered. " +
                    "Please enter new value(s) before submitting.");
            return;
        }

        // Establish database connection
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection
            conn = DatabaseConnection.getDBConnection();

            // First, check if the person exists in the database
            String checkSql = "SELECT COUNT(*) FROM persons WHERE id = ?";
            prepStmt = conn.prepareStatement(checkSql);
            prepStmt.setInt(1, personID);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Person exists, proceed with update

                // Validate optional prefix name field
                if (!personInfoTextFields[1].getText().trim().isEmpty()
                        && !InputValidation.isValidName(personInfoTextFields[1].getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid input for Prefix");
                    return;
                }

                // Validate mandatory first name field
                if (!InputValidation.isValidName(personInfoTextFields[2].getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid input for First Name");
                    return;
                }

                // Validate optional fields
                for (int i = 2, j = 2; j < 12; i++, j++) {
                    if(i == 6) j = 9; // Skip birthdate fields

                    String value = personInfoTextFields[j].getText().trim();

                    if (!value.isEmpty() && !isValidForField(i, value)) {
                        JOptionPane.showMessageDialog(null, "Invalid input for "
                                + COLUMN_NAMES[i]);
                        return;
                    }
                } // end for

                java.sql.Date sqlDate = null;

                // Check if birthdate was inputted
                boolean hasDateInput =
                        !isPlaceholderOrInvalid(personInfoTextFields[6].getText(), "YYYY")
                                || !isPlaceholderOrInvalid(personInfoTextFields[7].getText(), "MM")
                                || !isPlaceholderOrInvalid(personInfoTextFields[8].getText(), "DD");

                // Check if date fields have input
                if(hasDateInput) {
                    // Check if date is in proper YYYY-MM-DD format
                    try {
                        // Validate and format birthdate
                        String yearText = personInfoTextFields[6].getText().trim();
                        String monthText = personInfoTextFields[7].getText().trim();
                        String dayText = personInfoTextFields[8].getText().trim();

                        int year = Integer.parseInt(yearText);
                        int month = Integer.parseInt(monthText);
                        int day = Integer.parseInt(dayText);

                        // Check if year is within last 150 years
                        if (year < Calendar.getInstance().get(Calendar.YEAR) - 150 ||
                                year > Calendar.getInstance().get(Calendar.YEAR)) {
                            JOptionPane.showMessageDialog(this, "Invalid year. Enter a year between "
                                            + (Calendar.getInstance().get(Calendar.YEAR) - 150) + " and "
                                            + Calendar.getInstance().get(Calendar.YEAR),
                                    "Invalid Year", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // Check if month is between 1 and 12
                        else if (month < 1 || month > 12) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid month. Enter a month between 1 and 12.", "Invalid Month",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // Check if day for given month is valid
                        else if (day < 1 || day > getDaysInMonth(year, month)) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid day. Enter a valid day for " + "the selected month and year.",
                                    "Invalid Day", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // Set the date
                        String dateText = yearText + "-" + monthText + "-" + dayText;
                        sqlDate = java.sql.Date.valueOf(dateText);

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                // Set EMPTY to NULL
                String[] emptyToNullStrings = new String[personInfoValues.length];
                for (int i = 0; i < personInfoValues.length; i++) {
                    if (!this.personInfoTextFields[i].getText().isEmpty()) {
                        emptyToNullStrings[i] = this.personInfoTextFields[i].getText().trim();
                    }
                }

                // Prepare the SQL statement
                String sql = "UPDATE PERSONS SET prefix_name = ?, first_name = ?, middle_name = ?, last_name = ?, " +
                        "suffix_name = ?, birthdate = ?, address = ?, phone_number = ?, email = ? WHERE id = " + personID;

                prepStmt = conn.prepareStatement(sql);

                // Set values for PreparedStatement
                for (int i = 1, j = 1; i < 10; i++, j++) {
                    if (i == 6) { // if birthdate
                        // Set birthdate value for PreparedStatement
                        if (sqlDate != null)
                            prepStmt.setDate(6, sqlDate);
                        else
                            prepStmt.setNull(6, Types.DATE);

                        j = 8; // skip birthdate fields
                    }
                    else{
                        prepStmt.setString(i, emptyToNullStrings[j]);
                    }
                }

                // Execute the query
                prepStmt.execute();

                // Create a JOptionPane
                JOptionPane pane = new JOptionPane("Persons updated successfully", JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = pane.createDialog(this, "Message");

                // Set a timer to close the dialog after 1 seconds (1000 milliseconds)
                Timer timer = new Timer(1000, e -> dialog.dispose());
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();

                dialog.setVisible(true); // Show the dialog

                dispose(); // Close the window

                PersonModule.refreshTable();
            } else {
                // Person does not exist
                JOptionPane.showMessageDialog(this, "No person found with ID: " + personID + ". Cannot delete non-existent person.", "Not Found", JOptionPane.ERROR_MESSAGE);
            }

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
    } // end addPerson()

    /** Returns whether an input value equals its placeholder value or is empty for a given field. */
    private boolean isPlaceholderOrInvalid(String text, String placeholder) {
        return text.equals(placeholder) || text.isEmpty();
    }

    /** Returns whether an input value is valid for a given field. */
    private boolean isValidForField(int fieldIndex, String value) {
        // Implement field-specific validation logic here
        // This is a placeholder implementation, adjust according to your validation logic
        switch (fieldIndex) {
            case 1: // Prefix
            case 2:
            case 3: // Middle Name
            case 4: // Last Name
            case 5: // Suffix
                return InputValidation.isValidName(value);
            case 9: // Address
                return InputValidation.isValidAddress(value);
            case 10: // Phone Number
                return InputValidation.isValidPhoneNumber(value);
            case 11: // Email
                return InputValidation.isValidEmail(value);
            default:
                return true;
        }
    }

    /** Helper method to add FocusListener to text fields. */
    private void addFocusListenerToTextField(JTextField textField, String placeholder, boolean isMonthOrDate) {
        // focusGained
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                textField.setText(textField.getText().trim());

                if (textField.getText().equals(placeholder)) {
                    textField.setForeground(Color.GRAY);
                }
                else {
                    textField.setForeground(Color.BLACK);
                }
            }
        });

        // focusLost
        if(isMonthOrDate){
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().trim().length() == 1) {
                        textField.setText("0" + textField.getText().trim());
                    }
                }
            });
        }
    } // end addFocusListenerToTextField()

    /** Helper method for adding ActionListener to buttons. */
    private void setupActionListeners() {
        this.delete.addActionListener(event -> deletePerson());

        this.clear.addActionListener(event -> clearChanges());

        this.submit.addActionListener(event -> {
            try {
                updatePerson();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Driver Error: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid numeric input.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void deletePerson() {
        // First check if ID exists within table
        if(!userExists()){ // Person does not exist
            JOptionPane.showMessageDialog(this, "No person found with ID: " + personID
                    + ". Cannot delete non-existent person.", "Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Open confirmation window to confirm the deletion
        DeletionConfirmationWindow confirmWindow = new DeletionConfirmationWindow(this, personID);
        confirmWindow.setLocationRelativeTo(this); // Center the dialog
        confirmWindow.setVisible(true); // This will block until the dialog is closed

        // Check if deletion was confirmed
        if (!confirmWindow.isDeletionConfirmed()) {
            JOptionPane.showMessageDialog(this, "Deletion cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Variables for database connection
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection
            conn = DatabaseConnection.getDBConnection();

            // First, check if the person exists in the database
            String checkSql = "SELECT COUNT(*) FROM persons WHERE id = ?";
            prepStmt = conn.prepareStatement(checkSql);
            prepStmt.setInt(1, personID);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Person exists, proceed with deletion
                String deleteSql = "DELETE FROM persons WHERE id = ?";
                prepStmt = conn.prepareStatement(deleteSql);
                prepStmt.setInt(1, personID);
                prepStmt.executeUpdate();

                // Create a JOptionPane
                JOptionPane pane = new JOptionPane("Persons deleted successfully", JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = pane.createDialog(this, "Message");

                // Set a timer to close the dialog after 1 seconds (1000 milliseconds)
                Timer timer = new Timer(1000, e -> dialog.dispose());
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();

                dialog.setVisible(true); // Show the dialog

                dispose(); // Close the window

                PersonModule.refreshTable();
            } else {
                // Person does not exist
                JOptionPane.showMessageDialog(this, "No person found with ID: " + personID + ". Cannot delete non-existent person.", "Not Found", JOptionPane.ERROR_MESSAGE);
            }

            dispose();

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
    }

    /** Resets text fields to their original person info values */
    private void clearChanges(){
        // Open confirmation window to confirm the clearance
        int result = JOptionPane.showConfirmDialog(this, "Press OK to clear changes", "Clear Changes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 1; i < personInfoTextFields.length; i++) {
                String value = personInfoValues[i];
                if(value == null || value.isEmpty())
                    personInfoTextFields[i].setText("");
                else
                    personInfoTextFields[i].setText(personInfoValues[i]);
            }
            JOptionPane.showMessageDialog(this, "All changes cleared.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Returns if the text field values have changed or not */
    private boolean hasChanged(){
        for (int i = 1; i < personInfoTextFields.length; i++) {
            String value = personInfoValues[i];
            if(value == null) value = "";

            if (!personInfoTextFields[i].getText().equals(value))
                return true;
        }

        return false;
    }

    /** Helper method to get the number of days in a given month, accounting for leap yearesultSet. */
    private int getDaysInMonth(int year, int month) {
        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                return 29; // Leap year
            } else {
                return 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    /**
     * Sets the maximum length of text that can be entered into a JTextField.
     * This method applies a DocumentFilter to the JTextField which intercepts text insertions
     * and replacements, and only allows them if the resulting text length is within the specified limit.
     *
     * @param textField The JTextField to apply the length restriction.
     * @param maxLength The maximum number of characters allowed.
     */
    private void setMaxLength(JTextField textField, int maxLength) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                // Check if inserting 'text' at 'offset' would exceed 'maxLength'
                if ((fb.getDocument().getLength() + text.length()) <= maxLength) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Check if replacing 'length' characters at 'offset' with 'text' would exceed 'maxLength'
                if (text != null && (fb.getDocument().getLength() + text.length() - length) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    /** DeletionConfirmationWindow. Self-explanatory.  */
    public static class DeletionConfirmationWindow extends JDialog {
        private boolean isDeletionConfirmed = false;
        private int personIdToDelete;
        private JTextField idField;

        public DeletionConfirmationWindow(JFrame parent, int personIdToDelete) {
            super(parent, "Confirm Deletion", true); // true for modal
            this.personIdToDelete = personIdToDelete;
            initializeUI();
        }

        private void initializeUI() {
            setLayout(new FlowLayout());

            // Panel for label and text field
            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            idField = new JTextField(9);
            JLabel label = new JLabel("Enter ID to confirm deletion:");

            inputPanel.add(label);

            // Create and add components
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputPanel.add(label);

            idField = new JTextField(6);

            PlainDocument doc = (PlainDocument) idField.getDocument();
            doc.setDocumentFilter(new DocumentFilter() {
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String string = fb.getDocument().getText(0, fb.getDocument().getLength());
                    string += text;
                    if ((fb.getDocument().getLength() + text.length() - length) <= 9 && string.matches("\\d*")) {
                        super.replace(fb, offset, length, text, attrs);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }

                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if ((fb.getDocument().getLength() + string.length()) <= 9 && string.matches("\\d*")) {
                        super.insertString(fb, offset, string, attr);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            });

            inputPanel.add(idField);


            // Panel for arranging components vertically
            JPanel containerPanel = new JPanel();
            containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

            // Add the input panel to the container panel
            inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            containerPanel.add(inputPanel);


            JButton confirmButton = new JButton("Confirm");
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirmButton.addActionListener(e -> confirmDeletion());
            containerPanel.add(confirmButton);

            // Add the panel to the dialog's content pane
            getContentPane().add(containerPanel, BorderLayout.CENTER);
            pack();
        }

        private void confirmDeletion() {
            try {
                int enteredId = Integer.parseInt(idField.getText());
                if (enteredId == personIdToDelete) {
                    isDeletionConfirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "ID does not match. Try again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a valid number.");
            }
        }

        public boolean isDeletionConfirmed() {
            return isDeletionConfirmed;
        }
    }
}