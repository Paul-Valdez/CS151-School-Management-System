package schoolmanagementsystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.EventQueue;

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

public class EditTeacher extends ApplicationWindow {
	
	private JTextField[] teacherInfo;
	private JLabel[] teacherInfoLabel;
	private JPanel textFields;
	private JButton goBack;
	private JButton edit;
	private JLabel label;
	private JPanel buttons;
	
	public EditTeacher() {
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
		this.edit = new JButton("Edit");
		this.label = new JLabel("Edit Teacher Information");
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
		
		this.goBack.addActionListener(event -> showWindow(new TeacherModule()));
		this.edit.addActionListener(event -> {
			try {
				this.editTeacher();
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
	
	private void editTeacher() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new EditTeacher().showWindow());
	}
    /*

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String tid= id.getText();
        String tn=name.getText();
        String spe= spec.getText();
        String sub=subject.getText();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","");
            String sql= "update `addteacher` SET `name`='"+tn+"',`spec`='"+spe+"',`subject`='"+sub+"'WHERE id='"+tid+"'";
            PreparedStatement ptst=conn.prepareStatement(sql);
            ptst.execute();
            
            JOptionPane.showMessageDialog(null, "Recordupdated successfully");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     */
}
