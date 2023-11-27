package schoolmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

public class AddStudent extends ApplicationWindow {

	private JTextField[] studentInfo;
	private JLabel[] studentInfoLabel;
	private JPanel textFields;
	private JButton goBack;
	private JButton submit;
	private JLabel label;
	private JPanel buttons;
	
	public AddStudent() {
		this.initComponents();
	}

	private void initComponents() {
		this.studentInfoLabel = new JLabel[8];
		this.studentInfoLabel[0] = new JLabel("ID");
		this.studentInfoLabel[1] = new JLabel("First Name");
		this.studentInfoLabel[2] = new JLabel("Last Name");
		this.studentInfoLabel[3] = new JLabel("Phone");
		this.studentInfoLabel[4] = new JLabel("Guardian First Name");
		this.studentInfoLabel[5] = new JLabel("Guardian Last Name");
		this.studentInfoLabel[6] = new JLabel("Guardian Phone");
		this.studentInfoLabel[7] = new JLabel("Address");
		this.studentInfo = new JTextField[this.studentInfoLabel.length];
		for (int i = 0; i < this.studentInfo.length; i++) {
			this.studentInfo[i] = new JTextField();
		}
		this.textFields = new JPanel();
		this.goBack = new JButton("Back");
		this.submit = new JButton("Submit");
		this.label = new JLabel("Student Registration");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		GroupLayout textFieldLayout = new GroupLayout(this.textFields);
		this.textFields.setLayout(textFieldLayout);
		textFieldLayout.setAutoCreateGaps(true);
		textFieldLayout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup horizontalGroup = textFieldLayout.createSequentialGroup();
		GroupLayout.ParallelGroup labelGroup = textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup textFieldGroup = textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		for (int i = 0; i < this.studentInfoLabel.length; i++) {
			labelGroup.addComponent(this.studentInfoLabel[i]);
			textFieldGroup.addComponent(this.studentInfo[i]);
		}
		horizontalGroup.addGroup(labelGroup);
		horizontalGroup.addGroup(textFieldGroup);
		textFieldLayout.setHorizontalGroup(horizontalGroup);
		
		GroupLayout.SequentialGroup verticalGroup = textFieldLayout.createSequentialGroup();
		for (int i = 0; i < this.studentInfoLabel.length; i++) {
			verticalGroup.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(this.studentInfoLabel[i]).addComponent(this.studentInfo[i]));
		}
		textFieldLayout.setVerticalGroup(verticalGroup);
		
		this.goBack.addActionListener(event -> showWindow(new StudentModule()));
		this.submit.addActionListener(event -> {
			try {
				this.addStudent();
			}
			catch (SQLException e) {
				// TODO
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		this.buttons.add(this.goBack);
		this.buttons.add(this.submit);
		
		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.textFields, BorderLayout.CENTER);
		getContentPane().add(this.buttons, BorderLayout.SOUTH);
	}
	
	private void addStudent() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}
	
	public String[] getStudentAttributes() {
		String[] studentAttributes = new String[this.studentInfoLabel.length];
		for (int i = 0; i < studentAttributes.length; i++) {
			studentAttributes[i] = this.studentInfoLabel[i].getText();
		}
		return studentAttributes;
	}

	/*
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn; // FIX
			String sql = "insert into stureg values(?,?,?,?,?,?,?,?)";

			PreparedStatement ptstmt = conn.prepareStatement(sql);
			ptstmt.setString(1, sid.getText());
			ptstmt.setString(2, sn.getText());
			ptstmt.setString(3, fn.getText());
			ptstmt.setString(4, pn.getText());
			ptstmt.setString(5, fpn.getText());
			ptstmt.setString(6, cl.getText());
			ptstmt.setString(7, roll.getText());
			ptstmt.setString(8, add.getText());

			ptstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Data stores successfully");
			conn.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
*/
	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new AddStudent().showWindow());
	}
}