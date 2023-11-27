package schoolmanagementsystem;

import schoolmanagementsystem.Utilities.DatabaseConnection;
import schoolmanagementsystem.Utilities.InputValidation;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/** Class for inserting a person into PERSONS table. */
public class CreatePerson extends ApplicationWindow {

    private JTextField[] personInfo;
    private JButton goBack, clear, submit;
    private final Font headerFont = new Font("SansSerif", Font.BOLD, 16); // Example font, adjust as needed
    private JTextField yearField, monthField, dayField;
    private static final String[] personInfoPlaceholderStrings = {"Mr., Ms., Dr., etc.", "John", "James", "Smith",
            "Sr., Jr., III, Ph.D., M.S., M.D., etc.", "1 Washington Sq, San Jose, CA 95192", "555-123-4567",
            "john.smith@sjsu.edu"};
    private static final String[] personInfoLabelStrings = {"Prefix", "First Name", "Middle Name", "Last Name",
            "Suffix", "Address", "Phone Number", "Email"};

    /** Default constructor for AddPerson class. */
    public CreatePerson() {
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

    /** Helper method for focusing on window */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        this.requestFocusInWindow();
    }

    /** Initializes form components. */
    private void initComponents() {
        // Labels
        int[] fieldMaxLengths = {50, 100, 100, 100, 50, 255, 120, 100};
        JLabel[] personInfoLabel = new JLabel[8];

        for (int i = 0; i < personInfoLabel.length; i++)
            personInfoLabel[i] = new JLabel(personInfoLabelStrings[i]);

        this.personInfo = new JTextField[personInfoLabel.length];

        for (int i = 0; i < this.personInfo.length; i++) {
            this.personInfo[i] = new JTextField(personInfoPlaceholderStrings[i]);
            this.personInfo[i].setForeground(Color.GRAY);
            addFocusListenerToTextField(this.personInfo[i], personInfoPlaceholderStrings[i], false);

            // Set the maximum length for each JTextField based on the database column size
            setMaxLength(this.personInfo[i], fieldMaxLengths[i]);
        }

        JLabel birthdateLabel = new JLabel("Birthdate");

        // Placeholder texts for birthdate
        String yearPlaceholder = "YYYY";
        yearField = new JTextField(yearPlaceholder, 4);
        setMaxLength(yearField, 4);
        yearField.setForeground(Color.GRAY);

        String monthPlaceholder = "MM";
        monthField = new JTextField(monthPlaceholder, 2);
        setMaxLength(monthField, 2);
        monthField.setForeground(Color.GRAY);

        String dayPlaceholder = "DD";
        dayField = new JTextField(dayPlaceholder, 2);
        setMaxLength(dayField, 2);
        dayField.setForeground(Color.GRAY);

        // Add FocusListeners to handle placeholder text
        addFocusListenerToTextField(yearField, yearPlaceholder, false);
        addFocusListenerToTextField(monthField, monthPlaceholder, true);
        addFocusListenerToTextField(dayField, dayPlaceholder, true);

        JPanel textFields = new JPanel();
        this.goBack = new JButton("Back");
        this.clear = new JButton("Clear");
        this.submit = new JButton("Submit");
        JLabel label = new JLabel("Person Registration");
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

        // Add the birthdate label and year, month, and day fields to the layout groups
        labelGroup.addComponent(birthdateLabel);
        fieldGroup.addGroup(layout.createSequentialGroup()
                .addComponent(yearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(monthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(dayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

        // Add the label and field groups to the horizontal group
        hGroup.addGroup(labelGroup);
        hGroup.addGroup(fieldGroup);

        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        for (int i = 0; i < personInfoLabel.length; i++) {
            verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(personInfoLabel[i]).addComponent(this.personInfo[i]));
        }

        // Align the birthdate label and fields vertically
        verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(birthdateLabel)
                .addComponent(yearField)
                .addComponent(monthField)
                .addComponent(dayField));

        layout.setVerticalGroup(verticalGroup);

        // Add action listeners for the buttons
        setupActionListeners();

        // Add components to the content pane
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(textFields, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        buttons.add(this.goBack);
        buttons.add(this.clear);
        buttons.add(this.submit);
    }

    /** Establishes database connection, prepares and performs the database query to insert person into PERSONS table. */
    private void createPerson() throws ClassNotFoundException, SQLException {
        // Establish database connection
        Connection conn = null;
        PreparedStatement ptstmt = null;

        try {
            // Validate mandatory first name and last name fields
            if (personInfo[1].getForeground() == Color.GRAY ||
                    !InputValidation.isValidName(personInfo[1].getText().trim())) {
                JOptionPane.showMessageDialog(null, "Invalid first name");
                return;
            }

            if (personInfo[3].getForeground() == Color.GRAY ||
                    !InputValidation.isValidName(personInfo[3].getText().trim())) {
                JOptionPane.showMessageDialog(null, "Invalid last name");
                return;
            }

            // Validate optional fields
            for (int i = 0; i < personInfo.length; i++) {
                if(i != 1 && i != 3 && personInfo[i].getForeground() == Color.BLACK){
                    String value = personInfo[i].getText().trim();

                    if (!isPlaceholderOrInvalid(value, personInfoPlaceholderStrings[i]) && !isValidForField(i, value)) {
                        JOptionPane.showMessageDialog(null, "Invalid input for "
                                + personInfoLabelStrings[i]);
                        return;
                    }
                }
            } // end for


            java.sql.Date sqlDate = null;

            // Check if birthdate was inputted
            boolean hasDateInput =
            !isPlaceholderOrInvalid(yearField.getText(), "YYYY")
                || !isPlaceholderOrInvalid(monthField.getText(), "MM")
                || !isPlaceholderOrInvalid(dayField.getText(), "DD");

            // Check if date fields have input
            if(hasDateInput) {
                // Check if date is in proper YYYY-MM-DD format
                try {
                    // Validate and format birthdate
                    String yearText = yearField.getText().trim();
                    String monthText = monthField.getText().trim();
                    String dayText = dayField.getText().trim();

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

            // Get the database connection
            conn = DatabaseConnection.getDBConnection();

            // Prepare the SQL statement
            String sql = "INSERT INTO PERSONS (prefix_name, first_name, middle_name, last_name, suffix_name, " +
                    "address, phone_number, email, birthdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ptstmt = conn.prepareStatement(sql);

            // Set values for PreparedStatement
            for (int i = 0; i < personInfo.length; i++) {
                if (personInfo[i].getForeground() == Color.GRAY || personInfo[i].getText().isEmpty()) {
                    ptstmt.setNull(i + 1, Types.VARCHAR);
                } else {
                    ptstmt.setString(i + 1, personInfo[i].getText().trim());
                }
            }

            // Set birthdate value for PreparedStatement
            if (sqlDate != null) {
                ptstmt.setDate(personInfo.length + 1, sqlDate);
            } else {
                ptstmt.setNull(personInfo.length + 1, Types.DATE);
            }

            // Execute the query
            ptstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Person added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Reset the form
            initFormFieldsPlaceholders();

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
            if (ptstmt != null) ptstmt.close();
            if (conn != null) conn.close();
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
            case 0: // Prefix
            case 2: // Middle Name
            case 4: // Suffix
                return InputValidation.isValidName(value);
            case 5: // Address
                return InputValidation.isValidAddress(value);
            case 6: // Phone Number
                return InputValidation.isValidPhoneNumber(value);
            case 7: // Email
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
            public void focusGained(FocusEvent e) {
                if (textField.getForeground() == Color.GRAY && textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
        });

        // focusLost
        if(isMonthOrDate){
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().isEmpty() || textField.getText().trim().equals("0")) {
                        textField.setForeground(Color.GRAY);
                        textField.setText(placeholder);
                    } else if (textField.getText().trim().length() == 1) {
                        textField.setText("0" + textField.getText().trim());
                    }
                }
            });
        }
        else{
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if(textField.getText().isEmpty()) {
                        textField.setForeground(Color.GRAY);
                        textField.setText(placeholder);
                    }
                }
            });
        }
    } // end addFocusListenerToTextField()

    /** Helper method for adding ActionListener to buttons. */
    private void setupActionListeners() {
        //this.goBack.addActionListener(event -> showWindow(new PersonModule()));
        this.clear.addActionListener(event -> initFormFieldsPlaceholders());
        this.submit.addActionListener(event -> {
            try {
                createPerson();
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

    /** Helper method to get the number of days in a given month, accounting for leap years. */
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
                if ((fb.getDocument().getLength() + text.length() - length) <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    /** Clears all the input fields in the form. */
    private void initFormFieldsPlaceholders() {
        for(int i = 0; i < personInfo.length; i++){
            personInfo[i].setText(personInfoPlaceholderStrings[i]);
            personInfo[i].setForeground(Color.GRAY);
        }

        yearField.setText("YYYY");
        yearField.setForeground(Color.GRAY);
        monthField.setText("MM");
        monthField.setForeground(Color.GRAY);
        dayField.setText("DD");
        dayField.setForeground(Color.GRAY);
    }

    /** Main method. */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CreatePerson frame = new CreatePerson();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define the default close operation
        });
    }
}