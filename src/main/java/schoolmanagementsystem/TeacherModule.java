package schoolmanagementsystem;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TeacherModule extends ApplicationWindow {


	private JLabel label;
	private JTable table;
	private JButton goBack;
	private JButton addTeacher;
	private JButton edit;
	private JButton delete;
	private JButton search;
	private JComboBox listAttributes;
	private JTextField textToSearch;
	private JScrollPane scrollPane;
	private JPanel middlePanel;
	private JPanel bottomPanel;

	public TeacherModule() {
		this.initComponents();
	}

	private void initComponents() {
		this.label = new JLabel("Search & Edit Teachers");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.goBack = new JButton("Back");
		this.addTeacher = new JButton("Add");
		this.search = new JButton("Search");
		this.edit = new JButton("Edit");
		this.delete = new JButton("Fire");
		String[] columns = { "Attribute 1", "Attribute 2" };
		this.listAttributes = new JComboBox(columns);
		this.textToSearch = new JTextField();
		this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.middlePanel = new JPanel(new GridBagLayout());

		// TODO Add functionality
		String[][] values = { { "Example 1", "Example 2" }, { "Example 3", "Example 4" } };
		this.table = new JTable(values, columns);
		this.scrollPane = new JScrollPane(this.table);

		this.goBack.addActionListener(event -> showWindow(new HomePage()));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.middlePanel.add(this.listAttributes, constraints);
		constraints.weightx = 1.0;
		constraints.gridx++;
		this.middlePanel.add(this.textToSearch, constraints);
		constraints.weightx = 0.0;
		constraints.gridx++;
		this.middlePanel.add(this.search, constraints);
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		constraints.gridy++;
		this.middlePanel.add(this.scrollPane, constraints);
		
		this.bottomPanel.add(this.goBack);
		this.bottomPanel.add(this.addTeacher);
		this.bottomPanel.add(this.edit);
		this.bottomPanel.add(this.delete);

		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.middlePanel, BorderLayout.CENTER);
		getContentPane().add(this.bottomPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new TeacherModule().showWindow());
	}

	private void updateTable(String sql) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}

    /*
    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        TeacherModule obj =new TeacherModule();
        obj.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String tid= id.getText();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn; // FIX
            String sql="delete from `addteacher` WHERE id="+tid;
            PreparedStatement ptst=conn.prepareStatement(sql);
            ptst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data has been removed!");
            conn.close();
         }catch (Exception e){
             JOptionPane.showMessageDialog(this, e);
         }
    } 
     */
}