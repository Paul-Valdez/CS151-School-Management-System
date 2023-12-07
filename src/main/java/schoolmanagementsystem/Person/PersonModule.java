package schoolmanagementsystem.Person;

import schoolmanagementsystem.ApplicationWindow;
import schoolmanagementsystem.LoginPage;
import schoolmanagementsystem.Utilities.DatabaseConnection;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.TableColumnModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.ListSelectionModel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.BorderLayout;

public class PersonModule extends ApplicationWindow {
	
	private JPanel panel;
	private JTextField searchTextField;
	private JButton logoutButton, addButton, editButton, viewButton;
	private static JTable personTable;
	private TableRowSorter<DefaultTableModel> tableRowSorter;
	private static final String SEARCH_PLACEHOLDER = "Enter search criteria e.g.: 123456789 (ID), "
			+ "Dr. Sammy Spartan (name), 1999-07-14 (birthdate), 408-555-6789 (phone number), etc.",
			INSTRUCTIONS_LABEL_TEXT = "<html>Filter results by typing in the search bar.<br>"
					+ "Sort by a specific criterion by clicking the respective column name.<br>"
					+ "To Edit or View a Person, first select a row by clicking it.<br>"
					+ "To Delete a Person, first click Edit.<br>"
					+ "Individual cell content that is selected is copyable using standard keyboard commands."
					+ "</html>";
	protected static final String[] COLUMN_NAMES = { "ID", "Prefix", "First Name", "Middle Name", "Last Name", "Suffix",
			"Birthdate", "Address", "Phone Number", "Email" };
	private static final int[] COLUMN_WIDTHS = { 72, 40, 96, 96, 96, 40, 80, 200, 88, 160 };

