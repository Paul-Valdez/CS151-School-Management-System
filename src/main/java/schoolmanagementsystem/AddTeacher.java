package schoolmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
			catch (Exception e) {
				// TODO
			}
		});
		
		this.buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.buttons.add(this.goBack);
		this.buttons.add(this.submit);
		
		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.textFields, BorderLayout.CENTER);
		getContentPane().add(this.buttons, BorderLayout.SOUTH);
	}
	
	private void addTeacher() {
		// TODO
	}
	
	public String[] getTeacherAttributes() {
		String[] teacherAttributes = new String[this.teacherInfoLabel.length];
		for (int i = 0; i < teacherAttributes.length; i++) {
			teacherAttributes[i] = this.teacherInfoLabel[i].getText();
		}
		return teacherAttributes;
	}
	/*
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		sid = new javax.swing.JTextField();
		sn = new javax.swing.JTextField();
		fn = new javax.swing.JTextField();
		pn = new javax.swing.JTextField();
		fpn = new javax.swing.JTextField();
		cl = new javax.swing.JTextField();
		roll = new javax.swing.JTextField();
		add = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jLabel11 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
		jLabel1.setText("Student Registration");

		jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel2.setText("ID:");

		jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel3.setText("Name:");

		jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel4.setText("Guardian:");

		jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel5.setText("Phone");

		jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel6.setText("Guardian Phone:");

		jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel7.setText("Class:");

		jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel8.setText("Roll Number:");

		jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jLabel9.setText("Address:");

		sid.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		sn.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		fn.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		pn.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		fpn.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		cl.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		roll.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		roll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rollActionPerformed(evt);
			}
		});

		add.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N

		jButton1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 12)); // NOI18N
		jButton1.setText("Submit");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(0, 0, 204));
		jLabel11.setText("BACK");
		jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLabel11MouseClicked(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap(100, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel4).addComponent(jLabel5))
										.addGap(49, 49, 49)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(fn, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(sn, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(sid, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel6).addComponent(jLabel7).addComponent(jLabel8)
												.addComponent(jLabel9))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(cl, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(fpn, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(roll, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
								.addGap(155, 155, 155))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(jLabel11).addGap(27, 27, 27))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addComponent(jLabel1).addGap(138, 138, 138))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel2).addComponent(jLabel3))
								.addGap(28, 28, 28)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(jLabel11).addGap(8, 8, 8).addComponent(jLabel1)
				.addGap(28, 28, 28)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(sid, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(sn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel4).addComponent(fn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel5).addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel6).addComponent(fpn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel7)
						.addGap(6, 6, 6)).addComponent(cl, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel8)
						.addComponent(roll, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel9)
						.addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18).addComponent(jButton1).addContainerGap(19, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents*/
/*
	private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel11MouseClicked
		// TODO add your handling code here:
		StudentModule obj = new StudentModule();
		obj.setVisible(true);
		dispose();
	}// GEN-LAST:event_jLabel11MouseClicked

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
	}// GEN-LAST:event_jButton1ActionPerformed

	private void rollActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rollActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_rollActionPerformed
*/
	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new AddTeacher().showWindow());
	}
}