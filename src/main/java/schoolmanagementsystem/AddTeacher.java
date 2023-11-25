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

public class AddTeacher extends ApplicationWindow {

	private JTextField[] teacherInfo;
	private JLabel[] teacherInfoLabel;
	private JPanel textFields;
	private JButton goBack;
	private JButton submit;
	private JLabel label;
	private JPanel buttons;
	
	public AddTeacher() {
		this.initComponents();
	}

	private void initComponents() {
		this.teacherInfoLabel = new JLabel[6];
		this.teacherInfoLabel[0] = new JLabel("ID");
		this.teacherInfoLabel[1] = new JLabel("First Name");
		this.teacherInfoLabel[2] = new JLabel("Last Name");
		this.teacherInfoLabel[3] = new JLabel("Area of Study");
		this.teacherInfoLabel[4] = new JLabel("Teaching Subject");
		this.teacherInfoLabel[5] = new JLabel("Education Level");
		this.teacherInfo = new JTextField[this.teacherInfoLabel.length];
		for (int i = 0; i < this.teacherInfo.length; i++) {
			this.teacherInfo[i] = new JTextField();
		}
		this.textFields = new JPanel();
		this.goBack = new JButton("Back");
		this.submit = new JButton("Submit");
		this.label = new JLabel("Teacher Registration");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.buttons = new JPanel();
		
		GroupLayout textFieldLayout = new GroupLayout(this.textFields);
		this.textFields.setLayout(textFieldLayout);
		textFieldLayout.setAutoCreateGaps(true);
		textFieldLayout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup horizontalGroup = textFieldLayout.createSequentialGroup();
		GroupLayout.ParallelGroup labelGroup = textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup textFieldGroup = textFieldLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		for (int i = 0; i < this.teacherInfoLabel.length; i++) {
			labelGroup.addComponent(this.teacherInfoLabel[i]);
			textFieldGroup.addComponent(this.teacherInfo[i]);
		}
		horizontalGroup.addGroup(labelGroup);
		horizontalGroup.addGroup(textFieldGroup);
		textFieldLayout.setHorizontalGroup(horizontalGroup);
		
		GroupLayout.SequentialGroup verticalGroup = textFieldLayout.createSequentialGroup();
		for (int i = 0; i < this.teacherInfoLabel.length; i++) {
			verticalGroup.addGroup(textFieldLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(this.teacherInfoLabel[i]).addComponent(this.teacherInfo[i]));
		}
		textFieldLayout.setVerticalGroup(verticalGroup);
		
		this.goBack.addActionListener(event -> showWindow(new StudentModule()));
		this.submit.addActionListener(event -> {
			try {
				this.addTeacher();
			}
			catch (SQLException e) {
				// TODO
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		this.buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.buttons.add(this.goBack);
		this.buttons.add(this.submit);
		
		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.textFields, BorderLayout.CENTER);
		getContentPane().add(this.buttons, BorderLayout.SOUTH);
	}
	
	private void addTeacher() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}
	
	public String[] getTeacherAttributes() {
		String[] teacherAttributes = new String[this.teacherInfoLabel.length];
		for (int i = 0; i < teacherAttributes.length; i++) {
			teacherAttributes[i] = this.teacherInfoLabel[i].getText();
		}
		return teacherAttributes;
	}
/*
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
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
		EventQueue.invokeLater(() -> new AddTeacher().showWindow());
	}
}