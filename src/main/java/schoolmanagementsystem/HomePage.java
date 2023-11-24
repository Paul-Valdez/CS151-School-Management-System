package schoolmanagementsystem;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

public class HomePage extends ApplicationWindow {

	private JLabel label;
	private JButton studentModule;
	private JButton teacherModule;
	private JButton logout;
	private JPanel panel;

	public HomePage() {
		this.initComponents();
	}

	private void initComponents() {
		this.label = new JLabel("Main Menu");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.studentModule = new JButton("Student Module");
		this.teacherModule = new JButton("Teacher Module");
		this.logout = new JButton("Logout");
		this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		this.studentModule.addActionListener(event -> showWindow(new StudentModule()));
		this.teacherModule.addActionListener(event -> showWindow(new TeacherModule()));
		this.logout.addActionListener(event -> showWindow(new LoginPage()));

		this.panel.add(this.studentModule);
		this.panel.add(this.teacherModule);
		this.panel.add(this.logout);

		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.panel, BorderLayout.CENTER);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new HomePage().showWindow());
	}
}
