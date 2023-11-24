package schoolmanagementsystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;

public class LoginPage extends ApplicationWindow {

	private JLabel img;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel panel;
	private JPanel textFields;
	private JButton submit;
	private JLabel schmgtsysLabel;
	private JLabel loginPageLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;

	public LoginPage() {
		this.initComponents();
	}

	private void initComponents() {
		this.img = new JLabel(new ImageIcon(getClass().getResource("/SJSU_Seal.svg.png")));
		this.img.setBorder(new EmptyBorder(3, 3, 3, 3));
		this.usernameField = new JTextField();
		this.passwordField = new JPasswordField();
		this.submit = new JButton("Submit");
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
		this.textFields = new JPanel();
		this.schmgtsysLabel = new JLabel("School Management System");
		this.schmgtsysLabel.setFont(headerFont);
		this.schmgtsysLabel.setForeground(Color.RED);
		this.loginPageLabel = new JLabel("Sign In");
		this.usernameLabel = new JLabel("Username:");
		this.passwordLabel = new JLabel("Password:");

		GroupLayout textFieldLayout = new GroupLayout(this.textFields);
		this.textFields.setLayout(textFieldLayout);
		textFieldLayout.setAutoCreateGaps(true);
		textFieldLayout.setAutoCreateContainerGaps(true);

		textFieldLayout.setHorizontalGroup(textFieldLayout.createSequentialGroup()
				.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.usernameLabel).addComponent(this.passwordLabel))
				.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.usernameField).addComponent(this.passwordField)));
		textFieldLayout.setVerticalGroup(textFieldLayout.createSequentialGroup()
				.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.usernameLabel).addComponent(this.usernameField))
				.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.passwordLabel).addComponent(this.passwordField)));

		this.img.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.schmgtsysLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.loginPageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.textFields.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.submit.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.submit.addActionListener(event -> {
			try {
				this.loginUser();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Error while establishing connection!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});

		this.panel.add(this.img);
		this.panel.add(this.schmgtsysLabel);
		this.panel.add(this.loginPageLabel);
		this.panel.add(this.textFields);
		this.panel.add(this.submit);

		getContentPane().add(this.panel);
	}

	private void loginUser() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");

		// Example: jdbc:postgresql://[Host]:[Port]/[Database]
		String url = "jdbc:postgresql://db.ngkqyysuhzwkiczqojsa.supabase.co:5432/postgres";
		String user = "postgres";
		String password = "mtSEfzZNMQDw2qpy";

		Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		Statement st = (Statement) conn.createStatement();

		String sql = "SELECT * FROM user_login";

		ResultSet rs = st.executeQuery(sql);

		while (rs.next()) {
			String getUsername = rs.getString("username");
			String getPassword = rs.getString("password");

			boolean correctUsername = getUsername.equals(this.usernameField.getText());
			boolean correctPassword = getPassword.equals(String.valueOf(this.passwordField.getPassword()));

			if (correctUsername && correctPassword) {
				showWindow(new HomePage());
			} else {
				JOptionPane.showMessageDialog(this, "Username or password is incorrect!");
			}
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new LoginPage().showWindow());
	}
}