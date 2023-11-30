package schoolmanagementsystem.Student;

import schoolmanagementsystem.ApplicationWindow;
import schoolmanagementsystem.MainMenu;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StudentModule extends ApplicationWindow {

	private JLabel label;
	private JTable table;
	private JButton goBack;
	private JButton addStudent;
	private JButton modifyFee;
	private JButton edit;
	private JButton delete;
	private JButton search;
	private JComboBox listAttributes;
	private JTextField textToSearch;
	private JScrollPane scrollPane;
	private JPanel middlePanel;
	private JPanel bottomPanel;

	public StudentModule() {
		this.initComponents();
	}

	private void initComponents() {
		this.label = new JLabel("Search & Edit Students");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.goBack = new JButton("Back");
		this.addStudent = new JButton("Add");
		this.search = new JButton("Search");
		this.modifyFee = new JButton("Modify Fee");
		this.edit = new JButton("Edit");
		this.delete = new JButton("Delete");
		String[] columns = { "Attribute 1", "Attribute 2" };
		this.listAttributes = new JComboBox(columns);
		this.textToSearch = new JTextField();
		this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.middlePanel = new JPanel(new GridBagLayout());

		// TODO Add functionality
		String[][] values = { { "Example 1", "Example 2" }, { "Example 3", "Example 4" } };
		this.table = new JTable(values, columns);
		this.scrollPane = new JScrollPane(this.table);

		this.goBack.addActionListener(event -> showWindow(new MainMenu()));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
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
		this.bottomPanel.add(this.addStudent);
		this.bottomPanel.add(this.modifyFee);
		this.bottomPanel.add(this.edit);
		this.bottomPanel.add(this.delete);

		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.middlePanel, BorderLayout.CENTER);
		getContentPane().add(this.bottomPanel, BorderLayout.SOUTH);

		this.pack();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new StudentModule().showWindow());
	}

	private void updateTable(String sql) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
	}
/*
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
	 {//GEN-FIRST:event_jButton1ActionPerformed // TODO add your handling code here: try{ Class.forName("com.mysql.jdbc.Driver"); Connection conn =
	Connection conn; // FIX
	 Statement st=conn.createStatement(); String sql= "select * from stureg";
	 PreparedStatement ptst= conn.prepareStatement(sql); ResultSet rs=
	  ptst.executeQuery(); DefaultTableModel
	  tm=(DefaultTableModel)studTable.getModel(); tm.setRowCount(0);
	  while(rs.next()){ Object o[]=
	  {rs.getInt("ID"),rs.getString("Name"),rs.getString("fname"),rs.getInt("phone"),
	  rs.getInt("fatherphone"),rs.getString("class"),rs.getString("roll"),rs.getString("address")};
	  tm.addRow(o); }
	  
	  }catch(

	Exception e)
	{
		JOptionPane.showMessageDialog(null, e);
	}

	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
	  {//GEN-FIRST:event_jButton2ActionPerformed // TODO add your handling code
	  here:
	  
	  String sd= sid.getText(); try{ Class.forName("com.mysql.jdbc.Driver");
	Connection conn; // FIX
	  DriverManager.getConnection(); // FIX
	 Statement st=conn.createStatement(); String sql= "DELETE FROM `stureg` WHERE ID="+sd; PreparedStatement ptst= conn.prepareStatement(sql);
	  ptst.executeUpdate(); JOptionPane.showMessageDialog(null, "Data has been removed"); conn.close(); sid.setText(""); }catch(Exception e){
	  JOptionPane.showMessageDialog(null, e); }
	  }// GEN-LAST:event_jButton2ActionPerformed
*/
}