	// Default constructor
	public PersonModule() {
		this.initComponents(); // Initialize components
		// this.setLocationRelativeTo(null); // Center the window

		// Add window listener to request focus for the frame
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent evt) {
				formWindowOpened(evt);
			}
		});
	}

	/**
	 * Helper method for focusing on window
	 */
	private void formWindowOpened(WindowEvent evt) {
		this.requestFocusInWindow();
	}

	private void initComponents() {
		// Main panel setup
		// panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));

		// Instructions label setup
		JLabel instructionsLabel = new JLabel(INSTRUCTIONS_LABEL_TEXT);
		Font currentFont = instructionsLabel.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize2D() * 1.15f); // Increase size by 50%
		instructionsLabel.setFont(newFont);// instructionsLabel.setFont(regularFont);
		// instructionsLabel.setOpaque(true);
		instructionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		// instructionsLabel.setPreferredSize(new Dimension(900, 80));
		// panel.add(instructionsLabel);

		// Search text field setup
		searchTextField = new JTextField();
		// searchTextField.setPreferredSize(new Dimension(930, 30));
		panel.add(searchTextField);
		setSearchFocusListeners();

		// Create Table
		createTable();

		// Button panel setup
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 1 row, 5 cols, 5px hgap, 5px vgap
		// buttonPanel.setPreferredSize(new Dimension(930, 25));

		// Buttons setup
		logoutButton = new JButton("Logout");
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		viewButton = new JButton("View");
		buttonPanel.add(logoutButton);
		// buttonPanel.add(Box.createHorizontalStrut(722)); // Spacer setup
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(viewButton);

		// panel.add(buttonPanel);
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// Stop search from filtering using the placeholder
				if (!searchTextField.getText().equals(SEARCH_PLACEHOLDER)) {
					filterTable();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterTable();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Plain text components don't fire these events
			}
		});

		// Finalize frame setup
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
		// setContentPane(panel1); // Add panel to the frame
		getContentPane().add(instructionsLabel, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		// setSize(960, 525); // Set the frame size
		// setLocationRelativeTo(null); // Center the frame

		pack();
		setupActionListeners();
	}

	/**
	 * Helper method for adding ActionListener to buttons.
	 */
	private void setupActionListeners() {
		this.logoutButton.addActionListener(event -> showWindowAndDispose(new LoginPage()));
		this.addButton.addActionListener(event -> showWindow(new AddPerson()));
		this.editButton.addActionListener(event -> {
			int id = getSelectedRowID();
			if (id != -1)
				showWindow(new EditPerson(id));
			else
				JOptionPane.showMessageDialog(this, "No row is selected. Please select a row and try again.");
		});
		this.viewButton.addActionListener(event -> {
			int id = getSelectedRowID();
			if (id != -1)
				showWindow(new ViewPerson(id));
			else
				JOptionPane.showMessageDialog(this, "No row is selected. Please select a row and try again.");
		});
	}

	private static Object[][] fetchDataFromDatabase() {
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<Object[]> dataList = new ArrayList<>();

		try {
			// Get the database connection
			conn = DatabaseConnection.getDBConnection();

			// Prepare the SQL statement
			String sql = "SELECT * FROM PERSONS";
			prepStmt = conn.prepareStatement(sql);

			// Execute the query
			rs = prepStmt.executeQuery();

			// Process the ResultSet
			while (rs.next()) {
				Object[] rowData = new Object[10];
				rowData[0] = rs.getInt("id");
				rowData[1] = rs.getString("prefix_name");
				rowData[2] = rs.getString("first_name");
				rowData[3] = rs.getString("middle_name");
				rowData[4] = rs.getString("last_name");
				rowData[5] = rs.getString("suffix_name");
				rowData[6] = rs.getDate("birthdate");
				rowData[7] = rs.getString("address");
				rowData[8] = rs.getString("phone_number");
				rowData[9] = rs.getString("email");
				dataList.add(rowData);
			}

			// Execute the query
			prepStmt.executeQuery();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (rs != null)
					rs.close();
				if (prepStmt != null)
					prepStmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Convert List to Array
		return dataList.toArray(new Object[dataList.size()][]);
	}

	private DefaultTableModel createTableModel() {
		return new DefaultTableModel(fetchDataFromDatabase(), COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	public static void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) personTable.getModel();
		tableModel.setDataVector(fetchDataFromDatabase(), COLUMN_NAMES);
		tableModel.fireTableDataChanged();
		// setColumnWidths(); // Reapply column widths after refreshing data
	}

	// Helper method to set column widths
	private static void setColumnWidths() {
		TableColumnModel columnModel = personTable.getColumnModel();
		for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
			if (i < columnModel.getColumnCount()) {
				// columnModel.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
			} else
				break;
		}
	}

	private void createTable() {
		// Create table model with non-editable cells
		DefaultTableModel tableModel = createTableModel();
		personTable = new JTable(tableModel) {
			// Override to display tooltip with cell content
			public String getToolTipText(MouseEvent e) {
				Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);

				// Ensure the indices are valid
				if (rowIndex < 0 || colIndex < 0) {
					return null;
				}

				Object value = getModel().getValueAt(convertRowIndexToModel(rowIndex),
						convertColumnIndexToModel(colIndex));
				return value == null ? null : value.toString();
			}
		};

		ToolTipManager.sharedInstance().setInitialDelay(0); // Show tooltip immediately

		personTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Set to AUTO_RESIZE_OFF for scroll
		personTable.setRowSelectionAllowed(true);
		personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Action for copying individual cell contents
		Action copyAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = personTable.getSelectedRow();
				int col = personTable.getSelectedColumn();
				if (row >= 0 && col >= 0) {
					Object value = personTable.getValueAt(row, col);
					String textToCopy = (value == null) ? "" : value.toString();
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textToCopy), null);
				}
			}
		};

		personTable.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "Copy");
		personTable.getActionMap().put("Copy", copyAction);

		// Add a list selection listener to the table
		personTable.getSelectionModel().addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) {
				getSelectedRowID();
			}
		});

		// Calculate the width in pixels for each character
		FontMetrics metrics = personTable.getFontMetrics(personTable.getFont());
		int charWidth = metrics.charWidth('M');

		// Set column widths
		TableColumnModel columnModel = personTable.getColumnModel();
		for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
			// columnModel.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
		}

		// Table Row Sorterer
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
		personTable.setRowSorter(sorter);

		// Sorter for search
		tableRowSorter = new TableRowSorter<>(tableModel);
		personTable.setRowSorter(tableRowSorter);

		// Add personTable to scroll pane
		JScrollPane tableScrollPane = new JScrollPane(personTable);
		// tableScrollPane.setPreferredSize(new Dimension(930, 325));
		tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(tableScrollPane); // Add the JScrollPane containing the JTable to the panel
	}

	private int getSelectedRowID() {
		int selectedRow = personTable.getSelectedRow();

		if (selectedRow != -1) { // -1 means no row is selected
			Object value = personTable.getModel().getValueAt(selectedRow, 0); // 0 for the first column
			return (int) value;
		}

		return -1; // person ID not found
	}

	private void setSearchFocusListeners() {
		searchTextField.addFocusListener(new FocusAdapter() {
			// focusGained
			@Override
			public void focusGained(FocusEvent e) {
				if (searchTextField.getForeground() == Color.GRAY
						&& searchTextField.getText().equals(SEARCH_PLACEHOLDER)) {
					searchTextField.setText("");
					searchTextField.setForeground(Color.BLACK);
				} else if (!searchTextField.getText().isEmpty()) {
					// If there is actual text in the search field, filter the table
					filterTable();
				}
			}

			// focusLost
			@Override
			public void focusLost(FocusEvent e) {
				if (searchTextField.getText().trim().isEmpty()) {
					searchTextField.setForeground(Color.GRAY);
					searchTextField.setText(SEARCH_PLACEHOLDER);
				}
			}
		});
	}

	// SEARCH FUNCTIONALITY
	private void filterTable() {
		String searchText = searchTextField.getText().trim().toLowerCase();
		if (!searchText.isEmpty()) {
			tableRowSorter.setRowFilter(new PersonRowFilter(searchText));
		} else {
			tableRowSorter.setRowFilter(null);
		}
	}

	private class PersonRowFilter extends RowFilter<DefaultTableModel, Integer> {
		private final String searchText;

		public PersonRowFilter(String searchText) {
			this.searchText = searchText;
		}

		@Override
		public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
			for (int i = entry.getValueCount() - 1; i >= 0; i--) {
				if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
					return true;
				}
			}
			return false;
		}
	}
}