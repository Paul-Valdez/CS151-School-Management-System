package schoolmanagementsystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditStudent extends ApplicationWindow {
	
	private JTextField[] studentInfo;
	private JLabel[] studentInfoLabel;
	private JPanel textFields;
	private JButton goBack;
	private JButton edit;
	private JLabel label;
	private JPanel buttons;
	
	public EditStudent() {
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
		this.edit = new JButton("Edit");
		this.label = new JLabel("Edit Student Information");
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
		this.edit.addActionListener(event -> {
			try {
				this.editStudent();
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
		this.buttons.add(this.edit);
		
		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.textFields, BorderLayout.CENTER);
		getContentPane().add(this.buttons, BorderLayout.SOUTH);
	}
	
	private void editStudent() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new EditStudent().showWindow());
	}
    /*
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        String id = sid.getText();
        String name= sn.getText();
        String fname= fn.getText();
        String cla=cl.getText();
        String pnum=pn.getText();
        String fnumb= fpn.getText();
        String address= add.getText();
        String rn= roll.getText();
        
        // cant change fphone, roll, or address
        try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","");
        String sql = "update `stureg` SET `fname`='"+fname+"',`name`='"+name+"',`class`='"+cla+"',`phone`='"+pnum+"'WHERE id = '"+id+"'";
        PreparedStatement ptst= conn.prepareStatement(sql);
        ptst.execute();
        
        JOptionPane.showMessageDialog(null, "Record has been updated successfully!");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        
        }          
    } 
     */
}
